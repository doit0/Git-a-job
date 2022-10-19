package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



//  LoginController 클래스 선언하기

/*
  스프링에서 관용적으로  Controller 단어가 들어가는 클래스는
  URL 주소로 접속 시 대응해서 호출되는 메소드를 소유하고 있다. 
	-----------------------------------------------------
 	@Controller 어노테이션이 붙은 클래스 특징
 	-----------------------------------------------------
	1. 스프링 프레임워크가 객체를 생성하고 관리한다.
	2. [컨트롤러 클래스]임이 지정된다.


 */


@Controller			/// 웹브라우저가 임의의 URL 주소를 달고 들어오면 호출되는 자바 클래스를 가진 것은 어노테이션을 붙여줘야함
public class LoginController {


	@Autowired
	private LoginDAO loginDAO;		/// 인터페이스 LoginDAO 구현한 클래스를 객체화.

	//*********************************
	// 가상 URL주소 /loginForm.do 로 접근하면 호출되는 메소드 선언			/// 가상의 주소. 실존 XX.
	//*********************************

		//-----------------------------------------------------
		// URL 주소에 대응하여 호출되려면 메소드 앞에 
		// @RequestMapping( value="포트번호 이후의 가상 URL 주소") 이라는 어노테이션이 붙어야함.
		//-----------------------------------------------------
		// <주의> @RequestMapping 이 붙은 메소드의 이름은 개발자 맘대로. 
		//		  될 수 있는 대로 URL 주소의 의도를 살려주자. 포트번호이후의가상URL주소만 제대로 주면 됨.

	
	@RequestMapping( value="/loginForm.do")			// 포트번호 이후의 URL 주소,  단순한 로그인 화면을 열 때 사용함
	

	public ModelAndView loginForm() {				/// 포트번호 이후의 URL 주소 & 어노테이션이 중요. 메소드명은 적당히 중요하게 주면 됨.
			
		//-----------------------------------------------------
		// [ModelAndView 객체] 생성하기
		//-----------------------------------------------------
		ModelAndView mav = new ModelAndView();		/// ModelAndView객체 역할 : DB 연동 결과물 or 호출할 JSP 주소 저장 
		
		//-----------------------------------------------------
		// [ModelAndView 객체]의 setViewName 메소드를 호출하여
		// [호출할 JSP 페이지명]을 문자로 저장하기
		// [호출할 JSP 페이지명] 앞의 위치경로는 !! application.properties !! 안의 spring.mvc.view.prefix=/WEB-INF/views/   에 설정
    //-----------------------------------------------------					
		
		// DB 연동 대략적 간접 지시 코드
		mav.setViewName("loginForm.jsp");			/// WEB-INF/views/loginForm.jsp  인데 안써도 걍 넣어줌.
		
		//-----------------------------------------------------
		// [ModelAndView 객체] 리턴하기
		//-----------------------------------------------------
		return mav; 		/// return 값이 ModelAndView객체의 메위주
	}
	
	
	//*********************************
	// 가상 URL주소 /loginProc.do 로 접근하면 호출되는 메소드 선언			
	//*********************************
	@RequestMapping ( 										            	/// 비동기방식으로 접속하는 방법.
			value="/loginProc.do"				
			, method=RequestMethod.POST						        	/// Post 방식으로만 접속 허용하는 메소드
			, produces="application/json;charset=UTF-8"			/// 웹브라우저로 응답할 때의 리턴값을 자동으로 맞춰줌.만약 메소드의 형태가 ArrayList라면 Array 객체를
			)
	@ResponseBody
	public int loginProc(										// 매개변수 명은 파라미터명과 달라도 됨. 문법적으로 문제는 없으나 가독성이 떨어짐. 대체로 같게 써줌.
																
			//-----------------------------------------------------
			// "mid"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 mid 에 저장하고 들어온다.
			//-----------------------------------------------------
			@RequestParam(value="mid") String mid 		/// 매개변수 오른쪽에 적은 파라미터명 String uid. 그것의 파라미터값이 매개변수로 들어옴. 
			
			//-----------------------------------------------------
			// "pwd"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 pwd 에 저장하고 들어온다.
			//-----------------------------------------------------
			, @RequestParam(value="pwd") String pwd		/// 매개변수 오른쪽에 적은 파라미터명 String pwd. 그것의 파라미터값이 매개변수로 들어옴 
														
			
			) {			/// return 값이 int 임. ModelAndView  객체가 return 되지 않음.  (비동기 방식 사용)
				
		//---------------------------------------------------------
		// HashMap 객체 생성하고 객체의 메위주를 map에 저장하기				          /// 다량의 데이터에 키값을 붙여서 저장하는 배열객체
		// 이 HashMap 객체에는 매개변수로 들어온 아이디와 암호를 저장할 예정.   (DB 연동으로 전달할 데이터들을 HashMap 에 저장, 한 번에 전달하는 것)
		//---------------------------------------------------------
		Map<String, String> map = new HashMap<String, String>();
		// HashMap 객체에 매개변수로 들어온 아이디 저장하기
		map.put("mid", mid);
		// HashMap 객체에 매개변수로 들어온 암호 저장하기
		map.put("pwd", pwd);
		
		//---------------------------------------------------------
		// [로그인 아이디]와 [암호]의 DB 존재 개수를 저장할 변수 선언하기.
		// LoginDAOImpl 객체의 getCntLogin 메소드를 호출하여 얻은 데이터 저장
		//---------------------------------------------------------
		int loginIdCnt = loginDAO.getCntLogin(map);					/// loginDAO : LoginDAO 인터페이스 구현한 클래스 객체화 후 메위주 저장한 속성변수
		
		return loginIdCnt;
	/// URL 주소 들어가면서 파라미터명과 파라미터값을 가져오는 방법
	
	}
	
}
