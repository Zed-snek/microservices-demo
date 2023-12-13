package com.keymarket.customer_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SuccessfulPaymentDto {

    private float paidFromBalance;
    private float paidFromOtherSource;

    private Long orderId;
    private Long customerId;

}
