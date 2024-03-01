package com.loren.em.poc.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

import static com.loren.em.poc.constant.Constants.PUBLIC_SCHEMA;

@Data
@Entity
@Table(schema = PUBLIC_SCHEMA, name = "PRODUCT_METADATA")
public class ProductMetadata {

    @Id
    @Column(name = "PRODUCT_CATEGORY_ID", nullable = false, length = 50)
    private String productCategoryId;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 100)
    private String productName;

    //price/unit
    @Column(name = "PRICE")
    private Double price;

    @Override
    public String toString() {
        return "ProductMetadata{" +
                "productCategoryId='" + productCategoryId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }
}
