package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	
	private boolean pageOwnerState;
	private User user;

	private int imageCount;  // 뷰페이지에서 연산하지 않도록 dto에 넣기 

	// 구독정보
	private boolean subscribeState;
	private int subscribeCount;
	
}
