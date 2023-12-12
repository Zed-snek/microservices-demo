package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Builder
@Getter
@Setter
@ToString
public class NewOrderDto {

    private String email;

    private HashMap<Long, Integer> items; //Long = id of product || Integer = quantity of ordered

}
