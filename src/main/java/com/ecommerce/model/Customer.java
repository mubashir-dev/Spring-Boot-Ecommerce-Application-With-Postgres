package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "customers")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = false, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private Boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        this.setUuid(java.util.UUID.randomUUID());
    }
}
