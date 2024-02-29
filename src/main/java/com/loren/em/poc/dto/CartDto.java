package com.loren.em.poc.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private List<ProductStatistic> productList;
    private Double totalPrice;
    private Double disCountPrice;
}
