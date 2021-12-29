package com.PhuTungXeMay.entity;

public enum OrderState {

	Not("NOT")
	, Delivery("DELIVERY")
	, Delivered("DELIVERED");

	private final String value;
	
	private OrderState(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
