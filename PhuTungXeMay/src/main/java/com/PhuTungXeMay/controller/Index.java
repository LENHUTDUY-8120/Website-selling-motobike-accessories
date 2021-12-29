package com.PhuTungXeMay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index {
	
	@GetMapping("/trang-chu")
	public String getindex() {
		return "index.html";
	}
	
	@GetMapping("/admin")
	public String getAmin() {
		return "admin/product.html";
	}
}
