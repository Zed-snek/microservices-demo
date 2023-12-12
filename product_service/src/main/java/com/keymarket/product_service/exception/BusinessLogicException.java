package com.keymarket.product_service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessLogicException extends RuntimeException {

    public BusinessLogicException(String message) {
        super(message);
    }
}
