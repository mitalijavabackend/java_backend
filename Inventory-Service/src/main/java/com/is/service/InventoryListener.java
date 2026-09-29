package com.is.service;

import com.saga.dto.SagaCommand;
import com.saga.dto.SagaReply;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class InventoryListener {

    private final KafkaTemplate<String, SagaReply> kafkaTemplate;

    @KafkaListener(topics = "order-saga-commands", groupId = "inventory-service")
    public void onInventoryCommand(SagaCommand command) {
        if (!"INVENTORY".equals(command.getStep())) return;

        System.out.println("📦 Checking inventory for order: " + command.getOrderId());

        // Simulate availability randomly
        boolean inStock = new Random().nextBoolean();

        SagaReply reply = new SagaReply(
                command.getOrderId(),
                "INVENTORY",
                inStock,
                inStock ? "Item in stock" : "Out of stock"
        );

        kafkaTemplate.send("order-saga-replies", reply);
    }
}
