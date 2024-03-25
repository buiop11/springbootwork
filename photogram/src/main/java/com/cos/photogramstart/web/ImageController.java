package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;

	@GetMapping({"/", "/image/story"})
	public String strory() {
		return "image/story"; 
	}
	
	@GetMapping("/image/popular")
	public String popular() {
		return "image/popular"; 
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload"; 
	}
	
	@PostMapping("/image")  // 사진 등록 
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {  // 사진등록정보, 등록자정보
		
		// 이미지가 넘어오지 않는 경우
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
		}
		
		// 서비스 호출 
		imageService.imageUpload(imageUploadDto, principalDetails);
		return "redirect:/user/" + principalDetails.getUser().getId();   // 유저 자신의 사진리스트로 이동 
		
	}
	
	
}
