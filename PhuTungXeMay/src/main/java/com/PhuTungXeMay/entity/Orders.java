package com.PhuTungXeMay.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders extends BaseEntity{

	@Column
	private String fullname;
	@Column
	private String phoneNumber;
	@Column
	private String email;
	@Column
	private String address;
	@Column
	private String note;
	@Column
	private int total;
	@Column
	@Enumerated(EnumType.STRING)
	private OrderState orderState;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> listOrderItems = new ArrayList<>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getOrderState() {
		return orderState.getValue();
	}

	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}

	public List<OrderItem> getListOrderItems() {
		return listOrderItems;
	}

	public void setListOrderItems(List<OrderItem> listOrderItems) {
		this.listOrderItems = listOrderItems;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
