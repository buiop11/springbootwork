package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment writeComment(String content,  int imageId, int userId) {
//		userRepository.findById(userId); //  1. 이런거 하지 말고 네이티브 쿼리를 만들으라. commentRepository 하나로 하기
//		return commentRepository.save(content, imageId, userId);  // 3. 네이티브쿼리 msave 말고 기본 save를사용하라 

		// Tip (객체를 만들 때 id값만 담아서 insert 할 수 있다.)
		// 대신 return 시에 image 객체와 user 객체는 id 값만 가지고 있는 빈객체를 리턴받는다. username이 필요하면 세션값사용은 위험 -> user는 repository로 
		Image image = new Image();
		image.setId(imageId);
		
//		User user = new User();
//		user.setId(userId);
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
		});
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		
		return commentRepository.save(comment);  // 3. 네이티브쿼리 msave 말고 기본 save를사용하라 
	}
	
	@Transactional
	public void deleteComment(int id) {
		
		try {
			commentRepository.deleteById(id);  // 터지면 익셉션 넣어주면 된다.  이건 미리넣었음
		}catch(Exception e) {
			throw new CustomApiException(e.getMessage());   // 이건 데이터 리턴 그냥 CustomException은 페이지 리턴 
		}
		
	}
	
}
