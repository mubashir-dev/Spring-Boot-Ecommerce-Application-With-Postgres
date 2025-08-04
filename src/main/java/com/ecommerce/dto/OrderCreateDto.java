package com.ecommerce.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {
    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<OrderDetailCreateDto> orderDetail;

    private String notes = "N/A";
}
