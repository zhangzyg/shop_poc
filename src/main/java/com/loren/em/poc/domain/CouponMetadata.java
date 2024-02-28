package com.loren.em.poc.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static com.loren.em.poc.constant.Constants.PUBLIC_SCHEMA;

@Data
@Entity
@Table(schema = PUBLIC_SCHEMA, name = "COUPON_METADATA")
public class CouponMetadata {

    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    @Column(name = "COUPON_TYPE_ID", nullable = false, length = 50)
    private String couponTypeId;

    @Column(name = "COUPON_TYPE", nullable = false, length = 100)
    private String couponType;

    //description of how to use coupon, what's the discount rate
    @Column(name = "USAGE")
    private String usage;

    @OneToMany(mappedBy = "couponId", cascade = CascadeType.ALL)
    private List<Coupon> coupons;

    public CouponMetadata() {
        if (StringUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
        }
    }
}
