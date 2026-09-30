package com.ps.service;

import com.saga.dto.SagaCommand;
import com.saga.dto.SagaReply;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentListener {

    private final KafkaTemplate<String, SagaReply> kafkaTemplate;

    @KafkaListener(topics = "order-saga-commands", groupId = "payment-service")
    public void onPaymentCommand(SagaCommand command) {
        if (!"PAYMENT".equals(command.getStep())) return;

        System.out.println("💰 Processing payment for order: " + command.getOrderId());

        // Simulate successful payment
        boolean paymentSuccess = true;

        SagaReply reply = new SagaReply(
                command.getOrderId(),
                "PAYMENT",
                paymentSuccess,
                paymentSuccess ? "Payment Successful" : "Payment Failed"
        );

        kafkaTemplate.send("order-saga-replies", reply);
    }
}
