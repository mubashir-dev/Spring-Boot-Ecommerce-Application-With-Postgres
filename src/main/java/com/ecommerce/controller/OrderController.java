package com.ecommerce.controller;

import com.ecommerce.dto.OrderCreateDto;
import com.ecommerce.dto.order.OrderResponseDto;
import com.ecommerce.dto.order.OrderUpdateStatusDto;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name = "Order Controller")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get Orders")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> find() {
        return new ResponseEntity<List<OrderResponseDto>>(orderService.find(), HttpStatus.OK);
    }

    @Operation(summary = "Get Order")
    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponseDto> findOne(@PathVariable UUID uuid) {
        return new ResponseEntity<OrderResponseDto>(orderService.findOne(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Create Order", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create Order Payload", content = @Content(schema = @Schema(implementation = OrderCreateDto.class))))
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        return ResponseEntity.ok(orderService.create(orderCreateDto));
    }

    @Operation(summary = "Update Order Status", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Update Order Status Payload", content = @Content(schema = @Schema(implementation = OrderUpdateStatusDto.class))))
    @PostMapping("/status/{uuid}")
    public ResponseEntity<String> updateStatus(@PathVariable UUID uuid, @Valid @RequestBody OrderUpdateStatusDto orderUpdateStatusDto) {
        return ResponseEntity.ok(orderService.updateStatus(uuid, orderUpdateStatusDto));
    }

}
