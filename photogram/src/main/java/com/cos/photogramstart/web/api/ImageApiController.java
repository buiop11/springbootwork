package com.cos.photogramstart.web.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  // 잘보고 import!
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

	private final ImageService imageService;
	
	// 로그인자가 구독한 구독사진들 가져오기 
	@GetMapping("/api/image")  
	public ResponseEntity<?> imageStroy(@AuthenticationPrincipal PrincipalDetails principalDetails
//																		, @PageableDefault(size=3) Pageable pageable){
															, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<Image> images = imageService.imageStory(principalDetails.getUser().getId(), pageable);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독이미지로드 성공", images), HttpStatus.OK);
	}
	
}
