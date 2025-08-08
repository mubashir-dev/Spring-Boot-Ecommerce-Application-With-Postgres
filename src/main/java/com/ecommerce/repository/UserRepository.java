package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * from users where email=?", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT * from users where username=?", nativeQuery = true)
    Optional<User> findUserByUsername(String username);
}
