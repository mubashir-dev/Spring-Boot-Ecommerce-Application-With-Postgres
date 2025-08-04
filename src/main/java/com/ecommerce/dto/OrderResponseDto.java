package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private OrderStatus status;

    private Long id;

    private UUID uuid;

    private String address;
}
