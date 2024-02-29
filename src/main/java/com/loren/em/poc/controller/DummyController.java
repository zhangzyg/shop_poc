package com.loren.em.poc.controller;

import com.loren.em.poc.domain.*;
import com.loren.em.poc.enumeration.Gender;
import com.loren.em.poc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.loren.em.poc.constant.Constants.*;


/**
 * just a dummy one, not follow rest
 */
@RestController
@RequestMapping("/api/v1/dummy")
public class DummyController {

    @Autowired
    private CouponMetadataRepository couponMetadataRepository;

    @Autowired
    private ProductMetadataRepository productMetadataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/delete/metadata")
    public void deleteMetadata() {
        couponMetadataRepository.deleteAll();
        productMetadataRepository.deleteAll();
    }

    @GetMapping("/delete/data")
    public void deleteData() {
        couponRepository.deleteAll();
        productRepository.deleteAll();
    }

    @GetMapping("/init/metadata")
    public void initMetadata() {
        //coupon metadata
        CouponMetadata couponMetadata = new CouponMetadata();
        couponMetadata.setCouponType(COUPON_J);
        couponMetadata.setCouponTypeId(COUPON_J);
        couponMetadata.setUsage("Discount price by reduce money");

        CouponMetadata couponMetadata2 = new CouponMetadata();
        couponMetadata2.setCouponType(COUPON_D);
        couponMetadata2.setCouponTypeId(COUPON_D);
        couponMetadata2.setUsage("Discount price by percentage");

        couponMetadataRepository.saveAll(List.of(couponMetadata, couponMetadata2));

        //product metadata
        ProductMetadata productMetadata = new ProductMetadata();
        productMetadata.setProductCategoryId(PRODUCT_A);
        productMetadata.setProductName(PRODUCT_A);
        productMetadata.setPrice(100.0d);

        ProductMetadata productMetadata2 = new ProductMetadata();
        productMetadata.setProductCategoryId(PRODUCT_B);
        productMetadata.setProductName(PRODUCT_B);
        productMetadata.setPrice(70.0d);

        productMetadataRepository.saveAll(List.of(productMetadata, productMetadata2));

        //user
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserName("Account 1");
        user.setAge(1);
        user.setGender(Gender.MALE);

        userRepository.save(user);
    }


    @GetMapping("/init/products/coupons/cart")
    public void initProductsCoupons() {
        //cart
        Cart cart = new Cart();
        User user = userRepository.findByUserName("Account 1").get();
        cart.setCartId("Cart 1");
        cart.setUser(user);

        Cart jpaCart = cartRepository.save(cart);

        //products
        List<Product> productList = new ArrayList<>(11);
        for (int i = 0; i < 1; i++) {
            Product product = new Product();
            ProductMetadata productMetadata = productMetadataRepository.findByProductCategoryId(PRODUCT_A).get();
            product.setProductCategory(productMetadata);
            product.setProductId(UUID.randomUUID().toString());
            product.setCart(jpaCart);
            productList.add(product);
        }

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            ProductMetadata productMetadata = productMetadataRepository.findByProductCategoryId(PRODUCT_B).get();
            product.setProductCategory(productMetadata);
            product.setProductId(UUID.randomUUID().toString());
            product.setCart(jpaCart);
            productList.add(product);
        }

        productRepository.saveAll(productList);

        //coupons
        List<Coupon> couponList = new ArrayList<>(COUPON_DISCOUNT_PERCENTAGE_X + COUPON_CONDITION_PRICE_Y);
        for (int i = 0; i < COUPON_DISCOUNT_PERCENTAGE_X; i++) {
            Coupon coupon = new Coupon();
            coupon.setCouponId(UUID.randomUUID().toString());
            CouponMetadata couponMetadata = couponMetadataRepository.findByCouponType(COUPON_J).get();
            coupon.setCouponMetadata(couponMetadata);
            coupon.setCart(jpaCart);
            couponList.add(coupon);
        }

        for (int i = 0; i < COUPON_CONDITION_PRICE_Y; i++) {
            Coupon coupon = new Coupon();
            coupon.setCouponId(UUID.randomUUID().toString());
            CouponMetadata couponMetadata = couponMetadataRepository.findByCouponType(COUPON_D).get();
            coupon.setCouponMetadata(couponMetadata);
            coupon.setCart(jpaCart);
            couponList.add(coupon);
        }

        couponRepository.saveAll(couponList);
    }
}
