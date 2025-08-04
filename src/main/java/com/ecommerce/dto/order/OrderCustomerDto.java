package com.ecommerce.dto.order;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerDto {
    private Long id;

    private UUID uuid;

    private String name;

    private String email;

    private String phoneNumber;
}
