package com.PhuTungXeMay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.payload.response.CategoryResponse;
import com.PhuTungXeMay.payload.response.NavbarCategory;
import com.PhuTungXeMay.repository.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	public List<CategoryResponse> getAll(){
		return MapperUtil.mapAll(categoryRepo.findAll(), CategoryResponse.class);
	}
	
	public List<NavbarCategory> getAllNav() {
		return MapperUtil.mapAll(categoryRepo.findAll(), NavbarCategory.class);
	}
}
