package com.PhuTungXeMay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.payload.response.SubCategoryResponse;
import com.PhuTungXeMay.repository.CategoryRepo;
import com.PhuTungXeMay.repository.SubCategoryRepo;

@Service
public class SubCategoryService {

	@Autowired
	private SubCategoryRepo subCategoryRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	public List<SubCategoryResponse> getAll(){
		return MapperUtil.mapAll(subCategoryRepo.findAll(), SubCategoryResponse.class);
	}
	
	public List<SubCategoryResponse> getAllByCodeCategory(String code){
		return MapperUtil.mapAll(categoryRepo.findOneByCode(code).getListSubCategory(),SubCategoryResponse.class);
	}
}
