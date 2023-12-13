package com.keymarket.customer_service.messageBroker.consume;

import com.keymarket.customer_service.dto.PaymentRequestDto;
import com.keymarket.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class PaymentProcessing {

    private final CustomerService customerService;

    @Bean
    public Consumer<PaymentRequestDto> consumerPaymentProcessing() {
        return customerService::processPaymentOfOrder;
    }


}
