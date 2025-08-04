package com.ecommerce.repository;

import com.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders where uuid=?", nativeQuery = true)
    Optional<Order> findByUUID(UUID uuid);
}


