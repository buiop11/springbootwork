package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	
	// 2. 이렇게 하면  Comment의 id를 받을 수 없기 때문에 사용할 수 없음
//	@Modifying
//	@Query(value="INSERT INTO comment(content, imageId, userId, createDate) VALUES (:content, :imageId, :userId, now())",  nativeQuery=true)
//	int mSave(String content, int imageId, int userId);  
	
}
