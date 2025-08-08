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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("admin/categories")
@Tag(name = "Admin Category Controller")
public class AdminCategoryController {
    private static final Logger log = LoggerFactory.getLogger(AdminCategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create Category")
    @PostMapping("")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto newCategory = this.categoryService.save(categoryDto);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Category")
    @PutMapping("/{uuid}")
    public ResponseEntity<CategoryDto> update(@PathVariable UUID uuid, @RequestBody CategoryDto categoryDto) {
        CategoryDto newCategory = this.categoryService.update(uuid, categoryDto);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }

    @Operation(summary = "Delete Category")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<PageResponse<String>> delete(@PathVariable UUID uuid) {
        return new ResponseEntity<>(this.categoryService.delete(uuid), HttpStatus.OK);
    }
}

