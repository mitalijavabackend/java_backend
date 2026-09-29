package com.kiws.service;


import com.kiws.dto.InventoryReservedEvent;
import com.kiws.dto.PaymentProcessedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private KafkaTemplate<String,Object> kafkaTemplate;

    public InventoryService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "payment-processed",groupId = "saga-group")
    public void onPaymentProcessed(PaymentProcessedEvent event){
        //out of stock scenario
//        boolean inStock=Math.random()>0.5;
        boolean inStock=false;
        if(inStock){
            kafkaTemplate.send("inventory-reserved",new InventoryReservedEvent(event.getOrderId(), true));
        }else {
            kafkaTemplate.send("inventory-failed",new InventoryReservedEvent(event.getOrderId(), false));
        }
    }

}
