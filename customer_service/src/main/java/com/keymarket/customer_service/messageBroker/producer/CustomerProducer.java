package com.keymarket.customer_service.messageBroker.producer;

import com.keymarket.customer_service.dto.SuccessfulPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProducer {

    private final StreamBridge streamBridge;


    public void sendSuccessfulPaymentToOrderService(SuccessfulPaymentDto dto) {
        streamBridge.send("consumerSuccessfulPayment-in-0", dto);
    }

}
