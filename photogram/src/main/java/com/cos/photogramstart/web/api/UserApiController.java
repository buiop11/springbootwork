package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class UserApiController {

		private final UserService userService;
		private final SubscribeService subscribeService;
		
		 // 페이지 주인이 구독하는 모든 정보(모달)
		@GetMapping("/api/user/{pageUserId}/subscribe") 
		public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){
			
			List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principalDetails.getUser().getId(), pageUserId);
			
			return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
		}
		
	
		@PutMapping("/api/user/{id}")
		public CMRespDto<?> update(
				@PathVariable int id,  
				@Valid UserUpdateDto userUpdateDto, 
				BindingResult bindingResult,   // 위치가 중요! @Valid가 적혀있는 다음 파라미터로 기입해야함. 
				@AuthenticationPrincipal PrincipalDetails principalDetails) {
//			System.out.println(userUpdateDto);
			
			// 에러가 발생한 경우 - 오류페이지 리턴 (AuthController에서 사용하던것 그대로 가져옴)
			if(bindingResult.hasErrors()) {
				
				Map<String, String> errorMap = new HashMap<>();
				for(FieldError error : bindingResult.getFieldErrors()) {  // .getFieldError() 아니고 .getFieldErrors() : 리턴이 다름 
	 				errorMap.put(error.getField(), error.getDefaultMessage());
				}
				// 에러 발생시 강제로 throw excpetion 발생
				throw new CustomValidationApiException("수정시 유효성 검사 실패함", errorMap);  // 새로 만든 Exceptino
			
			} else {
				
				User userUpdateEntity = userService.UserUpdate(id, userUpdateDto.toEntity());
				principalDetails.setUser(userUpdateEntity);  // 기존 시큐리티 세션 정보도 변경하기 
				
				// 응답시에 userUpdateEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답한다.
				// image가 생기고 또 image에서 user를 호출하기 때문. -> User.java에 image에 @JsonIgnoreProperties 붙여줌 
				return new CMRespDto<>(1, "회원수정 완료", userUpdateEntity);  // 공통응답클래스로 리턴 
			}
		}
		
}
