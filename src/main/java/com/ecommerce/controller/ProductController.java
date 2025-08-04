package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product Controller")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get Products")
    @GetMapping("")
    public ResponseEntity<List<ProductResponseDto>> find() {
        List<ProductResponseDto> products = this.productService.find();
        return new ResponseEntity<List<ProductResponseDto>>(products, HttpStatus.OK);
    }

    @Operation(summary = "Get Product")
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDto> findOne(@PathVariable UUID uuid) {
        ProductResponseDto product = this.productService.findOne(uuid);
        return new ResponseEntity<ProductResponseDto>(product, HttpStatus.OK);
    }

    @Operation(summary = "Create Product")
    @PostMapping("")
    public ResponseEntity<ProductStatusResponseDto> create(@Valid @RequestBody ProductCreateDto productCreateDto) {
        ProductStatusResponseDto productStatusResponseDto = this.productService.save(productCreateDto);
        return new ResponseEntity<>(productStatusResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Product")
    @PutMapping("/{uuid}")
    public ResponseEntity<ProductStatusResponseDto> update(@PathVariable UUID uuid, @RequestBody ProductUpdateDto productUpdateDto) {
        ProductStatusResponseDto updateProduct = this.productService.update(uuid, productUpdateDto);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @Operation(summary = "Delete Product")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<ProductStatusResponseDto> delete(@PathVariable UUID uuid) {
        return new ResponseEntity<>(this.productService.delete(uuid), HttpStatus.OK);
    }
}
