package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // final 필드를 DI할때 사용 // final이 걸려 있는 모든 생성자를 의존 주입시켜줌 
@Controller // 1.IoC 등록의미 2. 파일을 리턴하는 컨트롤러 
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
//	@Autowired
	private final AuthService authService;  // final 넣으면 에러
	
	// 생성자로 AuthService 부르기 --> 의존성 주입 -> AuthService에 final + @RequiredArgsConstructor 로 필요없음
//	public AuthController(AuthService authService) {
//		this.authService = authService; // AuthService 가 IoC 등록이 되어있기 때문
//	}
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	// 회원가입 버튼 -> /auth/signup -> /auth/signin
	// 회원가입 버튼 X 
//	@ResponseBody   // 파일보내는 Controller지만 @ResponseBody 는 데이터를 응답
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {  // key = value  (x-www-form-urlencoded)
		log.info(signupDto.toString());

		/*
			전처리 진행 -> 여기서 length 20을 if 문으로분기하면 컨트롤러가 복잡해짐 
			mnvrepository.com -> validation -> spring boot starter  maven 복사 후 pom.xml에 추가 
			- singup함수 파라미터 앞에 @Valid 어노테이션 붙이기 
			- SignupDto.java -> valid 어노테이션 붙여주기 ex) @Max(20), @NotBlank
			- 후처리로 User.java 단에도 Column의 DB 처리 해주기 
			ex) @Column(length = 20, unique = true) , @Column(nullable = false)
			- 스키마 변경되었기에 yml 에서 create 후 update 처리 
			- Valid 처리된 에러 처리 BindingResult (아래)
		 */
		
		// 에러가 발생한 경우 - 오류페이지 리턴 
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {  // .getFieldError() 아니고 .getFieldErrors() : 리턴이 다름 
 				errorMap.put(error.getField(), error.getDefaultMessage());
 				System.out.println("====================================");
 				System.out.println(error.getDefaultMessage());
 				System.out.println("====================================");
			}
			
			// 에러 발생시 강제로 throw excpetion 발생
//			throw new RuntimeException("유효성 검사 에러"); // handler 패키지에 ControllerException에서 Runtime 가져감 
			throw new CustomValidationException("유효성 검사 에러", errorMap);
			
		} else {

			// User <- signUpDto
			User user = signupDto.toEntity();
			log.info(user.toString());
			
			// 회원가입처리 후 DB 저장된 User 객체로 리턴 
			User userEntity = authService.signin(user);
			System.out.println(userEntity);
			
			return "auth/signin";
			
		}
		
		
		
	}
	
}
