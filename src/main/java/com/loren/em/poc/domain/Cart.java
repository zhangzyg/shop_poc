package com.loren.em.poc.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static com.loren.em.poc.constant.Constants.PUBLIC_SCHEMA;

@Data
@Entity
@Table(schema = PUBLIC_SCHEMA, name = "CART")
public class Cart {

    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    @Column(name = "CART_ID", nullable = false, length = 50)
    private String cartId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<Product> productList;

    @OneToMany(mappedBy = "couponId", cascade = CascadeType.ALL)
    private List<Coupon> couponList;

    public Cart() {
        if (StringUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
        }
    }
}
