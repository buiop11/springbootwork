package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {  // oauth2 할때 사용할 서비스 

	
	private final UserRepository userRepository;

	
	@Override  //  이함수가 자동으로 오버라이드 되진않았음..
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
//		System.out.println("OAuth2 서비스 탐");
		OAuth2User oauth2User = super.loadUser(userRequest);
//		System.out.println(oauth2User.getAttributes()); //  정보가 넘어온 것을 확인할 수 있다. - returnType : map 
		
		Map<String, Object> userInfo = oauth2User.getAttributes();
		String username = "facebook_" + (String) userInfo.get("id");
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");
		// 패스워드는 암호화되서 들어가야해서 하는데, 몰라도 되는 값이다. 비밀번호로 로그인안하기 때문에.
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()); 
		
		// 할때마다 회원가입을 시킬 수 없으므로 회원여부를 확인해야함.
		User userEntity = userRepository.findByUsername(username);
		if( userEntity == null ) {  // 페이스북 최초로그인 

			// 이 정보들을 토대로 user로 회원가입 처리를 함. 
			User user = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.name(name)
					.role("ROLE_USER")  // 권한이 있어야 함.
					.build();
			
			return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());  // 일반 로그인, 타사이트 로그인도 똑같은걸로 받기 위해서 principalDetails로 반환
			
		} else {  // 이미 페이스북 회원가입이 되어있다는 뜻 
			
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}
		
	}
	
	
}
