package com.keymarket.order_service.service;

import com.keymarket.order_service.dto.*;
import com.keymarket.order_service.entity.Order;
import com.keymarket.order_service.entity.OrderLine;
import com.keymarket.order_service.exception.BusinessLogicException;
import com.keymarket.order_service.messageBroker.produce.OrderProducer;
import com.keymarket.order_service.repository.OrderLineRepository;
import com.keymarket.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;

    private final OrderProducer orderProducer;


    //private final WebClient webClient = WebClient.create();

    //@Value("${api.productservice}")
    //private String productServiceApiLink;



    @Transactional
    public Long makeOrder(NewOrderDto dto) {
        var order = Order.builder()
                .ordered(LocalDateTime.now())
                .customerId(dto.getCustomerId())
                .build();
        var updatedOrder = orderRepository.save(order);
        dto.setOrderId(updatedOrder.getId());

        orderProducer.sendOrderToProductService(dto);

        dto.getItems().forEach((key, value) -> {
                    var line = OrderLine.builder()
                            .order(updatedOrder)
                            .idProduct(key)
                            .pieces(value)
                            .build();
                    orderLineRepository.save(line);
                });
        return updatedOrder.getId();
    }


    public void setPriceAfterConfirmation(OrderConfirmationPriceDto dto) {
        System.out.println("setPriceAfterConfirmation(): " + dto);
        var order = findById(dto.getOrderId());
        order.setPrice(dto.getPrice());
        orderRepository.save(order);
    }


    public void payOrder(Long id) {
        var order = findById(id);

        if (order.getPrice() <= 0)
            throw new BusinessLogicException("Price hasn't been calculated yet");

        var dto = PaymentRequestDto.builder()
                .orderId(order.getId())
                .costumerId(order.getCustomerId())
                .price(order.getPrice())
                .build();
        orderProducer.sendPaymentToCustomerService(dto);
    }

    public void confirmPayment(SuccessfulPaymentDto dto) {
        var order = findById(dto.getOrderId());
        order.setPaid(LocalDateTime.now());
        orderRepository.save(order);

        System.out.println("Order paid: \n" +
                "- Paid from balance: " + dto.getPaidFromBalance() + "\n" +
                "- Paid from other source: " + dto.getPaidFromOtherSource());
    }

    public OrderDto getOrderDataById(Long id) {
        var order = findById(id);

        return OrderDto.builder()
                .ordered(order.getOrdered())
                .paid(order.getPaid())
                .price(order.getPrice())
                .items(order.getOrderLines()
                        .stream()
                        .collect(Collectors.toMap(OrderLine::getId, OrderLine::getPieces))
                )
                .build();
    }


    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }


}
