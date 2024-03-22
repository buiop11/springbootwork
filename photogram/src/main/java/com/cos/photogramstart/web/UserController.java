package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {  // 일단 회원 숫자로 
		return "user/profile"; 
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model ) { 
		// @AuthenticationPrincipal PrincipalDetails principalDetails :세션안 숨은 시큐리티 홀더에 있는 세션을 확인하기 위함 
		
		// 시큐리티에 숨은 세션정보 찾기 - 1. 추천 
		System.out.println("세션정보 : " + principalDetails.getUser() );
		
		// 시큐리티에 숨은 세션정보 찾기 - 2. 비추천 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails mPrincipalDetails = (PrincipalDetails)auth.getPrincipal();
		System.out.println("직접찾은 세션정보 : " + mPrincipalDetails.getUser());
		
		// 세션을 모델에 담아 보내기 (header에 시큐리티 태그로 진행해서 안보내도 됨). 대신 jsp에  ${ principal.user.gender } 로 user붙여야함
//		model.addAttribute("principal", principalDetails.getUser());
		
		return "user/update"; 
	}

}
