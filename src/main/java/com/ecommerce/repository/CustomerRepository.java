package com.ecommerce.repository;

import com.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customers WHERE deleted = false", nativeQuery = true)
    List<Customer> findActive();

    @Query(value = "SELECT * FROM customers where uuid = ? AND deleted = false", nativeQuery = true)
    Optional<Customer> findByUUID(UUID uuid);
}