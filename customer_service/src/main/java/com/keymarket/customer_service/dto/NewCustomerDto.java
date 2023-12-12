package com.keymarket.customer_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NewCustomerDto {

    private String username;
    private String email;
    private String password;

}
