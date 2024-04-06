package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	// 패스워드 재 암호화
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	// 구독정보 
	private final SubscribeRepository subscribeRepository;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	
	// 프로필 유저 사진 변경 
	@Transactional
	public User userProfileUpdate(int principalId, MultipartFile profileImageFile) {
		
		// 이미지 서비스 업로드 그대로 가져옴 
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" +  profileImageFile.getOriginalFilename();  // 파일 받기
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		
		// 통신, I/O (하드디스크를 읽거나 넣을때) -> 예외가 발생할 수 있다. 런타임시에만 잡을 수 있다. 
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());  
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		
		userEntity.setProfileImageUrl(imageFileName);
		return userEntity;
		
	} // 더티체킹으로 업데이트 됨.
	

	// select 시에도 트랜잭션 걸어주면 좋다. 에러? 
	// @org.springframework.transaction.annotation.Transactional 에서만 propagation, isolation, timeout, readOnly, rollback 를 다 지원한다.
	@Transactional(readOnly = true)
	public UserProfileDto userProfile(int pageUserId, int principalId) {
		
		UserProfileDto dto = new UserProfileDto();
		
		// select * from image where userId = :userId; -> JPA로 
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		
		dto.setUser(userEntity); // user를 통째로 담음
		dto.setImageCount(userEntity.getImages().size()); // 뷰페이지에서 연산하지 않도록 dto에 넣기 
		dto.setPageOwnerState(pageUserId == principalId); // 페이지 주인, 세션주인 동일여부
		
		// 구독정보도 담는다
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeState(subscribeState == 1); 
		dto.setSubscribeCount(subscribeCount);
		
		// 프로필 페이지에 자신의 이미지 좋아요 카운트 가져오기 
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
		
//		return userEntity; // 기존
		return dto;
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
