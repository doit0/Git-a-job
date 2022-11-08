package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

//**********************************************************
// URL 접속 시 [ @Controller가 붙은 클래스의 @RequestMapping 가 붙은 메소드] 호출 전 또는 후에
// 실행될 메소드를 소유한 [ SessionInterceptor 클래스 ] 선언.
//**********************************************************
	//---------------------------------------------------------------
	// [@Controller 가 붙은 클래스의 @RequestMapping 가 붙은 메소드] 호출 전 또는 후에 
	// 실행될 메소드를 소유한 클래스가 될 자격 조건
	//---------------------------------------------------------------
	/*		<1> 스프링이 제공하는 [HandlerInterceptor 인터페이스]를 구현하기
	 		<2> @RequestMapping 가 붙은 메소드 호출 전에 실행할 코딩은 
	 				HandlerInterceptor 인터페이스의 [preHandle 메소드 ] 를 재정의(= Overriding) 하면서 삽입한다.
	 		<3> @RequestMapping 가 붙은 메소드 호출 후에 실행할 코딩은
	 				HandlerInterceptor 인터페이스의 [postHandle 메소드 ] 를 재정의(= Overriding) 하면서 삽입한다.
	*/

public class SessionInterceptor implements HandlerInterceptor {		
	@Override			                            	// 인터페이스를 overriding 할 때의 매개변수가 서로 맞지 않으면 오류가 날 수 있음.
		public boolean preHandle(
				HttpServletRequest request
				, HttpServletResponse response	/// httpsession 객체는 매개변수로 받지 않는다.
				, Object handler
				// 실제 어떤 서버에 사용할건지 등록해야함.
			)  throws Exception  {		  	/// 예외처리를 스프링프레임워크한테 떠넘긴다. 클라이언트에게 속성변수/매개변수를 사용해서 어떤 에러가 발생했는지 궁극적으로 알려주는 .. 그런것.

			
			// HttpSession 객체 얻기
			// HttpServletRequest 객체의 getSession() 메소드 호출하면 HttpSession 객체를 얻을 수 있다.
			
			HttpSession session = request.getSession(); 		// HttpSession 객체의 메위주 얻기	
			
			
			//---------------------------------
			// HttpSession 객체에서 키값이 "mid" 로 저장된 데이터 꺼내기
			// 즉 로그인 정보 꺼내기
			//---------------------------------			/// 로그인 성공 여부에 대한 증명을 가지고 있니?
			String mid = (String)session.getAttribute("mid");

			
			// 만약 mid 변수 안에 null 이 저장되어 있으면
			// 즉 만약 로그인에 성공한 적이 없으면			
			if( mid==null) {

				
				// 클라이언트가 /loginForm.do 로 재접속하라고 설정하기
				// 응답 메세지를 받은 클라이언트는 이 URL 주소로 강제로 재접속을 한다.
				response.sendRedirect("/loginForm.do");			/// login 한 상태로 접속한 게 아님.   loginForm.do 로 다시 튕겨내야함.			+  Unhandled exception type IOException. 즉 예외처리를 꼭 해줘야함.(try, catch, finally ) 
				return false;
			}

			// 만약 mid 변수 안에 null 이 저장되지 않으면
			// 즉 만약 로그인에 성공한 적이 있으면
      /// else 가 애매하게 해석될 여지가 있다면 else if 를 사용해라.
			else {
				
				// true 값을 리턴하기
				// true 값을 리턴하면 @RequestMapping 가 붙은 메소드를 호출한다.
			return true;
			}		
	
			
			/// 만약 else if 를 사용했다면
			/*
			  if( mid==null) {

	
				// 클라이언트가 /loginForm.do 로 재접속하라고 설정하기
				// 응답 메세지를 받은 클라이언트는 이 URL 주소로 강제로 재접속을 한다.
				response.sendRedirect("/loginForm.do");			/// login 한 상태로 접속한 게 아님.   loginForm.do 로 다시 튕겨내야함.			+  Unhandled exception type IOException. 즉 예외처리를 꼭 해줘야함.(try, catch, finally ) 
				flag = false;
			}

			// 만약 mid 변수 안에 null 이 저장되지 않으면 즉, 만약 로그인에 성공한 적이 있으면
		  /// else 가 애매하게 해석될 여지가 있다면 else if 를 사용해라.
			else {
				
				
				// true 값을 리턴하기 ( true 값을 리턴하면 @RequestMapping 가 붙은 메소드를 호출한다.)
							
				flag =  true;
			}		/// 이 메소드가 return true 만 하면 
	
			return flag; 를 하면 된다.
			 */
			
			
			
		}	// preHandle 끝나는 곳
			
}		// SessionInterceptor 클래스 끝나는 곳 			
