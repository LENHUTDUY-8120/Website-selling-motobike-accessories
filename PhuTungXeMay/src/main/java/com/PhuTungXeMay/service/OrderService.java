package com.PhuTungXeMay.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.converter.OrdersConverter;
import com.PhuTungXeMay.entity.OrderItem;
import com.PhuTungXeMay.entity.OrderState;
import com.PhuTungXeMay.entity.Orders;
import com.PhuTungXeMay.payload.request.ItemRequest;
import com.PhuTungXeMay.payload.request.OrderRequest;
import com.PhuTungXeMay.payload.response.OrderDetailResponse;
import com.PhuTungXeMay.payload.response.OrderItemResponse;
import com.PhuTungXeMay.payload.response.OrderResponse;
import com.PhuTungXeMay.repository.OrderItemRepo;
import com.PhuTungXeMay.repository.OrdersRepo;
import com.PhuTungXeMay.repository.ProductsRepo;

@Service
public class OrderService {

	@Autowired
	private OrdersRepo ordersRepo;
	@Autowired
	private OrderItemRepo orderItemRepo;
	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
    public JavaMailSender emailSender;
	
	@Transactional
	public OrderResponse createOrder(OrderRequest orderRequest) {
		List<ItemRequest> items = orderRequest.getItems();
		Orders order = new Orders();
		order.setFullname(orderRequest.getFullname());
		order.setPhoneNumber(orderRequest.getPhoneNumber());
		order.setEmail(orderRequest.getEmail());
		order.setAddress(orderRequest.getAddress());
		order.setNote(orderRequest.getNote());
		order.setTotal(orderRequest.getTotal());
		order.setOrderState(OrderState.Delivery);
		Orders order1 = ordersRepo.save(order);
		for (ItemRequest itemRequest : items) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(itemRequest.getPrice());
			orderItem.setQuantity(itemRequest.getQuantity());
			orderItem.setProduct(productsRepo.findById(itemRequest.getProductId()).orElseThrow());
			orderItem.setOrder(order1);
			orderItemRepo.save(orderItem);
		}
		return new OrderResponse(order1.getId());
	}
	
	public List<OrderDetailResponse> getListOrderAd(String status){
		List<Orders> orders = new ArrayList<>();
		if ("DELIVERY".equals(status)) {
			orders = ordersRepo.findByOrderState(OrderState.Delivery,Sort.by("createdDate").descending());
		} else if ("DELIVERED".equals(status)){
			orders = ordersRepo.findByOrderState(OrderState.Delivered,Sort.by("createdDate").descending());
		} else {
			orders = ordersRepo.findByOrderState(OrderState.Not,Sort.by("createdDate").descending());
		}
		return OrdersConverter.toListOrderDetail(orders);
	}
	
	public OrderItemResponse getOrderItemResponse(Long orderId) {
		Orders orders = ordersRepo.findById(orderId).orElseThrow();
		OrderItemResponse orderItemRes = MapperUtil.map(orders, OrderItemResponse.class);
		orderItemRes.setOrderState(orders.getOrderState());
		orderItemRes.setListItem(OrdersConverter.toListItemRes(orders.getListOrderItems()));
		return orderItemRes;
	}
	
	public void setState(Long id, String status) {
		Orders orders = ordersRepo.findById(id).orElseThrow();
		if ("DELIVERY".equals(status)) {
			orders.setOrderState(OrderState.Delivery);
		} else if("DELIVERED".equals(status)) {
			orders.setOrderState(OrderState.Delivered);
		} else {
			orders.setOrderState(OrderState.Not);
		}
		ordersRepo.save(orders);
	}
	
	public List<OrderDetailResponse> getByPhone(String phoneNumber) {
		List<Orders> orders = ordersRepo.findByphoneNumber(phoneNumber, Sort.by("createdDate").descending());
		return OrdersConverter.toListOrderDetail(orders);
	}
	
}
