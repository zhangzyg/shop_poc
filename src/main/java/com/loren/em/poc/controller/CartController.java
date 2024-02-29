package com.loren.em.poc.controller;

import com.loren.em.poc.dto.CartDto;
import com.loren.em.poc.dto.ProductDto;
import com.loren.em.poc.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.loren.em.poc.constant.Constants.API_V1_BASE_PATH;
import static com.loren.em.poc.constant.Constants.CART_CONTROLLER_PATH;

@RestController
@RequestMapping(path = API_V1_BASE_PATH + CART_CONTROLLER_PATH)
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public CartDto getCartInfo(@RequestParam("user") String userId) {
        return cartService.getCartInfo(userId);
    }
}
