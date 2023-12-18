package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CouponDto {

    private Long id;
    private int discountPercentage;
    private float maxPrice;
}
