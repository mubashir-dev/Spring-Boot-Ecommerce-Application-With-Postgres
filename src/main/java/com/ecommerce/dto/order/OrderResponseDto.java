package com.ecommerce.dto.order;

import com.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private OrderStatus status;

    private Long id;

    private UUID uuid;

    private String address;

    private OrderCustomerDto customer;

    private List<OrderDetailDto> orderDetails;
}
