package com.cos.photogramstart.domain.subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

//어노테이션이 없어도 JpaRepository를 상속하면 IoC등록이 자동으로 된다.
public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{

	// 네이티브 쿼리 작성 - prepersist 작동을안함 : 키워드는 대문자로, 변수 바인딩은 :변수로 기입
	@Modifying // INSERT, DELETE, UPDATE를 네이티브 쿼리로 만들기 위한 필수 어노테이션
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES (:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId);  
	
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
//	int mUnSubscribe(int fromUserId, int toUserId); // 성공 1 (변경된 행의 갯수가 리턴) , 실패 -1 하기위해서 리턴은 int로 처리 
	void mUnSubscribe(int fromUserId, int toUserId); // 리턴 int -> void로 변경 (나중에 익셉션처리)
	
}