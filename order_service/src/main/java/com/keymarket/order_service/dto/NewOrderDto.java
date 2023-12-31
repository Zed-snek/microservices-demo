package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class NewOrderDto {

    private Long customerId;
    private Long orderId;

    private Map<Long, Integer> items; //Long = id of product || Integer = quantity of ordered

}
