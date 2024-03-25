package com.cos.photogramstart.handler.ex;

public class CustomApiException extends RuntimeException {  // 익셉션이 되려면 상속 받기 

	// 객체를 구분할 때. 중요한건 아니다. JVM에게 중요한것 
	private static final long serialVersionUID = 1L;
	
	
	// 생성자 (메시지)
	public CustomApiException(String message) {
		super(message);  // 부모가 어차피 글을 리턴해준다
	}
	
}
