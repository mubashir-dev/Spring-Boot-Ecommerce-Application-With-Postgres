package com.ecommerce.controller;

import com.ecommerce.dto.customer.CustomerResponseDto;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("admin/customers")
@RestController
@Tag(name = "Admin Customer Controller")
public class AdminCustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @Operation(summary = "Get Customers")
    public ResponseEntity<PageResponse<CustomerResponseDto>> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.ok(customerService.find(PageRequest.of(page, size == 0 ? 1 : size), search));
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get A Customer")
    public ResponseEntity<CustomerResponseDto> findOne(@PathVariable UUID uuid) {
        return new ResponseEntity<CustomerResponseDto>(customerService.findOne(uuid), HttpStatus.OK);
    }


    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete A Customer")
    public ResponseEntity<String> delete(@PathVariable UUID uuid) {
        return new ResponseEntity<String>(customerService.delete(uuid), HttpStatus.OK);
    }


}
