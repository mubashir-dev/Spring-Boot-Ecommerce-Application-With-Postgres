package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "products")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int quantity = 0;

    private String image;

    @Column(nullable = true)
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false,unique = false)
    private Category category;

    @PrePersist
    protected void onCreate() {
        this.setUuid(java.util.UUID.randomUUID());
    }
}
