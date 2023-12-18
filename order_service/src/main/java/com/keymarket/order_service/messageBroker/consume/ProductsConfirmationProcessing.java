package com.keymarket.order_service.messageBroker.consume;

import com.keymarket.order_service.dto.OrderConfirmationPriceDto;
import com.keymarket.order_service.dto.SuccessfulPaymentDto;
import com.keymarket.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ProductsConfirmationProcessing {

    private final OrderService orderService;

    @Bean
    public Consumer<OrderConfirmationPriceDto> consumerOrderConfirmation() {
        return orderService::setPriceAfterConfirmation;
    }

    @Bean
    public Consumer<SuccessfulPaymentDto> consumerSuccessfulPayment() {
        return orderService::confirmPayment;
    }

}
