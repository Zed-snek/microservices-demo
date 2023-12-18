package com.keymarket.order_service.service;

import com.keymarket.order_service.dto.*;
import com.keymarket.order_service.entity.Order;
import com.keymarket.order_service.entity.OrderLine;
import com.keymarket.order_service.exception.BusinessLogicException;
import com.keymarket.order_service.feignClients.CustomerClient;
import com.keymarket.order_service.messageBroker.produce.OrderProducer;
import com.keymarket.order_service.feignClients.ProductClient;
import com.keymarket.order_service.repository.OrderLineRepository;
import com.keymarket.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;

    private final ProductClient productClient;
    private final CustomerClient customerClient;
    private final OrderProducer orderProducer;


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



    public LastOrdersInfoDto getLastOrdersInfo(Long customerId) throws ExecutionException, InterruptedException {
        var orders = orderRepository.findFirst10ByCustomerId(customerId);
        var set = orders.stream()
                .flatMap(order -> order.getOrderLines().stream().map(OrderLine::getIdProduct))
                .collect(Collectors.toSet());
        
        CompletableFuture<List<CouponDto>> couponList = CompletableFuture
                .supplyAsync(() -> customerClient.getProductsInfo(customerId));

        var productList = CompletableFuture
                .supplyAsync(() -> productClient.getProductsInfo(set)).get();

        return LastOrdersInfoDto.builder()
                .orders(orders.stream()
                        .map(order -> OrderDto.builder()
                                .id(order.getId())
                                .paid(order.getPaid())
                                .price(order.getPrice())
                                .products(order.getOrderLines()
                                        .stream()
                                        .map(line -> {
                                            var product = productList.stream()
                                                    .filter(dto -> dto.getId().equals(line.getIdProduct()))
                                                    .findFirst()
                                                    .orElseThrow(() -> new BusinessLogicException("Internal error"));
                                            product.setQuantity(line.getPieces());
                                            return product;
                                        })
                                        .toList())
                                .build())
                        .toList())
                .coupons(couponList.get())
                .build();
    }


    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }


}
