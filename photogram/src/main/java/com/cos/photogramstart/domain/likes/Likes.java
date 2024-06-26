package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(
		uniqueConstraints = {   // 하나의 이미지에 하나의 유저만 좋아요 가능 -> 중복 유니크 키로 묶음!
				@UniqueConstraint(
						name = "likes_uk", 
						columnNames = { "imageId" , "userId" }
				)
		}
)
public class Likes {  // N

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 번호 증가 전략이 데이터베이스를 따라간다. auto-increament
	private int id;
	
	/* 
	 	어떤 이미지를 누가 좋아요했는지 
		하나의 이미지는 N의 좋아요 가능  image 1 : likes N 
	    하나의 유저는 좋아요를 N 가능 user 1: likes N
	 */
	
	// 무한참조됨 (오류)
	@JoinColumn(name = "imageId")
	@ManyToOne
	private Image image;  // 1
	
	// 무한참조됨 (오류)
	// 오류가 터지고 나서 잡아봅시다.
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;  // 1
	
	private LocalDateTime createDate;

	//native query를 사용하면 안들어간다.
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
