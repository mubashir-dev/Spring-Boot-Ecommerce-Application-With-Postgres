package com.ecommerce.dto.order;

import com.ecommerce.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateStatusDto {
    @NotNull
    private OrderStatus status;
}
