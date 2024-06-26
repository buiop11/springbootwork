package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

		private final ImageRepository imageRepository;
	
		// yml file 경로 가져오기  @Value  : org.springboot 꺼
		@Value("${file.path}")
		private String uploadFolder;

		
		// 구독한 사진들 가져오기 
		@Transactional(readOnly = true)  // 영속성 컨텐스트 변경감지를 해서, 더티체킹, (readOnly시 - flush(반영)을 하지 않는다. 
		public Page<Image> imageStory(int principalId, Pageable pageable){
			Page<Image> images = imageRepository.mStrory(principalId, pageable);
			
			// 시나리오 : 2(cos)로그인 -> 2번이 좋아요를 한 이미지인지 확인 필요
 			// 이미지의 좋아요 상태 가져오기 
			images.forEach((image)->{
				
				// 좋아요 카운트 담기 
				image.setLikeCount(image.getLikes().size());
				
				image.getLikes().forEach((like)->{
					if(like.getUser().getId() == principalId) { // 좋아요의 userid와 로그인 id확인 
						image.setLikeState(true);
					}
				});
				
			});
			
			return images;
		}
		
		
		// 사진 업로드 - 사진 객체, 등록자 정보 
		// 변경(추가,수정,삭제)이 일어날땐 @Transactional 꼭 걸어야한다. (습관)
		@Transactional
		public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
			
			UUID uuid = UUID.randomUUID(); // UUID : 파일 구분을 위해 -> 네크워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약 : Universally Unique Identifier
			String imageFileName = uuid + "_" +  imageUploadDto.getFile().getOriginalFilename();  // 파일 받기
//			System.out.println("이미지 파일이름 : " +  imageFileName);
			Path imageFilePath = Paths.get(uploadFolder + imageFileName);
			
			// 통신, I/O (하드디스크를 읽거나 넣을때) -> 예외가 발생할 수 있다. 런타임시에만 잡을 수 있다. 
			try {
				Files.write(imageFilePath, imageUploadDto.getFile().getBytes());  
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			// 이미지 DB에 저장 
			Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());  // 이미지 파일명
//			Image imageEntity = imageRepository.save(image);
			imageRepository.save(image);
			
//			System.out.println("이미지 entity : " + imageEntity);  // 이런 객체 찍는 sysout은 좋지 않으므로 테스트하고 꼭 지운다.
			// lazy 에러 발생, 객체(object)를 실행하면 .toString이 보이지 않지만 같이 실행된다.  = imageEntity.toString();
			// 객체를 찍기위해서 image.javaa 파일에 toString 함수 만들어서 user를 지워줬음.
			
		}
		
		// 인기사진 
		@Transactional(readOnly = true)
		public List<Image> popularImage() {
			return imageRepository.mPopular();
		}
	
}
