package com.cos.controllerdemo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cos.controllerdemo.domain.User;


@RestController
public class HttpBodyController {
	
	// logger로 자동완성 
	private static final Logger log = LoggerFactory.getLogger(HttpBodyController.class);

	
	@PostMapping("/body1")
	public String xwwwformurlencoded(String username) {  
		// 파라미터 변수명만으로 받아주는 것은 스프링부트는 기본적으로 x-www-form-urlencoded 타입을 파싱(분석)해주기때문
		// 보내는 곳과 파라미터 변수명이 정확해야 받을 수 있다. 
		
		log.info(username);
		return "key=value 전송옴";
	}
	
	@PostMapping("/body2")
	public String textplain(@RequestBody String data) {  // 평문
		
		log.info(data);
		return "text/plain 전송옴";
	}
	
	@PostMapping("/body3")
	public String applicationjson(@RequestBody String data) {

		// 넘어오는 값이 json 자체라서 ex) {"username":"cos"}  그래도 넘어와 데이터 활용이 어렵다. 
		log.info(data);
		return "json 전송옴";
	}
	
	@PostMapping("/body4")
	public String applicationjsonToObject(@RequestBody User user) {
		
		 //post로 postman에서 raw/json으로 {"username":"cos"} 형식으로 보내면 객체로 값만 받을 수 있다.
		log.info(user.getUsername());
		return "json 전송옴";
	}

}
