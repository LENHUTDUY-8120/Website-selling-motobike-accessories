package com.PhuTungXeMay.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhuTungXeMay.payload.response.CategoryResponse;
import com.PhuTungXeMay.payload.response.NavbarCategory;
import com.PhuTungXeMay.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CategoryApi {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/category")
	public List<CategoryResponse> getAll() {
		return categoryService.getAll();
	}
	
	@GetMapping("/nav/category")
	public List<NavbarCategory> getAllNav(){
		return categoryService.getAllNav();
	}
}
