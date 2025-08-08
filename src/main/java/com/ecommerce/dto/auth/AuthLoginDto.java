package com.ecommerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthLoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
