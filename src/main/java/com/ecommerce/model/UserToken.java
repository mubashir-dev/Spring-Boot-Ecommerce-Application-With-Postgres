package com.ecommerce.model;

import com.ecommerce.enums.UserTokenType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "user_tokens")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Date expiresAt;

    @Column(nullable = false)
    private UserTokenType userTokenType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,unique = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.setUuid(java.util.UUID.randomUUID());
    }
}
