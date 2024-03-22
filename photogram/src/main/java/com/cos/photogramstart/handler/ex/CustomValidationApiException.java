package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException {  // 익셉션이 되려면 상속 받기 

	// 객체를 구분할 때. 중요한건 아니다. JVM에게 중요한것 
	private static final long serialVersionUID = 1L;
	
	
	private String message; 
	private Map<String, String> errorMap;
	
	// 생성자 
	public CustomValidationApiException(String message, Map<String ,String> errorMap) {
//		this.message = message;
		super(message);  // 부모가 어차피 글을 리턴해준다
		this.errorMap = errorMap;
	}
	
	// getter
	public Map<String, String> getErrorMap(){
		return errorMap;
	}
	
}
