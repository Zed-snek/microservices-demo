package com.keymarket.product_service.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MySuccessResponse {

    private final int status = 200;
    private String message;
    private long timestamp;

    public MySuccessResponse(String message) {
        this.message = message;

        this.timestamp = System.currentTimeMillis();
    }

}
