package com.keymarket.customer_service.controller;

import com.keymarket.customer_service.dto.CouponDto;
import com.keymarket.customer_service.dto.CustomerDto;
import com.keymarket.customer_service.dto.NewCustomerDto;
import com.keymarket.customer_service.dto.BalanceDto;
import com.keymarket.customer_service.response.MySuccessResponse;
import com.keymarket.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("")
    public ResponseEntity<MySuccessResponse> registerCustomer(@RequestBody NewCustomerDto dto) {
        customerService.registerCustomer(dto);

        var response = new MySuccessResponse("Customer is registered");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/get_info/{email}")
    public CustomerDto getCustomer(@PathVariable String email) {
        return customerService.getCustomerInfo(email);
    }


    @PutMapping("/top_up_balance")
    public ResponseEntity<MySuccessResponse> topUpBalance(@RequestBody BalanceDto dto) {
        customerService.topUpBalance(dto);

        var response = new MySuccessResponse("Balance is updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/get_balance/{email}")
    public String getBalance(@PathVariable String email) {
        float balance = customerService.getBalanceOfCustomer(email);
        return String.valueOf(balance);
    }


    @GetMapping(value = "/coupon/{customerId}")
    public List<CouponDto> getProductsInfo(@PathVariable Long customerId) {
        return customerService.getCouponsByCustomerId(customerId);
    }

}
