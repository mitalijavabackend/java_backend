package com.saga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SagaReply {
    private String orderId;
    private String step;
    private boolean success;
    private String message;

    public SagaReply() {
    }

    public SagaReply(String orderId,
                     String step,
                     boolean success,
                     String message) {

        this.orderId = orderId;
        this.step = step;
        this.success = success;
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SagaReply{" +
                "orderId='" + orderId + '\'' +
                ", step='" + step + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
