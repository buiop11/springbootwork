package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data // Getter, Setter 
public class SignupDto {

	private String username;
	private String password;
	private String email;
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
