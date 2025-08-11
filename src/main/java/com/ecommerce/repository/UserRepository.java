package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * from users where email=?", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT * from users where email=?", nativeQuery = true)
    Optional<User> findUserByUsername(String email);

    @Query(value = "SELECT * from users where uuid=?", nativeQuery = true)
    Optional<User> findByUUID(UUID uuid);

    @Query(value = "SELECT * from users " +
            "WHERE username LIKE %:search% " +
            "OR name LIKE %:search% " +
            "OR email LIKE %:search% ORDER BY id", nativeQuery = true
    )
    Page<User> findAll(Pageable pageable, String search);
}
