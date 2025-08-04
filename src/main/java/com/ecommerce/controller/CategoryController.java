package com.ecommerce.controller;

import com.ecommerce.dto.CategoryDto;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
@Tag(name = "Category Controller")
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get Categories")
    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> find() {
        List<CategoryDto> categories = this.categoryService.find();
        return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
    }

    @Operation(summary = "Get Category")
    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryDto> findOne(@PathVariable UUID uuid) {
        CategoryDto category = this.categoryService.findOne(uuid);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }

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
    public ResponseEntity<CategoryDto> delete(@PathVariable UUID uuid) {
        CategoryDto newCategory = this.categoryService.delete(uuid);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }
}

