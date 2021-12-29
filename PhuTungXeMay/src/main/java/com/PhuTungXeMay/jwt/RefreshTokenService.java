package com.PhuTungXeMay.jwt;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.entity.RefreshToken;
import com.PhuTungXeMay.exception.TokenRefreshException;
import com.PhuTungXeMay.repository.RefreshTokenRepo;
import com.PhuTungXeMay.repository.UserRepo;

@Service
public class RefreshTokenService {

	private static final long RefreshExpirationMs = 86400000000L;

	@Autowired
	private RefreshTokenRepo refreshTokenRepo;

	@Autowired
	private UserRepo userRepo;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepo.findByToken(token);
	}

	public RefreshToken createRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepo.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(RefreshExpirationMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken = refreshTokenRepo.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepo.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

	@Transactional
	public int deleteByUserId(Long userId) {
		return refreshTokenRepo.deleteByUser(userRepo.findById(userId).get());
	}
}
