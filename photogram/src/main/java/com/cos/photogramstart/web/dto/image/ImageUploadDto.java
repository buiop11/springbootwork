package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	
	// file 과 caption을 받을 것 
	private MultipartFile file;  // 멀티파트에는 @NotBlank 어노테이션 지원안됨
	private String caption;
	
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)  // 서비스에서 만들어둔 파일경로 + 파일네임
				.user(user)
				.build();
	}
	

}
