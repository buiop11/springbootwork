package com.cos.photogramstart.web.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// 공통응답 클래스
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {  //  "공통응답 클래스"인데,  에러나 User의 형태가 다르다 -> Generic 사용  : <T>
	// 제네릭 : 원하는 타입의 객체로 리턴 할 수 있다. 
	
	private int code;   //  1: 성공 , -1 : 실패 
	private String message; 
//	private Map<String, String> errorMap;
	private T data;
	

}
