package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products WHERE deleted = false AND title = ?1", nativeQuery = true)
    Optional<Product> existsByTitle(String title);

    @Query(value = "SELECT * FROM products WHERE deleted = false AND uuid = ?1", nativeQuery = true)
    Optional<Product> findByUUID(UUID uuid);

    @Query(value = "SELECT * FROM products WHERE deleted = false", nativeQuery = true)
    List<Product> findAllActive();
}