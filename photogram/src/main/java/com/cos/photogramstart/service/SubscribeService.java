package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	
	private final SubscribeRepository subscribeRepository;
	
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