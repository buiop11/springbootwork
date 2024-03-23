package com.cos.photogramstart.domain.subscribe;

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
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name = "subscribe_uk", 
						columnNames = { "fromUserId" , "toUserId" }
				)
		}
)
public class Subscribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 번호 증가 전략이 데이터베이스를 따라간다. auto-increament
	private int id;
	
	@JoinColumn(name = "fromUserId" )  // 기본 _ 언더바 인데, 이름 지정가능 
	@ManyToOne
	private User fromUser;
	
	@JoinColumn(name = "toUserId" )
	@ManyToOne
	private User toUser;
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
