package com.os.controller;

import com.os.entity.OrderSaga;
import com.os.service.OrderSagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderSagaService orderSagaService;

    @PostMapping("/start")
    public ResponseEntity<String> startSaga(@RequestParam String orderId) {
        return ResponseEntity.ok(orderSagaService.startOrder(orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderSaga> getSagaStatus(@PathVariable String orderId) {
        return ResponseEntity.ok(orderSagaService.getSaga(orderId));
    }
}
