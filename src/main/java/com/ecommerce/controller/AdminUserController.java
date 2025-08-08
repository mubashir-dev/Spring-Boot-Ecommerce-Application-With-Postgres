package com.ecommerce.controller;

import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.user.CreateUserDto;
import com.ecommerce.dto.user.ResponseUserDto;
import com.ecommerce.dto.user.UpdateUserDto;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("admin/users")
@Tag(name = "Admin User Controller")
public class AdminUserController {

    @Autowired
    UserService userService;

    @PostMapping
    @Operation(summary = "Create A User")
    public ResponseEntity<PageResponse<ResponseUserDto>> save(@Valid @RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(userService.save(createUserDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get Users")
    public ResponseEntity<PageResponse<ResponseUserDto>> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(this.userService.find(PageRequest.of(page, size == 0 ? 1 : size), search), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get A User")
    public ResponseEntity<PageResponse<ResponseUserDto>> findOne(@PathVariable UUID uuid) {
        return ResponseEntity.ok(this.userService.findOne(uuid));
    }

    @PatchMapping("/{uuid}")
    @Operation(summary = "Update A User")
    public ResponseEntity<PageResponse<?>> update(@RequestBody UpdateUserDto updateUserDto, @PathVariable UUID uuid) {
        return ResponseEntity.ok(this.userService.update(updateUserDto,uuid));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete A User")
    public ResponseEntity<PageResponse<?>> delete(@PathVariable UUID uuid) {
        return ResponseEntity.ok(this.userService.delete(uuid));
    }

    @GetMapping("/me")
    @Operation(summary = "Get Current User")
    public ResponseEntity<PageResponse<ResponseUserDto>> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return ResponseEntity.ok(this.userService.findByUsername(userDetails.getUsername()));
        }
        return ResponseEntity.ok(null);
    }

}
