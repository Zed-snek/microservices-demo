package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LastOrdersInfoDto {

    private List<CouponDto> coupons;
    private List<OrderDto> orders;


}
