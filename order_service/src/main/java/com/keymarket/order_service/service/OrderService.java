package com.keymarket.order_service.service;

import com.keymarket.order_service.repository.OrderLineRepository;
import com.keymarket.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;

    /* get order data:
        HTTP/RPC: get order, then fetch the products from ProductService to get name, image and id

     */


    /* make order:
        HTTP/RPC: send to product service to check availability of each product
        if everything is okay, creates an order in database
        QUEUE: then reduces number of availability in ProductService
    */



    /* pay order
        Make order Paid in Database (create timestamp)

        HTTP/RPC:send to CustomerService request to minus the balance, then minus money on the balance.

        Then show to user how much money has he paid from balance and from card:
            CustomerService shows in console: "Sent request to email service to send the keys of bought items"

     */




}
