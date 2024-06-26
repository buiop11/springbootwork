package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;

	// 자신의 이미지말고, 구독한 사용자의 이미지들 리스트 
	@GetMapping({"/", "/image/story"})  
	public String strory() {
		return "image/story"; 
	}
	
	// 인기페이지 
	@GetMapping("/image/popular")    // 여기는 데이터를 리턴할 필요가 없어서 ImageApiController에 하지 않았음.
	public String popular(Model model) { 
		
		List<Image> images = imageService.popularImage();
		model.addAttribute("images", images);
		return "image/popular"; 
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload"; 
	}
	
	@PostMapping("/image")  // 사진 등록 
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {  // 사진등록정보, 등록자정보
		
		// 이미지가 넘어오지 않는 경우 - 깍두기, 별도로 사용해야함.
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
		}
		
		// 서비스 호출 
		imageService.imageUpload(imageUploadDto, principalDetails);
		return "redirect:/user/" + principalDetails.getUser().getId();   // 유저 자신의 사진리스트로 이동 
		
	}
	
	
}
