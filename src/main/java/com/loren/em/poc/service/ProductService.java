package com.loren.em.poc.service;

import com.loren.em.poc.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> addProduct(String productType, String userId);

    List<ProductDto> removeProduct(String productType, String userId);
}
