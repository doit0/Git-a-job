package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {

// BoardDAO 인터페이스를 구현한 클래스를 찾아서 객체화해서 속성변수 boardDAO 에 객체의 메모리 위치 주소값을 저장   	
	@Autowired
	private BoardDAO boardDAO;	
  
  // 가상주소 /boardList.do 로 접근하면 호출되는 메소드 선언
  // get방식과 post 방식 모두 접근 가능. 
  @RequestMapping( value="/boardList.do")	

	public ModelAndView boardList() {
  
  
	// [ModelAndView 객체] 생성하기
	ModelAndView mav = new ModelAndView();	
  

	// BoardDAOImpl 객체의 getBoardList 메소드 호출로 [게시판 목록] 얻기	
  // boardList 에는 다량의 HashMap 객체의 有
	List<Map<String, String>> boardList = this.boardDAO.getBoardList();
  
  // [ModelAndView 객체] 에 [게시판 목록 검색 결과]를 저장하기
	// [ModelAndView 객체] 에 저장된 객체는 HttpServletRequest 객체에도 저장이 됨.
  mav.addObject("boardList", boardList);
  
    // 호출된 JSP 페이지로 DB 연동 결과물을 보냄.
  		mav.setViewName("boardList.jsp");						
		return mav; 
		
	}
}
