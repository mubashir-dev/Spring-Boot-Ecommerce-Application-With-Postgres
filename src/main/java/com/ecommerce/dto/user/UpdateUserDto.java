package com.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserDto {
    private String name;

    @Email
    private String email;

    private String username;

    private String password;
}
