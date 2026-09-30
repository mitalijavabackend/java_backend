package com.os.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaga {
    @Id
    private String orderId;
    private String currentStep; // PAYMENT, INVENTORY, SHIPPING
    private String status;      // IN_PROGRESS, FAILED, COMPLETED
}