package com.keymarket.order_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentRequestDto {

    private Long costumerId;
    private Long orderId;
    private float price;

}
