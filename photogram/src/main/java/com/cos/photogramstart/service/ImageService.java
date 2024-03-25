package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	
		private final ImageRepository imageRepository;
	
		// yml fil 경로 가져오기  @Value  : org.springboot 꺼
		@Value("${file.path}")
		private String uploadFolder;
		
		
		// 사진 업로드 - 사진 객체, 등록자 정보 
		public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
			
			UUID uuid = UUID.randomUUID(); // UUID : 파일 구분을 위해 -> 네크워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약 : Universally Unique Identifier
			String imageFileName = uuid + "_" +  imageUploadDto.getFile().getOriginalFilename();  // 파일 받기
			
			System.out.println("이미지 파일이름 : " +  imageFileName);
			
			Path imageFilePath = Paths.get(uploadFolder + imageFileName);
			
			// 통신, I/O (하드디스크를 읽거나 넣을때) -> 예외가 발생할 수 있다. 런타임시에만 잡을 수 있다. 
			try {
				
				Files.write(imageFilePath, imageUploadDto.getFile().getBytes());  
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			// 이미지 DB에 저장 
			Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());  // 이미지 파일명
			Image imageEntity = imageRepository.save(image);
			
			System.out.println("이미지 entity : " + imageEntity);
			
		}
	
}