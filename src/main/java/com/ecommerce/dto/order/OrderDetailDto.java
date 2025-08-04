package com.ecommerce.dto.order;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;

    private UUID uuid;

    private int quantity;

    private float price;

    private float totalPrice;

    private OrderProductDto product;
}
