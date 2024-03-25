package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

// 사용자 업데이트 시 받을 것만 정리
@Data
public class UserUpdateDto {

	@NotBlank
	private String name;  		// 필수값
	@NotBlank
	private String password; // 필수값
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	// 필수값이 아닌곳이 있으니 엔티티로 만드는것은 위험하다. 코드수정 예정 
	public User toEntity() {
		
		return User.builder()
				.name(name)   // 이름 미 기재시 문제발생 -> Validation 체크 필요 
				.password(password) // 사용자가 패스워드를 기재 안하면 공백 DB에 공백이 들어가면 문제가 된다. -> Validation 체크필요
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
	
}