package com.ecommerce.controller;

import com.ecommerce.dto.auth.AuthLoginDto;
import com.ecommerce.dto.auth.AuthRegisterDto;
import com.ecommerce.dto.auth.AuthResponseDto;
import com.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/auth/")
@Tag(name = "Authentication Controller")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    @Operation(summary = "Login A User")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthLoginDto authLoginDto) {
        return new ResponseEntity<AuthResponseDto>(authService.login(authLoginDto), HttpStatus.OK);
    }

    @PostMapping("register")
    @Operation(summary = "Register A New User")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody AuthRegisterDto authRegisterDto) {
        return new ResponseEntity<AuthResponseDto>(authService.register(authRegisterDto), HttpStatus.CREATED);
    }
}
