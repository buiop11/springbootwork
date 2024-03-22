package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	// 상속받아서 자동으로 등록한 함수
	// 로그인 시큐리티 진행하면 원래 UserDetailsService 가 낚아챈다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println("== 나 실행 돼 ? == " + username);
		
		return null;
	}

}
