package com.cos.controllerdemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryPathController {


	@GetMapping("/chicken")
	public String chickenQuery(String type) {  // http://localhost:8080/chicken?type=양념  -> 파라미터로만 넣어주면  받아줌
		return type + "  배달갑니다. (쿼리스트링)";
	}
	
	// 스프링부트에선 거의 이걸로 사용함.
	@GetMapping("/chicken/{type}")  // {} 안에 데이터를 받을 수 있다. --> http://localhost:8080/chicken/양념 
	public String chickenPath(@PathVariable String type) {   
		return type + " 배달갑니다.(주소 변수 매핑-패스)";
	}
	
}
