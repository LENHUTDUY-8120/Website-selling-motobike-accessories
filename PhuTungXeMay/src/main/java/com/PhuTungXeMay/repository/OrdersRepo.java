package com.PhuTungXeMay.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.OrderState;
import com.PhuTungXeMay.entity.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Long>{

	List<Orders> findByOrderState(OrderState orderState,Sort sort);
	
	List<Orders> findByphoneNumber(String phoneNumber,Sort sort);
}
