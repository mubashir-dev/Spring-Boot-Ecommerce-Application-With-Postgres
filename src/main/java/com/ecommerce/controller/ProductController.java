package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("public/products")
@Tag(name = "Product Controller")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get Products")
    @GetMapping("")
    public ResponseEntity<PageResponse<ProductResponseDto>> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<PageResponse<ProductResponseDto>>(this.productService.find(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @Operation(summary = "Get Product")
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDto> findOne(@PathVariable UUID uuid) {
        ProductResponseDto product = this.productService.findOne(uuid);
        return new ResponseEntity<ProductResponseDto>(product, HttpStatus.OK);
    }
}
