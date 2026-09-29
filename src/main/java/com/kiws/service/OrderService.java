package com.kiws.service;

import com.kiws.dto.OrderCreatedEvent;
import com.kiws.dto.SagaResponse;
import com.kiws.entity.OrderStatus;
import com.kiws.repository.OrderStatusRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final OrderStatusRepository orderRepo;

    public OrderService(KafkaTemplate<String, Object> kafkaTemplate, OrderStatusRepository orderRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepo = orderRepo;
    }

    public SagaResponse createOrder(String userId,double amount){
        String orderId= UUID.randomUUID().toString();
        //save initial status
        OrderStatus status=new OrderStatus(orderId,userId,amount,"CREATED");
        orderRepo.save(status);

        kafkaTemplate.send("order-created",new OrderCreatedEvent(orderId,userId,amount));
        return new SagaResponse(orderId,userId,amount,"CREATED");
    }

}
