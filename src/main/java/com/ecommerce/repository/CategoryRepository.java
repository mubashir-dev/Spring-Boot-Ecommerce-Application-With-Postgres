package com.ecommerce.repository;

import com.ecommerce.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByTitle(String title);

    @Query(value = "SELECT * FROM categories WHERE deleted = false AND uuid = ?1", nativeQuery = true)
    Optional<Category> findByUuid(UUID uuid);

    @Query(value = "SELECT * FROM categories WHERE deleted = false AND id = ?1", nativeQuery = true)
    Optional<Category> findById(Long id);

    @Query(value = "SELECT * FROM categories WHERE deleted = false", nativeQuery = true)
    Page<Category> findAllActive(Pageable pageable);
}