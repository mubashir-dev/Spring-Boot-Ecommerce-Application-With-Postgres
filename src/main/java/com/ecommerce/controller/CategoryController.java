package com.ecommerce.controller;

import com.ecommerce.dto.CategoryDto;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("public/categories")
@Tag(name = "Category Controller")
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get Categories")
    @GetMapping("")
    public ResponseEntity<PageResponse<CategoryDto>> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.ok(this.categoryService.find(PageRequest.of(page, size == 0 ? 1 : size),search));
    }

    @Operation(summary = "Get Category")
    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryDto> findOne(@PathVariable UUID uuid) {
        CategoryDto category = this.categoryService.findOne(uuid);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }
}

