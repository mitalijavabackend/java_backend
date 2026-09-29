package com.kiws.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus {

    @Id
    private String orderId;
    private String userId;
    private double amount;
    private String status; // CREATED, PAID, RESERVED, SHIPPED, FAILED, REFUNDED
}
