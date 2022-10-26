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
	
인터페이스 BoardService 구현한 객체(BoardServiceImpl 클래스) 의 메위주가 들어있는 boardService.
  @Autowired
private BoardService boardService;				
	
  
  
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
	
	
	//  가상주소 /boardRegForm.do 로 접근하면 호출되는 메소드 선언

		@RequestMapping (value="/boardRegForm.do")
		public ModelAndView boardRegForm( ) {		
			
		// [ModelAndView 객체] 생성하기
		// [ModelAndView 객체] 에 [호출할 JSP 페이지명]을 저장하기
		// [ModelAndView 객체]  리턴하기
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegForm.jsp");				
		return mav; 
		}
	
	
		// /boardRegProc.do 로 접근하면 호출되는 메소드 선언		/// [새글쓰기]에 관련된 모든 데이터를 들고 오는 메소드
		@RequestMapping ( 											
				value="/boardRegProc.do"							
				, method=RequestMethod.POST							
				, produces="application/json;charset=UTF-8"		
				)
		
		@ResponseBody
		public int boardRegProc(	
			BoardDTO boardDTO ){
		int boardRegCnt = this.boardDAO.insertBoard( boardDTO );	
		return boardRegCnt;
		}
		
		

		// /boardDetailForm.do 로 접근하면 호출되는 메소드 선언
		
		@RequestMapping ( value="/boardDetailForm.do" )						
		public ModelAndView boardDetailForm(
		
				/// 클릭한 행의 게시판의 고유번호를 requestparam 으로 받지.. boardDTO 로 받을 필요가 없다.
				@RequestParam(value="b_no") int b_no
				) {

			// [BoardServiceImpl 객체]의 getBoard 메소드 호출로 [1개의 게시판 글]을 BoardDTO 객체에 담아오기				
			BoardDTO boardDTO = this.boardService.getBoard( b_no );			
			/// 만약 boardDTO 에 삭제된 게시물이 있다면 null이 들어가고... 작업 진행이 안됨.
			
			
			//***************************************************
			// [ModelAndView 객체] 생성하기
			// [ModelAndView 객체] 에 [호출할 JSP 페이지명]을 저장하기
			// [ModelAndView 객체]  리턴하기
			//***************************************************
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/boardDetailForm.jsp");			/// DB 연동!
			mav.addObject( "boardDTO", boardDTO );				/// BoardDAO 객체 사이에 boardService 객체가 낀 것.

			return mav;
		}
		
}
