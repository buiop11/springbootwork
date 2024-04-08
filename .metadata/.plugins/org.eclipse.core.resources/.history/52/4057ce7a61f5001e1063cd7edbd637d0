package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	
	private final CommentService commentService;
	
	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails){  // json으로 받기
//		System.out.println("==========" + commetDto);
		
		// 에러가 발생한 경우 - 오류페이지 리턴  bindingResult : 스프링이 제공하는 검증 오류 처리, 보관하는 객체 (파라미터내 순서 중요)
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {  // .getFieldError() 아니고 .getFieldErrors() : 리턴이 다름 
 				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationApiException("댓글 작성 유효성 검사 실패함", errorMap);    // 에러 발생시 강제로 throw excpetion 발생
		}
		
		Comment comment = commentService.writeComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());   // content, imageId, userId 
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기 성공", comment), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		commentService.deleteComment(id);
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제 성공", null), HttpStatus.OK);
	}
	
}
