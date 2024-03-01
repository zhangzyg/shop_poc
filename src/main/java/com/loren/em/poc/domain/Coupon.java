package com.loren.em.poc.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

import static com.loren.em.poc.constant.Constants.PUBLIC_SCHEMA;

@Data
@Entity
@Table(schema = PUBLIC_SCHEMA, name = "COUPON")
public class Coupon {

    @Id
    @Column(name = "COUPON_ID", nullable = false, length = 50)
    private String couponId;

    @ManyToOne
    @JoinColumn(name = "couponTypeId")
    private CouponMetadata couponMetadata;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    public Coupon() {
        if(Objects.isNull(couponId)) {
            couponId = UUID.randomUUID().toString();
        }

    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId='" + couponId + '\'' +
                '}';
    }
}
