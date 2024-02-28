package com.loren.em.poc.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

import static com.loren.em.poc.constant.Constants.PUBLIC_SCHEMA;

@Data
@Entity
@Table(schema = PUBLIC_SCHEMA, name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    @Column(name = "PRODUCT_ID", nullable = false, length = 50)
    private String productId;

    @ManyToOne
    @JoinColumn(name = "productCategoryId")
    private ProductMetadata productCategory;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    public Product() {
        if (StringUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
        }
    }
}
