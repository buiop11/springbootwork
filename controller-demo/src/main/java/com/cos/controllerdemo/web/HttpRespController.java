package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // 파일을 리턴할 거기 때문에 restcontroller 말고 그냥 컨트롤러씀 
public class HttpRespController {

	@GetMapping("/txt")
	public String txt() {
		return "a.txt";  // 프레임 워크를 사용(틀이 이미 정해져 있음 - 일반 정적파일은 resources/static 폴더가 디폴트 경로이다.
	}
	
	/*
	 * .mustache 파일로 해보기
	 * -> MVN에서 다운로드 (https://mvnrepository.com/)
	 * -> mustache 검색 -> spring boot starter mustache 
	 * -> 들어가서 Maven dependency 복사 
	 * -> pom.xml 에 dependencies 안에 넣기 
	 * -> <version>은 필요없으니 지워주면 된다. 
	 * -> maven 수정시에는 서버를 껏다가 다시 켜줘야한다. 
	 * */
	@GetMapping("/mus")  //브라우저에서 실행하면 그냥 파일을 다운로드하는데 이건 해석을 못했다는 뜻 -> 톰켓에 가는 자바해석부분이 먹히지 않음
	public String mus() {
		return "b";  // 머스태치 템플릿 엔진 라이브러리 등록완료 - template 폴더안에 .mustache를 놔두면 확장자 없이 파일명만 적으면 자동으로 찾아감 
	}
	
	/*
	 * .jsp 파일로 해보기 (스프링부트는 jsp 기본으로 없어서 다운받아야함)
	 * -> MVN에서 다운로드 (https://mvnrepository.com/)
	 * -> jasper 검색 -> Tomcat Jasper 이게 jsp 임 
	 * -> 버전은 9점대로 받으라고 함.  9.0.41 제일 많이 다운로드 받은걸로 똑같이 받으라고 함 
	 *  -> maven dependency 복사 
	 *  -> pom.xml 에 붙여넣기 
	 *  -> mustache와 두개가 공존 할 수 없기 때문에 머스타치는 주석 처리 해줘야함 
	 *  -> jsp 는 스프링부트에서 지원을 안하기 떄문에 src/main 아래에 webapp/WEB-INF/views로 폴더를 만들어주고 그 아래에 jsp 파일을 생성한다. 
	 * */
	@GetMapping("jsp")
	public String jsp() {
		return "c";  // jsp 엔진사용 : src/main/webapp  폴더가 디폴트 경로!! 
		// application.yml 에 적용한 /WEB-INF/views/c.jsp (ViewResolver가 발동해서 suffix, preffix 를 찾아서 붙여준다.)
	}
	
	// ** 나는 sts4 3버전이상이라서 dependecy가 안먹음 --> pom.xml 에 sts 버전을 
	//<artifactId>spring-boot-starter-parent</artifactId><version>2.4.5</version> -> 3점 대에서 숫자만 변경해주니 그대로 진행되었다. 
	
}
