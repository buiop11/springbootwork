package com.cos.photogramstart.service;

import java.util.function.Supplier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	// 패스워드 재 암호화
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	// select 시에도 트랜잭션 걸어주면 좋다. 에러? 
	// @org.springframework.transaction.annotation.Transactional 에서만 propagation, isolation, timeout, readOnly, rollback 를 다 지원한다.
	@Transactional(readOnly = true)
	public User userProfile(int userId) {
		
		// select * from image where userId = :userId; -> JPA로 
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		
//		userEntity.getImages().get(0);
		
		return userEntity;
	}
	
	
	@Transactional
	public User UserUpdate(int id, User user) {
		
		// 1.영속화 
//		User userUpdateEntity = userRepository.findById(id).get();  // 1.  무조건 찾았다. get() 2. 못찾았어 익셉션발동  orElseThrow() 사용
		// 2. orElseThrow() 적용 
//		User userUpdateEntity = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("찾을 수 없는 id입니다.");
//			}
//		});
		// 3. 람다식으로 적용 
		User userUpdateEntity = userRepository.findById(id).orElseThrow(() -> {
				return new CustomValidationApiException("찾을 수 없는 id입니다.");
		});
		
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
