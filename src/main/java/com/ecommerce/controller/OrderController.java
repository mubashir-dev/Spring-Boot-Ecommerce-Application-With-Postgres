package com.ecommerce.controller;

import com.ecommerce.dto.OrderCreateDto;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/orders/")
@Tag(name = "Order Controller")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create Order", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create Order Payload", content = @Content(schema = @Schema(implementation = OrderCreateDto.class))))
    @PostMapping
    public ResponseEntity<PageResponse<?>> create(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        return ResponseEntity.ok(orderService.create(orderCreateDto));
    }
}
