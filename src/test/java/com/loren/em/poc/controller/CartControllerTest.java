package com.loren.em.poc.controller;

import com.loren.em.poc.dto.CartDto;
import com.loren.em.poc.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class CartControllerTest {

    @InjectMocks
    private CartController controller;

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    void getCartInfoTest() {
        //arrangement
        String userId = "userA";
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(1.0d);
        cartDto.setDisCountPrice(0.8d);
        when(cartService.getCartInfo(userId)).thenReturn(cartDto);

        //action
        CartDto cartInfo = controller.getCartInfo(userId);

        //assert
        assertEquals(1.0d, cartInfo.getTotalPrice());
        assertEquals(0.8d, cartInfo.getDisCountPrice());
    }
}
