package com.loren.em.poc.repository;

import com.loren.em.poc.domain.ProductMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductMetadataRepository extends JpaRepository<ProductMetadata, String> {

    Optional<ProductMetadata> findByProductCategoryId(String productCategoryId);
}
