package com.PhuTungXeMay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.entity.User;
import com.PhuTungXeMay.repository.UserRepo;
import com.PhuTungXeMay.security.CustomUserDetails;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username).orElseThrow();
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	public User save(User t) {
		t.setPassword(passwordEncoder.encode(t.getPassword()));
		return userRepo.save(t);
	}
}
