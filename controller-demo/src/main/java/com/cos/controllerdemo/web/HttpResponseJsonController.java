package com.cos.controllerdemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.controllerdemo.domain.User;

@RestController
public class HttpResponseJsonController {

	@GetMapping("/resp/json")
	public String respJson() {
		return "{\"username\":\"cos\"}";  // 메모장에 쓴 후 여기 붙여 넣으면 역슬래시(\)가 자동으로 붙어 편하다.
	}
	
	// 객체를 사용하지 않으면 불편하다 보여줌 예시 
	@GetMapping("/resp/json/javaobject")
	public String respJsonJavaObject() {

		User user = new User();
		user.setUsername("홍길동");
		String data = "{\"username\":\"" + user.getUsername() + "\"}";
		return data;
	}
	
	// 위의 불편함을 객체 User(object)로 리턴하여 해결 
	@GetMapping("/resp/json/object")
	public User respJsonObject() {

		User user = new User();
		user.setUsername("홍길동");
		return user;  // 1. 스프링의 MessageConverter 가 자동으로 Java Object 를 Json(구:xml)으로 변경해서 통신을 통해 응답해 준다.
								 // 2. @RestController 일때만 MessageConverter가 작동한다.
	}
	
	
}
