package com.keymarket.product_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Builder
@Getter
@Setter
@ToString
public class NewOrderDto {

    private Long customerId;
    private Long orderId;

    private Map<Long, Integer> items; //Long = id of product || Integer = quantity of ordered

}