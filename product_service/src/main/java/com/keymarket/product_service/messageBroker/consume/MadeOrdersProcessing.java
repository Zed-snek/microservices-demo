package com.keymarket.product_service.messageBroker.consume;

import com.keymarket.product_service.dto.NewOrderDto;
import com.keymarket.product_service.dto.ProductItemDto;
import com.keymarket.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class MadeOrdersProcessing {

    private final ProductService productService;

    @Bean
    public Consumer<NewOrderDto> consumerProcessOrder() {
        return productService::sumPriceAndCheckAvailabilityOfProducts;
    }








    //testing, no usage
    @Bean
    public Consumer<ProductItemDto> consumerProcessProduct() {
        return (message) -> {
            System.out.println(message);
        };
    }

}
