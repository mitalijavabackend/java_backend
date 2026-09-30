package com.os.service;

import com.os.entity.OrderSaga;
import com.os.repository.OrderSagaRepository;
import com.saga.dto.SagaCommand;
import com.saga.dto.SagaReply;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderSagaService {

    private final KafkaTemplate<String, SagaCommand> kafkaTemplate;
    private final OrderSagaRepository orderSagaRepository;

    public String startOrder(String orderId) {
        orderSagaRepository.save(new OrderSaga(orderId, "PAYMENT", "IN_PROGRESS"));
        SagaCommand command = new SagaCommand(orderId, "PAYMENT", Map.of("amount", 500.0));
        kafkaTemplate.send("order-saga-commands", command);
        return "Saga started with ID: " + orderId;
    }

    @KafkaListener(topics = "order-saga-replies", groupId = "order-orchestrator")
    public void onSagaReply(SagaReply reply) {
        System.out.println("Received reply: " + reply);
        OrderSaga saga = orderSagaRepository.findById(reply.getOrderId()).orElseThrow();

        if (!reply.isSuccess()) {
            saga.setStatus("FAILED at " + reply.getStep());
            orderSagaRepository.save(saga);
            return;
        }

        switch (reply.getStep()) {
            case "PAYMENT" -> {
                saga.setCurrentStep("INVENTORY");
                orderSagaRepository.save(saga);
                kafkaTemplate.send("order-saga-commands", new SagaCommand(reply.getOrderId(), "INVENTORY", Map.of("item", "product1")));
            }
            case "INVENTORY" -> {
                saga.setCurrentStep("SHIPPING");
                orderSagaRepository.save(saga);
                kafkaTemplate.send("order-saga-commands", new SagaCommand(reply.getOrderId(), "SHIPPING", Map.of("address", "XYZ")));
            }
            case "SHIPPING" -> {
                saga.setCurrentStep("SHIPPING");
                saga.setStatus("COMPLETED");
                orderSagaRepository.save(saga);
            }
        }
    }

    public OrderSaga getSaga(String orderId) {
        return orderSagaRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Saga not found"));
    }
}
