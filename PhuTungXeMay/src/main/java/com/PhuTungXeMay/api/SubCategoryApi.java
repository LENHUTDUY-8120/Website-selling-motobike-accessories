package com.PhuTungXeMay.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PhuTungXeMay.payload.response.SubCategoryResponse;
import com.PhuTungXeMay.service.SubCategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SubCategoryApi {

	@Autowired
	private SubCategoryService subCategoryService;
	
	@GetMapping("/sub-category")
	public List<SubCategoryResponse> getAllByCode(@RequestParam String code){
		return subCategoryService.getAllByCodeCategory(code);
	}
	
}
