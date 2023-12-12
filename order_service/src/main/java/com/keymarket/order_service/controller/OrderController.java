package com.keymarket.order_service.controller;

import com.keymarket.order_service.dto.NewOrderDto;
import com.keymarket.order_service.response.MySuccessResponse;
import com.keymarket.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("")
    public ResponseEntity<MySuccessResponse> makeOrder(@RequestBody NewOrderDto dto) {
        System.out.println(dto);
        var response = new MySuccessResponse("Order is made");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
