package com.PhuTungXeMay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
}
