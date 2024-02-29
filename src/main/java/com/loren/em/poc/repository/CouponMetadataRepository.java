package com.loren.em.poc.repository;

import com.loren.em.poc.domain.CouponMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponMetadataRepository extends JpaRepository<CouponMetadata, String> {

    Optional<CouponMetadata> findByCouponType(String couponType);
}
