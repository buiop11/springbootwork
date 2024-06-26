package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component  // RestController, Service 모든 것들이 Component를 상속해서 만들어져 있음 
@Aspect
public class ValidationAdvice { // Advice를 공통기능이라고 하자. 

	// @Before, @After, @Around 
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")  // 모든 컨트롤러. 지정할 수 있음
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println(" web api 컨트롤러 ===============");
		
		Object[] args = proceedingJoinPoint.getArgs();
		for(Object arg : args) {
//			System.out.println(arg);
			if(arg instanceof BindingResult) {
				System.out.println("유효성 검사를 하는 함수입니다.");
				
				BindingResult bindingResult = (BindingResult) arg;
				// CommentApiController에서 쓰던거 그대로 가져옴 
				if(bindingResult.hasErrors()) {
//					System.out.println(" 나 실행돼?? ?? ");
					Map<String, String> errorMap = new HashMap<>();
					for(FieldError error : bindingResult.getFieldErrors()) {  // .getFieldError() 아니고 .getFieldErrors() : 리턴이 다름 
		 				errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationApiException("댓글 작성 유효성 검사 실패함", errorMap);    // 에러 발생시 강제로 throw excpetion 발생
				}
			}
		}
		
		//proceedingJoinPoint => ex) profile 함수의 모든 곳에 접근할 수 있는 변수 
		// ex) profile 함수보다 먼저 실행 
		// 위 throw를 날리는 순간 아래는 실행되지 않는다. 
		
		return proceedingJoinPoint.proceed();  //ex) profile함수가 이때 실행 
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))") 
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		System.out.println(" web 컨트롤러 ===============");
		
		Object[] args = proceedingJoinPoint.getArgs();
		for(Object arg : args) {
//			System.out.println(arg);
			if(arg instanceof BindingResult) {
//				System.out.println("유효성 검사를 하는 함수입니다.");
				
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					for(FieldError error : bindingResult.getFieldErrors()) {  // .getFieldError() 아니고 .getFieldErrors() : 리턴이 다름 
		 				errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationException("유효성 검사 에러", errorMap);
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
	}
	
}
