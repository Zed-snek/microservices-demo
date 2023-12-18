package com.keymarket.customer_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponDto {

    private Long id;
    private int discountPercentage;
    private float maxPrice;
}
