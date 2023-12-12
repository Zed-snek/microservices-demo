package com.keymarket.customer_service.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyErrorResponse {

    private int status;
    private String message;
    private long timestamp;


    public MyErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;

        this.timestamp = System.currentTimeMillis();
    }

}