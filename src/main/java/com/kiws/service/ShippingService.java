package com.kiws.service;

import com.kiws.dto.InventoryReservedEvent;
import com.kiws.repository.OrderStatusRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    private final OrderStatusRepository orderRepo;

    public ShippingService(OrderStatusRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @KafkaListener(topics = "inventory-reserved", groupId = "saga-group")
    public void onInventoryReserved(InventoryReservedEvent event) {
        if (event.isSuccess()) {
            System.out.println("Order shipped: " + event.getOrderId());
            orderRepo.findById(event.getOrderId()).ifPresent(order -> {
                order.setStatus("SHIPPED");
                orderRepo.save(order);
            });
        }
    }
}
