package com.ecommerce.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBaseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = false, nullable = false)
    private UUID uuid;

    @NotBlank
    private String title;

    @NotBlank
    private String Description;

    @NotNull
    @Positive
    private Long categoryId;

    @NotNull
    @Positive
    private float price;

    @NotNull
    @Positive
    private int quantity;

    private String image;
}
