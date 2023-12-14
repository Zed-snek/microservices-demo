package com.keymarket.order_service.messageBroker.produce;

import com.keymarket.order_service.dto.NewOrderDto;
import com.keymarket.order_service.dto.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final StreamBridge streamBridge;


    public void sendOrderToProductService(NewOrderDto dto) {
        streamBridge.send("consumerProcessOrder-in-0", dto);
    }

    public void sendPaymentToCustomerService(PaymentRequestDto dto) {
        streamBridge.send("consumerPaymentProcessing-in-0", dto);
    }

}
