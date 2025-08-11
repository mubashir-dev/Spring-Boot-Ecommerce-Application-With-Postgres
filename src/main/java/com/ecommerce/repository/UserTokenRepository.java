package com.ecommerce.repository;

import com.ecommerce.enums.UserTokenType;
import com.ecommerce.model.UserToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_tokens WHERE user_id = ? AND user_token_type = ?", nativeQuery = true)
    void removeAllUserTokenByTypeAndUserId(Long userId, UserTokenType userTokenTypeOrdinal);

    @Query(value = "SELECT * FROM user_tokens WHERE token = ?", nativeQuery = true)
    Optional<UserToken> findUserTokenByToken(String token);
}
