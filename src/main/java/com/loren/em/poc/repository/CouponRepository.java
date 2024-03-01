package com.loren.em.poc.repository;

import com.loren.em.poc.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, String> {

    List<Coupon> findByCartUserUserId(String userId);
}
