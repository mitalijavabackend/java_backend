package com.kiws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SagaResponse {
    private String orderId;
    private String userId;
    private double amount;
    private String sagaStatus;
}