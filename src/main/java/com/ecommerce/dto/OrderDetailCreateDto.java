package com.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailCreateDto {
    @NotNull
    @Positive
    @Min(1)
    private Long productId;

    @NotNull
    @Min(1)
    private int quantity;

    private float price;

    private float totalPrice;

    private Long orderId;
}
