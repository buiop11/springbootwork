package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
@EnableWebSecurity // 해당파일로 시큐리티를 활성화 
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
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
			.defaultSuccessUrl("/"); // 인증완료되면 이쪽으로 
		
	}
	
}
