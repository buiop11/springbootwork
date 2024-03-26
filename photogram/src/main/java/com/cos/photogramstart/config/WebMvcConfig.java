package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {  // web 설정 파일이 된다. 
	
	@Value("${file.path}")
	private String uploadFolder;  // yml에 설정한것 
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			registry
				.addResourceHandler("/upload/**")  // jsp 페이지에서 /upload/** 이런 주소패턴이 나오면 
				.addResourceLocations("file:///" + uploadFolder)  // yml 설정 경로(실제경로)로 변경을 해라. 
				.setCachePeriod(60*10*6) // 1시간 캐시처리
				.resourceChain(true)  // 발동 
				.addResolver(new PathResourceResolver());
	}
	

}
