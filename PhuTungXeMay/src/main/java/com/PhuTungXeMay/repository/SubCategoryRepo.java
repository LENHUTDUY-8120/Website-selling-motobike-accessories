package com.PhuTungXeMay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.SubCategory;

public interface SubCategoryRepo extends JpaRepository<SubCategory, Long>{

	SubCategory findOneByCode(String code);
}
