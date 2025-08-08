package com.ecommerce.dto.customer;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CustomerResponseDto {
    private Long id;

    private UUID uuid;

    private String name;

    private String email;

    private String phoneNumber;
}
