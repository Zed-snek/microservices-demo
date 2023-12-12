package com.keymarket.customer_service.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CustomerDto {

    private String username;
    private float balance;

}
