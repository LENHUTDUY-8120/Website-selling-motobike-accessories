package com.PhuTungXeMay.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhuTungXeMay.entity.RefreshToken;
import com.PhuTungXeMay.exception.TokenRefreshException;
import com.PhuTungXeMay.jwt.JwtService;
import com.PhuTungXeMay.jwt.RefreshTokenService;
import com.PhuTungXeMay.payload.request.AccountRequest;
import com.PhuTungXeMay.payload.request.TokenRefreshRequest;
import com.PhuTungXeMay.payload.response.JwtResponse;
import com.PhuTungXeMay.payload.response.TokenRefreshResponse;
import com.PhuTungXeMay.security.CustomUserDetails;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> login(@RequestBody AccountRequest user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		String jwt = jwtService.generateJwtToken(userDetails);

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setId(userDetails.getId());
		jwtResponse.setToken(jwt);
		jwtResponse.setUsername(userDetails.getUsername());
		jwtResponse.setRefreshToken(refreshToken.getToken());

		return ResponseEntity.ok(jwtResponse);

	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtService.generateTokenFromUser(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}
}
