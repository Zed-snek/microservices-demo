package com.keymarket.order_service.controller;

import com.keymarket.order_service.dto.LastOrdersInfoDto;
import com.keymarket.order_service.dto.NewOrderDto;
import com.keymarket.order_service.dto.OrderDto;
import com.keymarket.order_service.response.MySuccessResponse;
import com.keymarket.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<MySuccessResponse> makeOrder(@RequestBody NewOrderDto dto) {

        Long id = orderService.makeOrder(dto);

        var response = new MySuccessResponse("Order is made, its id: " + id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/pay_order/{id}")
    public ResponseEntity<MySuccessResponse> payOrder(@PathVariable Long id) {
        orderService.payOrder(id);

        var response = new MySuccessResponse("Request to pay is sent");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/info/{customerId}")
    public LastOrdersInfoDto getLastOrdersInfo(@PathVariable Long customerId) throws ExecutionException, InterruptedException {
        return orderService.getLastOrdersInfo(customerId);
    }

}
