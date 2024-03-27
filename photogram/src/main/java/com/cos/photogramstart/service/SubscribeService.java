package com.cos.photogramstart.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	
	private final SubscribeRepository subscribeRepository;
	
	
	@Transactional(readOnly = true)  // 구독리스트 (모달)
	public List<SubscribeDto> subscribeList(int principalId, int pageUserId){
		
		
		
		
		return null;
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
