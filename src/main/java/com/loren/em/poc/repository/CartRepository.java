package com.loren.em.poc.repository;

import com.loren.em.poc.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUserUserId(String userId);
}
