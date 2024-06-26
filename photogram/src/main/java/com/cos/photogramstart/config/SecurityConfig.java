package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

// 최신판 
// BCryptPasswordEncoder DefaultSecurityfilterChain
//@Configuration
//public class SecurityConfig {
//	
//	// UserService AuthService
//	@Bean
//	BCryptPasswordEncoder encode() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	SecurityFilterChain configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests()
//			.antMatchers("/",  "/user/**", "/image/**",  "/subscribe/**", "/comment/**", "/api/**").authenticated()
//			.anyRequest().permitAll()
//			.and()
//			.formLogin()
//			.loginPage("/auth/signin")
//			.defaultSuccessUrl("/");
//			
//		return http.build();	
//	}
//	
//}


// 구버전
//@RequiredArgsConstructor
@EnableWebSecurity // 해당파일로 시큐리티를 활성화 
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
//	private final OAuth2DetailsService oauth2DetailsService;   // oauth 기본셋팅
	
	
	@Bean // 빈으로 등록시켜줌 
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http); // super 삭제 - 기본 시큐리티가 가지고 있는 기능이 다 비활성화 됨.

		http.csrf().disable(); // 기본 csrf를 사용하지 않는다.  정상사용자인지 구분X
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated() // 인증이 필요한 주소
			.anyRequest().permitAll()  // 나머지는 모두 허용 
			.and()
			.formLogin()
			.loginPage("/auth/signin")  // 인증필요한 주소는 여기로 넘어가게  - GET 
			.loginProcessingUrl("/auth/signin")  //  - POST -> 스프링 시큐리티가 로그인 프로세스를 진행 
			.defaultSuccessUrl("/") ; // 인증완료되면 이쪽으로 
//			.usernameParameter("username")
//			.successHandler(null)  // 완료 후 진행할것 커스텀가능 
		
		// oauth 추가후 아래 것도 추가해주면된다. 
		// .and()
		// .ouath2Login()   // form 로그인도 하는데, oauth2로그인도 할꺼야!
 		// . userInfoEndpoint()    // oauth2 로그인을 하면 최종응답을 엑세스토큰(코드) 말고 회원정보로 바로 받아 달라는 뜻. 
		// .userService(oauth2DetailsService);
		
	}
	
}
