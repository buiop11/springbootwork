package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

	// 자바 핸들러로 제어 
//	@ExceptionHandler(CustomValidationException.class)  // 런타임 익셉션이 발생하는 모든 함수를 이 함수가 가로챈다.
//	public CMRespDto<?> validationException(CustomValidationException e) {  // CustomValidation의 Map 이 아니라 만들어둔 CMRespDto로 리턴 
		// 기존 RuntimeException 에서 만들어둔 CustomValidationException 으로 변경
		// 리턴도 String -> Map으로 변경 
//		return e.getMessage();
//		return e.getErrorMap();
//		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());  // 실패라 -1  , Dto 제네릭이라 리턴받을 객체 써주면된다.
//	}
	
	// 자바스크립트로 제어 
	// 클라이언트에게 응답할 때는 Script 좋음(브라우저가받는다), 하지만  Ajax, Android 통신 - CMRespDto (개발자가 응답받을 때)
	@ExceptionHandler(CustomValidationException.class)   
	public String validationException(CustomValidationException e) {
		return Script.back(e.getErrorMap().toString());
	}
	
}
