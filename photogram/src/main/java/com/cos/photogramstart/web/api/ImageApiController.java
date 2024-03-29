package com.cos.photogramstart.web.api;

import java.util.List;

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
	public ResponseEntity<?> imageStroy(@AuthenticationPrincipal PrincipalDetails principalDetails){
		List<Image> images = imageService.imageStory(principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "구독이미지로드 성공", images), HttpStatus.OK);
	}
	
}