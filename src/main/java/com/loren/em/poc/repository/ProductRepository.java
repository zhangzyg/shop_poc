package com.loren.em.poc.repository;

import com.loren.em.poc.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findFirstByProductCategoryProductCategoryId(String productType);

    List<Product> findByCartUserUserId(String userId);
}
