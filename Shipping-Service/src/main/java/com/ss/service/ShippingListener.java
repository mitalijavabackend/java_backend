package com.ss.service;

import com.saga.dto.SagaCommand;
import com.saga.dto.SagaReply;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingListener {

    private final KafkaTemplate<String, SagaReply> kafkaTemplate;

    @KafkaListener(topics = "order-saga-commands", groupId = "shipping-service")
    public void onShippingCommand(SagaCommand command) {
        if (!"SHIPPING".equals(command.getStep())) return;

        System.out.println("🚚 Shipping order: " + command.getOrderId());

        SagaReply reply = new SagaReply(
                command.getOrderId(),
                "SHIPPING",
                true,
                "Shipped successfully"
        );

        kafkaTemplate.send("order-saga-replies", reply);
    }
}
