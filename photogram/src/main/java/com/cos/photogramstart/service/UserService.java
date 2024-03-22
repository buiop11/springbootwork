package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	// 패스워드 재 암호화
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Transactional
	public User UserUpdate(int id, User user) {
		
		// 1.영속화 
		User userUpdateEntity = userRepository.findById(id).get();  // 1.  무조건 찾았다. get() 2. 못찾았어 익셉션발동  orElseThrow()
		
		// 2. 영속화된 오브젝트를 수정  - 더티체킹 ( 업데이트 완료 )
		userUpdateEntity.setName(user.getName());
		
		// 패스워드는 암호화 처리 
		String rawPassword = user.getPassword();
		String encPassword = bcryptPasswordEncoder.encode(rawPassword);
		userUpdateEntity.setPassword(encPassword);
		
		userUpdateEntity.setBio(user.getBio());
		userUpdateEntity.setWebsite(user.getWebsite());
		userUpdateEntity.setPhone(user.getPhone());
		userUpdateEntity.setGender(user.getGender());
		
		return userUpdateEntity;
	
	}  // 더티체킹이 일어나서 업데이트가 완료됨.
	
	
	
}
