package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDto {


    private Long id;
    private int quantity;
    private String name;
    private String image;

}
