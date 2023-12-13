package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class OrderConfirmationPriceDto {

    private Long orderId;
    private float price;

}
