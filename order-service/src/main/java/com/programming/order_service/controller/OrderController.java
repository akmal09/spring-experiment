package com.programming.order_service.controller;

import com.programming.order_service.dto.OrderRequest;
import com.programming.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.testcontainers.shaded.org.apache.commons.lang3.concurrent.CircuitBreaker;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderServiceImpl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Placing Order");
        orderServiceImpl.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
}
