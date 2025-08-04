package com.ecommerce.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {
    private Long id;

    private UUID uuid;

    private String title;
}
