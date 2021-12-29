package com.PhuTungXeMay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.PhuTungXeMay.entity.RefreshToken;
import com.PhuTungXeMay.entity.User;


@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findById(Long id);

	Optional<RefreshToken> findByToken(String token);

	@Modifying
	int deleteByUser(User user);
}
