package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
@Builder
public class OrderDto {

    private LocalDateTime ordered;

    private LocalDateTime paid;

    private float price;

    private Map<Long, Integer> items; //Long = id of product || Integer = quantity of ordered
}
