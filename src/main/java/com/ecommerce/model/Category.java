package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "categories")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = false, nullable = false)
    private UUID uuid;

    @Column(unique = true)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String image;

    @Column(nullable = true)
    private Boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        this.setUuid(java.util.UUID.randomUUID());
    }
}
