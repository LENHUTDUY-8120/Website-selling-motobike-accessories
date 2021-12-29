package com.PhuTungXeMay.payload.request;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

	private String fullname;
	private String phoneNumber;
	private String email;
	private String address;
	private String note;
	private int total;
	List<ItemRequest> items = new ArrayList<>();
	
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<ItemRequest> getItems() {
		return items;
	}
	public void setItems(List<ItemRequest> items) {
		this.items = items;
	} 
}
