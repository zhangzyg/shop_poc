package com.loren.em.poc.service.impl;

import com.loren.em.poc.domain.Cart;
import com.loren.em.poc.domain.Coupon;
import com.loren.em.poc.domain.Product;
import com.loren.em.poc.domain.ProductMetadata;
import com.loren.em.poc.dto.CartDto;
import com.loren.em.poc.dto.ProductDto;
import com.loren.em.poc.dto.ProductStatistic;
import com.loren.em.poc.exception.EmBadRequestException;
import com.loren.em.poc.repository.CartRepository;
import com.loren.em.poc.repository.CouponRepository;
import com.loren.em.poc.repository.ProductMetadataRepository;
import com.loren.em.poc.repository.ProductRepository;
import com.loren.em.poc.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.loren.em.poc.constant.Constants.*;

@Service
public class CartServiceImpl implements CartService {

    //TODO: make below dict configurable, using js expression in JVM
    private static Map<String, Function<Double, Double>> consumeCouponFunction;
    private static Map<String, Predicate<Object>> isApplicableCouponPredicate;

    static {
        consumeCouponFunction = new HashMap<>(2);
        consumeCouponFunction.put(COUPON_J, (price) -> price - COUPON_DISCOUNT_PRICE_M);
        consumeCouponFunction.put(COUPON_D, (price) -> price - price * COUPON_DISCOUNT_PERCENTAGE_X * 0.01d);

        isApplicableCouponPredicate = new HashMap<>();
        isApplicableCouponPredicate.put(COUPON_J, (price) -> (int) price >= COUPON_CONDITION_UNIT_N);
        isApplicableCouponPredicate.put(COUPON_D, (price) -> (double) price > COUPON_CONDITION_PRICE_Y);
    }

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<ProductStatistic> getProductStatistic(String userId) {
        Map<String, ProductStatistic> productStatisticMap = getProductStatisticMap(userId);
        List<Coupon> coupons = couponRepository.findByCartUserUserId(userId);
        return productStatisticMap.values().stream().map(statistic -> {
            List<String> applicableCoupons = getApplicableCoupons(coupons, statistic);
            //if no coupon is applicable, then should be original price
            if (CollectionUtils.isEmpty(applicableCoupons)) {
                statistic.setDisCountPrice(statistic.getTotalPrice());
                return statistic;
            }
            Double disCountPrice = consumeCouponAndGetDiscountPrice(applicableCoupons, coupons, statistic);
            statistic.setDisCountPrice(disCountPrice);
            return statistic;
        }).collect(Collectors.toList());
    }

    @Override
    public CartDto getCartInfo(String userId) {
        CartDto cartDto = new CartDto();
        List<ProductStatistic> productList = getProductStatistic(userId);
        cartDto.setProductList(productList);
        cartDto.setTotalPrice(productList.stream().mapToDouble(ProductStatistic::getTotalPrice).sum());
        cartDto.setDisCountPrice(productList.stream().mapToDouble(ProductStatistic::getDisCountPrice).sum());
        return cartDto;
    }

    private Map<String, ProductStatistic> getProductStatisticMap(String userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserUserId(userId);
        if (cartOptional.isPresent()) {
            List<Product> productList = productRepository.findByCartUserUserId(userId);
            Map<String, ProductStatistic> productStatisticMap = productList.stream().collect(Collectors
                    .toMap(product -> product.getProductCategory().getProductCategoryId(), product -> {
                        ProductStatistic productStatistic = new ProductStatistic();
                        productStatistic.setProductCategoryId(product.getProductCategory().getProductCategoryId());
                        productStatistic.setAmount(1);
                        productStatistic.setTotalPrice(product.getProductCategory().getPrice());
                        return productStatistic;
                    }, (product1, product2) -> {
                        ProductStatistic productStatistic = new ProductStatistic();
                        productStatistic.setProductCategoryId(product1.getProductCategoryId());
                        productStatistic.setAmount(product1.getAmount() + product2.getAmount());
                        productStatistic.setTotalPrice(product1.getTotalPrice() + product2.getTotalPrice());
                        return productStatistic;
                    }));
            return productStatisticMap;
        }
        return null;
    }

    private List<String> getApplicableCoupons(List<Coupon> coupons, ProductStatistic productStatistic) {
        Map<String, Long> couponAmountDict = coupons.stream()
                .collect(
                        Collectors.groupingBy(coupon -> coupon.getCouponMetadata().getCouponTypeId(), Collectors.counting())
                );
        return couponAmountDict.entrySet().stream().filter((entry) -> {
            if (entry.getValue() < 0L) {
                return false;
            }
            if (entry.getKey().equals(COUPON_J)) {
                return entry.getValue() > 0 && isApplicableCouponPredicate.get(COUPON_J).test(productStatistic.getAmount());
            } else if (entry.getValue() > 0 && entry.getKey().equals(COUPON_D)) {
                return isApplicableCouponPredicate.get(COUPON_D).test(productStatistic.getTotalPrice());
            }
            return false;
        }).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private Double consumeCouponAndGetDiscountPrice(List<String> applicableCoupons, List<Coupon> couponList, ProductStatistic productStatistic) {
        Map<String, List<Coupon>> couponAmountDict = couponList.stream()
                .collect(
                        Collectors.groupingBy(coupon -> coupon.getCouponMetadata().getCouponTypeId())
                );
        Pair<Coupon, Double> couponDoublePair = applicableCoupons.stream().map(type -> {
            List<Coupon> coupons = couponAmountDict.get(type);
            //consume first coupon for each category
            Coupon coupon = coupons.get(0);
            Double disCountPrice = consumeCouponFunction.get(coupon.getCouponMetadata().getCouponType())
                    .apply(productStatistic.getTotalPrice());
            Double disCountPriceBoundary = disCountPrice < 0 ? 0.01d : disCountPrice;
            return Pair.of(coupon, disCountPriceBoundary);
        }).min(Comparator.comparing(Pair::getSecond)).get();

        //remove from list, after payment, will be deleted from repository
        couponList.remove(couponDoublePair.getFirst());

        return couponDoublePair.getSecond();
    }

}
