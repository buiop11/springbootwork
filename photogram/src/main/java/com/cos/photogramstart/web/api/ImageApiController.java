package com.cos.photogramstart.web.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  // 잘보고 import!
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

	private final ImageService imageService;
	private final LikesService likesService;  // 좋아요 서비스 
	
	// 로그인자가 구독한 구독사진들 가져오기 
	@GetMapping("/api/image")  
	public ResponseEntity<?> imageStroy(@AuthenticationPrincipal PrincipalDetails principalDetails
//																		, @PageableDefault(size=3) Pageable pageable){
															, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<Image> images = imageService.imageStory(principalDetails.getUser().getId(), pageable);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독이미지로드 성공", images), HttpStatus.OK);
	}
	
	
	// 좋아요는 여기에 매핑한다.  -- 이미지에 좋아요를 넣는 거기 때문에 
	@PostMapping("/api/image/{imageId}/likes")  // 아래 파라미터 변수 이름과 동일해야한다.
	public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		likesService.like(imageId, principalDetails.getUser().getId()); 
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요성공", null), HttpStatus.CREATED);  //생성할때 
	}
	
	// 좋아요 취소하기 
	@DeleteMapping("/api/image/{imageId}/unlikes")
	public ResponseEntity<?> unlikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		likesService.unLike(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소성공", null), HttpStatus.OK);
	}
	
}
