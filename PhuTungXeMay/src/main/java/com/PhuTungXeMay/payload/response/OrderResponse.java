package com.PhuTungXeMay.payload.response;

public class OrderResponse {

	private Long orderId;

	public OrderResponse(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
