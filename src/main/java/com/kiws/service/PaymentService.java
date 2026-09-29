package com.kiws.service;

import com.kiws.dto.InventoryReservedEvent;
import com.kiws.dto.OrderCreatedEvent;
import com.kiws.dto.PaymentProcessedEvent;
import com.kiws.repository.OrderStatusRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final OrderStatusRepository orderRepo;

    public PaymentService(KafkaTemplate<String, Object> kafkaTemplate, OrderStatusRepository orderRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepo = orderRepo;
    }

    @KafkaListener(topics = "order-created",groupId = "saga-group")
    public void onOrderCreated(OrderCreatedEvent event){
        boolean success=true;

        orderRepo.findById(event.getOrderId()).ifPresent(order -> {
                order.setStatus("PAID");
        orderRepo.save(order);
        });

        kafkaTemplate.send("payment-processed",new PaymentProcessedEvent(event.getOrderId(), success));
    }

    @KafkaListener(topics = "inventory-failed", groupId = "saga-group")
    public void onInventoryFailed(InventoryReservedEvent event) {
        // Compensating Action: Refund
        System.out.println("❌ Inventory failed. Refunding payment...");

        orderRepo.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus("REFUNDED");
            orderRepo.save(order);
        });

        kafkaTemplate.send("payment-refunded", new PaymentProcessedEvent(event.getOrderId(), true));
    }


}
