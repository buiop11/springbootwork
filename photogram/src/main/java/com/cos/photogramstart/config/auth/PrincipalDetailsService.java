package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service  // IoC
public class PrincipalDetailsService implements UserDetailsService{

	
	private final UserRepository userRepository;
	
	// 상속받아서 자동으로 등록한 함수
	// 로그인 시큐리티 진행하면 원래 UserDetailsService 가 낚아챈다.
	// 1. 리턴이 잘 되면 자동으로 세션을 만들어준다.
	// 2. 파라미터의 비밀번호는 시큐리티가 알아서 체크해줌 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//		System.out.println("== 나 실행 돼 ? == " + username);
		// username 에 따른 DB 조회 row 찾기  
		User userEntity = userRepository.findByUsername(username);
		if( userEntity == null ) {
			return null;
		} else {
			return new PrincipalDetails(userEntity); // user를 담기 위해서 UserDetails implements한 클래스 만듬
		}
		
	}

}
