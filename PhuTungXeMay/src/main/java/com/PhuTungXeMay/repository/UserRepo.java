package com.PhuTungXeMay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	public Optional<User> findByUsername(String username);
	
}
