package com.loren.em.poc.service;

import com.loren.em.poc.dto.CartDto;
import com.loren.em.poc.dto.ProductDto;
import com.loren.em.poc.dto.ProductStatistic;

import java.util.List;

/**
 * service for shop cart e.g. modify product amount, consume coupons, populate total price, populate total price with discount
 */
public interface CartService {

    //get each category info, will include disCount price, original price for each category
    List<ProductStatistic> getProductStatistic(String userId);

    //get all cart info: total price, each category info
    CartDto getCartInfo(String userId);

}
