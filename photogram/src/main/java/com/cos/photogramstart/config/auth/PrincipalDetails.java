package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {  // 타사이트 로그인도 여기 담기도록 처리 

	private static final long serialVersionUID = 1L;
	
	private User user;
	// oauth2
	private Map<String, Object> attributes;
	
	
	// 생성자 - user을 담아서 생성
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// oauth2 확인용 생성자
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 권한을 가져오는 함수(기본)
//		return user.getRole(); // 리턴이 String이 아닌 경우 권한이 하나가 아닐 수 있기 때문 (3개 이상의 권한) 
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
//		collector.add(new GrantedAuthority() {  // 권한 넣기 - 함수를 넘기는게 목적 : java는 함수를 넘기려면 인터페이스로 넘겨야한다.
//			@Override
//			public String getAuthority() {
//				return user.getRole();
//			}
//		});
		
		// 위에걸 람다식으로 변경하면 이렇게 된다. 
		collector.add(() ->  {  return user.getRole(); });
		
		return collector;
	}

	@Override
	public String getPassword() {  // 비밀번호 가져오는 함수 (기본)
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {  // 계정만료 확인 - 우리는 안써서 true로 처리 
		// 사용시 user에서 만료일 가져와서 처리하면 된다.
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정잠김 확인 - 우리는 true로 처리 
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //  계정만기 정도는 다 true로 처리
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
	// ==  아래 OAuth2 의 오버라이드 2개!!  =====
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;   // {id: 2222~ , name: 등 페이스북에서 받은 정보 
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

}
