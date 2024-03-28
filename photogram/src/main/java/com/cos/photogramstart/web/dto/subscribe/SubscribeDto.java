package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {  // 전체 구독확인 위한 모달에 뿌림 
	
	private int id; 
	private String username; 
	private String profileImageUrl;  
	private Integer subscribeState;   // 마리아DB는 integer로 받아진다.
	private Integer equalUserState; 
	

}
