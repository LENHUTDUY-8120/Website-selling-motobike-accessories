package com.PhuTungXeMay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

	Category findOneByCode(String code);
	
}
