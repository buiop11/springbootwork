package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistance API ( 자바를 데이터로 영구적으로 저장(DB)할 수 있는 API를 제공 ) 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity  // 디비에 테이블을 생성 
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increament
	private int id;
	
	private String caption; // 사진 설명 
	private String postImageUrl; // 사진을 전송 받아서 그 사진을 서버의 특정 폴더에 저장 -> DB에 그 저장된 경로를 insert 
	
	// 등록자는 여러개의 이미지를 저장할 수 있다. 1: N , 이미지는 등록자가 1명이다. 1 : 1
	@JoinColumn(name = "userId")  // 포린키의 이름을 지정
	@ManyToOne(fetch= FetchType.EAGER)   // 이미지를 select하면 조인해서 User정보를 같이 들고와라
	private User user; // 등록자 -> 오브젝트로 dB에 저장하면 포린키로 저장됨 
	
	// 추가 예정 : 이미지 좋아요 
	
	// 추가 예정 : 이미지 댓글 
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	// user 빼고 찍어 놔야 sysout 짝을 수 있음 - 오브젝트를 콘솔에 출력할 때 문제가 될 수 있기에 User 부분을 출력에서 지웠다.
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl + ", createDate="
//				+ createDate + "]";
//	}
	
}
