package com.ecommerce.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseUserDto {
    private Long id;

    private UUID uuid;

    private String name;

    private String username;

    private String email;
}
