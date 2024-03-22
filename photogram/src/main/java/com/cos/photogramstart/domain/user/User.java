package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA - Java Persistance API ( 자바를 데이터로 영구적으로 저장(DB)할 수 있는 API를 제공 ) 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity  // 디비에 테이블을 생성 
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 번호 증가 전략이 데이터베이스를 따라간다. auto-increament
	private int id;
	
	@Column(length = 20, unique = true)  // 유니크 처리 
	private String username;
	
	@Column(nullable = false) // persistence에서 처리 하는거, null 불가
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	private String website;  // 웹사이트 
	private String bio; 			 // 자기소개 
	
	@Column(nullable = false)
	private String email;
	
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진 
	private String role; // 권한 
	
	// DB 입력시 자동으로 입력되는 시간 
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
