package com.loren.em.poc.dto;

import lombok.Data;

/**
 * dto for showing product statistic, e.g. category, how many products, total price
 */
@Data
public class ProductStatistic {

    private String productCategoryId;
    private Integer amount;
    private Double totalPrice;
    private Double disCountPrice;
}
