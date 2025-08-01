package com.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductStatusResponseDto {
    @NotNull
    private UUID uuid;
}
