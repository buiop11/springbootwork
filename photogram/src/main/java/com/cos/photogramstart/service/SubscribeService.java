package com.cos.photogramstart.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;   // qlrm - pom.xml에 따로 추가 : Dto를 편하게 받을 수 있다. (JpaResultMapper)
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	
	private final SubscribeRepository subscribeRepository;
	private EntityManager em; // Repository는 EntityManager를 구현해서 만들어져 있는 구현체다.
	
	
	@Transactional(readOnly = true)  // 구독리스트 (모달)
	public List<SubscribeDto> subscribeList(int principalId, int pageUserId){
		
		System.out.println("===============principalId============" + principalId);
		System.out.println("===============pageUserId============" + pageUserId);
		
		//여기에 직접 네이티브 쿼리를 진행함 - SubscribeRepository는 Subscribe 객체만 가지고 올 수 있기 때문
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  u.id, u.username, u.profileImageUrl , ");  // 끝나는지점에 항상 공란추가
		sb.append("if((SELECT 1  FROM subscribe  WHERE fromUserId  = ? AND toUserId  = u.id), 1, 0) subscribeState, ");
		sb.append("if((? = u.id), 1, 0) equalUserState ");
		sb.append("FROM user u  INNER JOIN subscribe s  ");
		sb.append("on u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId  = ?");  // 세미콜론 첨부 X 
		
		
		// 첫번째 물음표 principalId (로그인 한 사람)
		// 두번째 물음표 principalId  (로그인 한 사람)
		// 마지막 물음표 pageUserId (페이지의 유저)
		
		// javax.persistence.Query를 import
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		System.out.println("===============query============" + query);
		
		// 쿼리 실행 - subscribeDto : 내가 필요한 모양으로 받을 때  dto를 만들어서 DB 결과를 받음 JpaResultMapper (qlrm 라이브러리 필요)
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);  // 1건 리턴시에는 .uniqueResult
		
		return subscribeDtos;
	}
	
	
	@Transactional
	public void subscribe(int fromUserId, int toUserId) {  // 구독하기 
//		subscribeRepository.save(null); // Subscribe.java의 fromUserId, toUserId가 객체이기 때문에 이걸로 사용하지 않는다.
//		int result = subscribeRepository.mSubscribe(fromUserId, toUserId); // int로 받지 말고 그냥 Exception 처리예정 
		
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch(Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void unsubscribe(int fromUserId, int toUserId) {  // 구독취소 
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
	
}
