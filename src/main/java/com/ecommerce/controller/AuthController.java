package com.ecommerce.controller;

import com.ecommerce.dto.auth.AuthForgotPasswordComplete;
import com.ecommerce.dto.auth.AuthForgotPasswordDto;
import com.ecommerce.dto.auth.AuthLoginDto;
import com.ecommerce.dto.auth.AuthResponseDto;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/auth/")
@Tag(name = "Authentication Controller")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    @Operation(summary = "Login A User")
    public ResponseEntity<PageResponse<AuthResponseDto>> login(@Valid @RequestBody AuthLoginDto authLoginDto) {
        return ResponseEntity.ok(authService.login(authLoginDto));
    }

    @PostMapping("/password/restore")
    @Operation(summary = "Restore Password")
    public ResponseEntity<?> restorePassword(@RequestBody AuthForgotPasswordDto authForgotPasswordDto) {
        return ResponseEntity.ok(authService.passwordForgotInitiate(authForgotPasswordDto));
    }

    @PostMapping("/password/restore/{token}")
    @Operation(summary = "Verify Forgot Password")
    public ResponseEntity<?> verifyForgotPassword(@PathVariable() String token,@RequestBody AuthForgotPasswordComplete authForgotPasswordComplete) {
        return ResponseEntity.ok(authService.verifyForgotPassword(token,authForgotPasswordComplete));
    }

}
