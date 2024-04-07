package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
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
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increament
	private int id;
	
	private String caption; // 사진 설명 
	private String postImageUrl; // 사진을 전송 받아서 그 사진을 서버의 특정 폴더에 저장 -> DB에 그 저장된 경로를 insert 
	
	// 등록자는 여러개의 이미지를 저장할 수 있다. 1: N , 이미지는 등록자가 1명이다. 1 : 1 
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")  // 포린키의 이름을 지정
	@ManyToOne(fetch= FetchType.EAGER)   // 이미지를 select하면 조인해서 User정보를 같이 들고와라
	private User user; // 등록자 -> 오브젝트로 dB에 저장하면 포린키로 저장됨 
	
	// 추가 예정 : 이미지 좋아요 
	@JsonIgnoreProperties({"image"}) 
	@OneToMany(mappedBy = "image") // 이미지하나에 좋아요 여러개(여러사람)
	private List<Likes> likes;
	
	@Transient  // DB에 컬럼이 만들어지지 않는다.
	private boolean likeState;    // transit : 컬럼을 만들지 마라
	
	@Transient
	private int likeCount;
	
	
	// 추가 예정 : 이미지 댓글  (양방향 맵핑)
	@OrderBy("id DESC")  // 댓글 아이디 역순
	@JsonIgnoreProperties({"image"})  // 댓글에서 가져올때 image 무시 
	@OneToMany(mappedBy = "image") // 이미지하나에 댓글 여러개  (기본 LAZY : 부를때만 불러옴) 
	private List<Comment> comments;
	
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
