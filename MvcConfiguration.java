package com.naver.erp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
//개발자가 만든 SessionInterceptor 클래스를 [인터셉터]로 등록하기 위한 MvcConfiguration 클래스 선언하기
//즉 설정을 위한 클래스이다.
//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
@Configuration							/// @Configuration	가 붙으면 웹서버가 작동하면 해당 클래스를 먼저 읽고 웹서버를 작동할 수 있도록 함. + WebMvcConfigurer 를 구현해야 해당 클래스가 작동이 됨.


public class MvcConfiguration implements WebMvcConfigurer {				/// SessionInterceptor 클래스에서 한 것들을 등록하는 것 (환경설정을 위한 클래스)

	
	// SessionInterceptor 객체를 인터셉터로 등록하는 코딩이 내포된
	// addInterceptors 메소드를 오버라이딩한다.

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		
		// InterceptorRegistry 객체의 
		// addInterceptors 		메소드를 호출하여 SessionInterceptor 객체를 인터셉터로 등록하고
		// excludePathPatterns  메소드를 호출하여 예외되는 URL 주소 패턴을 등록한다.
		/// 튕겨내지 말아야할 것들 (로그인화면, 이미지파일 등)을 excludePathPatterns 에 등록하는 것
		/// 내가 만든 클래스가 URL 주소로 들어오면 먼저 낚아챌 수 있도록
		registry.addInterceptor(new SessionInterceptor()).excludePathPatterns(			/// 인터셉ㅇ터 기능이 있는 클래스를 객체화해서 넘겨주면 제외시킬 주소들을 등록 가능.
				 "/loginForm.do"
				, "/loginProc.do"			/// 비동기 방식으로 로그인할 때 
				,"/js/**"					    /// js 폴더에 있는 모든 파일.
				
				);		/// 무조건 다 막아버리면 로그인은 물론 JQuery 수입 메소드 까지 모두 열 수 없음. 따라서 사전에 예외로 둘 URL 주소를 등록해야함.
		
	
	
	
	}		// addInterceptors 끝나는 곳

	
	
}		// MvcConfiguration 클래스 끝나는 곳
