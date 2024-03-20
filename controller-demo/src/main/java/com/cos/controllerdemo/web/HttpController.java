package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller // File을 응답하는 컨트롤러 (클라이언트가 브라우저면 .html 파일을 리턴)
@RestController   // 데이터를 응답하는 컨트롤러 (클라이언트가 핸드폰이면 DATA를 리턴)
public class HttpController {

	// http://localhost:8080/get     -> get만 웹브라우저에서 가능 
	@GetMapping("/get")
	public String get() {
		return "<h1>get요청됨</h1>";  // @Controller인 경우는 파일을 리턴하기때문에 jsp 나 html 을 리턴해야한다.
	}
	
	// http://localhost:8080/post    -> 포스트부터 풋,딜리트는 포스트맨으로 확인해야함.
	@PostMapping("/post")
	public String post() {
		return "post요청됨";
	}
	
	// http://localhost:8080/put    
	@PutMapping("/put")
	public String put() {
		return "put요청됨";
	}

	// http://localhost:8080/delete    
	@DeleteMapping("/delete")
	public String delete() {
		return "delete요청됨";
	}
	
	
}
