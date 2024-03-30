package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{
	
	
//	@Query(value="SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId  = :principalId) ORDER BY id DESC", nativeQuery = true)
	@Query(value="SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId  = :principalId)", nativeQuery = true)
	Page<Image> mStrory(int principalId, Pageable pageable);
	// 	List<Image> mStrory(int principalId, Pageable pageable);
	//  Pageable pageable : 자동으로 알아서 최신순으로 3개씩 가져온다, 리턴타입 List->Page로 변경 
	// ORDER BY id DESC 를 추가 controller에서 파라미터 타입을 지우고 
   // http://localhost:8080/api/image?page=1 이런식으로 사용하면 된다.
	
}
