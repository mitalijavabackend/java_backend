package com.saga.dto;

import java.util.Map;

import lombok.*;

@Data
public class SagaCommand {
	
	private String orderId;
	private String step;
	private Map<String,Object> payload;

	public SagaCommand() {
	}

	public SagaCommand(String orderId, String step, Map<String, Object> payload) {
		this.orderId = orderId;
		this.step = step;
		this.payload = payload;
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

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "SagaCommand{" +
				"orderId='" + orderId + '\'' +
				", step='" + step + '\'' +
				", payload=" + payload +
				'}';
	}

}
