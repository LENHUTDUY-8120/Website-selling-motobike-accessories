package com.PhuTungXeMay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.payload.response.AdminAnalysisResponse;
import com.PhuTungXeMay.repository.OrdersRepo;
import com.PhuTungXeMay.repository.ProductsRepo;

@Service
public class AnalysisService {

	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
	private OrdersRepo ordersRepo;
	
	public AdminAnalysisResponse getAllInfo() {
		AdminAnalysisResponse adrs = new AdminAnalysisResponse();
		adrs.setTotalProduct(productsRepo.countByStatus("active"));
		adrs.setTotalOrder(ordersRepo.count());
		return adrs;
	}
}
