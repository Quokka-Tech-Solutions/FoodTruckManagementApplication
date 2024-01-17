package com.quokkatechsolutions.foodTruckManagement.order.infrastructure.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.quokkatechsolutions.foodTruckManagement.order.infrastructure.representation.OrderRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/orders")
    public OrderRepresentation greeting(@RequestParam(value = "name", defaultValue = "Order Name goes here") String name) {
        return new OrderRepresentation(counter.incrementAndGet(), String.format(template, name));
    }
}
