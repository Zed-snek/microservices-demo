package com.keymarket.product_service.messageBroker.produce;

import com.keymarket.product_service.dto.OrderConfirmationPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductProducer {

    private final StreamBridge streamBridge;


    public void sendOrderConfirmationToOrderService(OrderConfirmationPriceDto dto) {
        streamBridge.send("consumerOrderConfirmation-in-0", dto);
    }

}
