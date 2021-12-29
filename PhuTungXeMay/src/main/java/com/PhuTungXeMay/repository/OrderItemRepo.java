package com.PhuTungXeMay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
