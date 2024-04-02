package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	
	private final LikesRepository likesRepository;
	
	@Transactional
	public void like(int imageid, int principalId) {
		likesRepository.mLikes(imageid, principalId);
	}
	
	@Transactional
	public void unLike(int imageid, int principalId) {
		likesRepository.mUnLikes(imageid, principalId);
	}

}
