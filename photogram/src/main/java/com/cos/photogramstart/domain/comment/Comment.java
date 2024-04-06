package com.cos.photogramstart.domain.comment;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity  // 디비에 테이블을 생성 
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increament
	private int id;
	
	@Column(length = 100, nullable = false)
	private String content;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;  // 하나의 댓글은 한명이 쓴다.  한명의 유저는 여러개의 댓글 
	
	@JoinColumn(name = "imageId")
	@ManyToOne(fetch=FetchType.EAGER)
	private Image image; // 하나의 이미지는 댓글이 여러개, 하나의 댓글은 이미지가 한개 

	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
