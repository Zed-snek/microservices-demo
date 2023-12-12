package com.keymarket.customer_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BalanceDto {

    private String email;
    private float money;

}
