package com.loren.em.poc.controller;

import com.loren.em.poc.dto.ProductDto;
import com.loren.em.poc.dto.ProductRequestDto;
import com.loren.em.poc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.loren.em.poc.constant.Constants.API_V1_BASE_PATH;
import static com.loren.em.poc.constant.Constants.PRODUCT_CONTROLLER_PATH;

@RestController
@RequestMapping(API_V1_BASE_PATH + PRODUCT_CONTROLLER_PATH)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public List<ProductDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(productRequestDto.getProductType(), productRequestDto.getUserId());
    }

    @DeleteMapping
    public List<ProductDto> removeProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.removeProduct(productRequestDto.getProductType(), productRequestDto.getUserId());
    }
}
