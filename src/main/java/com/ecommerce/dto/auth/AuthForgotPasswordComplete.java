package com.ecommerce.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthForgotPasswordComplete {
    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;
}
