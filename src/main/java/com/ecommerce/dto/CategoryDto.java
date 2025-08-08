package com.ecommerce.dto;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {
    @Nullable()
    private Long id;

    @Nullable()
    private UUID uuid;

    @NotEmpty(message = "Title field is required")
    private String title;

    @NotEmpty(message = "Description field is required")
    private String description;

    @Nullable()
    private String image;
}
