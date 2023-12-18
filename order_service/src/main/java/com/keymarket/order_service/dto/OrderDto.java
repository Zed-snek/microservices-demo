package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {

    private Long id;

    private LocalDateTime paid;

    private float price;

    private List<ProductDto> products;

}
