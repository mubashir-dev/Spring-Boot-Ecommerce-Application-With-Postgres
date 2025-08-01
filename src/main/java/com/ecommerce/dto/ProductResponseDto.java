package com.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto extends ProductBaseDto {
    private CategoryDto category;
}
