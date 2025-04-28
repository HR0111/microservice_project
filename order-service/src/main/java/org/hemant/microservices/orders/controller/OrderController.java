package org.hemant.microservices.orders.controller;


import lombok.RequiredArgsConstructor;
import org.hemant.microservices.orders.DTO.OrderRequest;
import org.hemant.microservices.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {

        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }


}
