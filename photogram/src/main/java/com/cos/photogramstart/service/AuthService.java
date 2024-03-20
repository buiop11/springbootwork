package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // IoC , 트랜잭션 관리 
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional  // Write (Insert, Update, Delete)
	public User signin(User user) {
		
		// 패스워드 암호화 
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		// 권한 
		user.setRole("ROLE_USER");  // 관리자 ROLE_ADMIN 으로 만들 예정 
		
		// 회원가입 진행 
		User userEntity = userRepository.save(user);
		return userEntity;
	}
	
	
}
