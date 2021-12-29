package com.PhuTungXeMay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.Xe;

public interface XeRepo extends JpaRepository<Xe, Long>{

	Xe findOneByCodeXe(String code);
	
}
