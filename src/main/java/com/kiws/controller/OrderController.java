package com.kiws.controller;


import com.kiws.dto.SagaResponse;
import com.kiws.repository.OrderStatusRepository;
import com.kiws.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderStatusRepository orderRepo;

    public OrderController(OrderService orderService, OrderStatusRepository orderRepo) {
        this.orderService = orderService;
        this.orderRepo = orderRepo;
    }

    @PostMapping("/create")
    public SagaResponse createOrder(@RequestParam String userId, @RequestParam double amount) {
        return orderService.createOrder(userId, amount);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get Saga Status for an Order")
    public ResponseEntity<SagaResponse> getOrderStatus(@PathVariable String orderId) {
        return orderRepo.findById(orderId)
                .map(order -> ResponseEntity.ok(new SagaResponse(
                        order.getOrderId(),
                        order.getUserId(),
                        order.getAmount(),
                        order.getStatus()
                )))
                .orElse(ResponseEntity.notFound().build());
    }


}