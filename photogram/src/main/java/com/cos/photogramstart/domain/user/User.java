package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@Column(length = 100, unique = true)  // 유니크 처리 , OAuth2 로그인을 위해 컬럼 늘리기 
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
	
	// 이미지테이블의 이미지도 함께 가져오는 : 양방향 매핑
	// mappedBy : 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마, User를 select 할때 해당 User id로 등록된 image를 다 가져오라는 뜻
	// fetchType.LAZY : User 를 select 할때 해당 User id로 등록된 image들을 가져오지마 - 대신 "getImages() 함수의 image들이 호출될때만" 가져와
	// fetchType.Eager = User를 select 할 때 해당 User id 로 등록된 image들을 전부 Join해서 가져와
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)  //EAGER (항상)  - LAZY 함수 실행시에만
	@JsonIgnoreProperties({"user"}) // ** user는 json 파싱을 하지 않는다. 라는 뜻 - JPA 무한 참조 방지로 자주쓰임 ** 
	private List<Image> images;  // 양방향 매핑 
	
	// DB 입력시 자동으로 입력되는 시간 
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	
	// aop 테스트를 위해서 image 무한참조 지웠음.
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", createDate=" + createDate + "]";
	}
	
	
	
	
}
