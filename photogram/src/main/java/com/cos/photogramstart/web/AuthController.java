package com.cos.photogramstart.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
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
	@PostMapping("/auth/signup")
	public String signup(SignupDto signupDto) {  // key = value  (x-www-form-urlencoded)
		log.info(signupDto.toString());

		// User <- signUpDto
		User user = signupDto.toEntity();
		log.info(user.toString());
		
		// 회원가입처리 후 DB 저장된 User 객체로 리턴 
		User userEntity = authService.signin(user);
		System.out.println(userEntity);
		
		return "auth/signin";
	}
	
}
