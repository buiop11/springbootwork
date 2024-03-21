package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data // Getter, Setter 
public class SignupDto {

	@Size(min = 2, max=20)  // 최소 2 ~ 20자 이상 넘어가면 안된다.
	@NotBlank
	private String username;
	
	@NotBlank // 스페이스 포함 빈공간 불가 
	private String password;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String name;
	
	// User을 만들어서 그대로 build
	public User toEntity() {
		
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
	
	
}
