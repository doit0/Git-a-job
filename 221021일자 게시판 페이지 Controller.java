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

	public ModelAndView boardList(
			BoardSearchDTO	boardSearchDTO			/// 검색조건 저장용이라서 테이블과는 관계 없음
			, HttpSession session			
	
	) {
  
 		String mid = (String) session.getAttribute( "mid" );	/// "mid"의 자료형은 Object 기 때문에 cast 연산자로 (String)을 붙여줘야함.
		
		if(mid == null) {						/// 로그인 없이 BoardList.do를 치고 들어왔을 경우
		
			ModelAndView mav = new ModelAndView();	
			mav.addObject("msg" , "로그인을 해야 이용할 수 있습니다.");
			mav.setViewName("error.jsp");		/// 호출할 jsp 페이지를 error 로 설정
			return mav; 				/// DB연동하지 말고 바로 mav 객체 리턴.

			
  
  	// 전체 게시판에 있는 총 게시물의 개수
	int boardFullTotCnt = this.boardDAO.getBoardListFullTotCnt( );
	
  	// 총 개수를 구하는 쿼리문이 따로 들어가야함. 페이징 처리를 안 한 [검색된 게시글의 총 개수]   ( 검색만 하고 페이징 처리 X, 검색은 Transaction 의 대상이 X -> 서비스 클래스에 들를필요는 X. (하지만 해놓으면 좋다. )  )
	int boardTotCnt = this.boardDAO.getBoardListTotCnt( boardSearchDTO );
			

	
  		//**************************************************************
  		// 페이징 처리 관련 데이터와 기타 데이터가 저장된 HashMap 객체 얻기		/// ★★ 페이징 처리 안에 검색결과가 들어가야 페이징 처리가 가능함. 
		// Util 객체의 getPagingMap 라는 메소드 호출로 얻는다.					/// ★★ 따라서 검색 결과의 개수를 얻어야 그 후에 페이징처리가 가능함.
		//**************************************************************		
		Map<String, Integer> pagingMap = Util.getPagingMap(					/// 검색된 게시글의 총 개수를 먼저 구하고 난 후에 페이징처리를 해야한다.

				boardSearchDTO.getSelectPageNo()		/// 선택한 페이지번호
				,boardSearchDTO.getRowCntPerPage()		/// 한 페이지당 보여줄 행의 개수
				,boardTotCnt							/// [페이징 처리를 안 한 전체 게시글의 개수]  (검색된 게시판의 총 개수)
				
		);	
		
		
		//**************************************************************
  		// BoardSearchDTO 객체의 속성변수 selectPageNo 에 보정된 선택페이지 저장하기.	
		// BoardSearchDTO 객체의 속성변수 begin_rowNo 에  검색 결과물에서 페이지 번호에 맞게 부분을 가져올 때 시작행 번호 저장하기
		// BoardSearchDTO 객체의 속성변수 end_rowNo 에    검색 결과물에서 페이지 번호에 맞게 부분을 가져올 때 마지막행 번호 저장하기
		//**************************************************************
		 boardSearchDTO.setSelectPageNo( (int)pagingMap.get("selectPageNo") );			/// 보정된 페이지번호를 넣어줌. + 자료형 맞춰줘야함. (자료형 안 맞아도 Integer 객체가 자동으로 기본형으로 바뀜)
		 boardSearchDTO.setBegin_rowNo( pagingMap.get("begin_rowNo") );					/// DB에서 검색한 결과 중 ... 뭐...?
		 boardSearchDTO.setEnd_rowNo( pagingMap.get("end_rowNo") );
  
  
	// [ModelAndView 객체] 생성하기
	ModelAndView mav = new ModelAndView();	

	
	// BoardDAOImpl 객체의 getBoardList 메소드 호출로 [게시판 목록] 얻기	
  	// boardList 에는 다량의 HashMap 객체의 有
	List<Map<String, String>> boardList = this.boardDAO.getBoardList( boardSearchDTO );
  
  	// [ModelAndView 객체] 에 [게시판 목록 검색 결과]를 저장하기   (EL  로 꺼낼 수 있음)
	// [ModelAndView 객체] 에 저장된 객체는 HttpServletRequest 객체에도 저장이 됨.
  	mav.addObject("boardList", boardList);
 	mav.addObject("boardTotCnt", boardTotCnt );	
	mav.addObject("boardFullTotCnt", boardFullTotCnt );
	mav.addObject("pagingMap", pagingMap );
	
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
		
			
		// /boardUpProc.do 로 접근하면 호출되는 메소드 선언		
		
		@RequestMapping ( 		
				value="/boardUpProc.do"							
				, method=RequestMethod.POST							
				, produces="application/json;charset=UTF-8"		
				)
		
		@ResponseBody
		public int boardUpProc(						

				// 파라미터값을 저장할 [BoardDTO 객체]를 매개변수로 선언
				BoardDTO boardDTO							
		) {			
			
			// BoardServiceImpl 객체의 updateBoard 메소드 호출로 update 된 행의 갯수를 얻기
			int updateBoardcnt = this.boardService.updateBoard( boardDTO );
			
			
			// 업데이트 된 행의 개수 리턴하기.
			return updateBoardcnt;
		}
		
		
		
		
		
		
			// boardDelProc.do 로 접근하면 호출되는 메소드 선언		/// [삭제]에 관련된 모든 데이터를 들고 오는 메소드
			@RequestMapping ( 										
					value="/boardDelProc.do"							
					, method=RequestMethod.POST							
					, produces="application/json;charset=UTF-8"		
					)
				
			@ResponseBody							/// 비동기방식으로 접속하는 방법.  ( @ResponseBody )	/// **** 댓글은 누군가를 밀어내서 update가 일어난 후 insert 가 나와야함. 
			public int boardDelProc(										
				
					
			// 파라미터값을 저장할 [BoardDTO 객체]를 매개변수로 선언
				BoardDTO boardDTO									/// boardDTO 같은 수정물이 다 들어와야함.삭제할 때 pk 번호와 암호가 필요
			) {			
				//System.out.println("boardController.boardDelProc 메소드 호출!");
				
				// BoardServiceImpl 객체의 deleteBoard 메소드 호출로 게시판글을 삭제하고 삭제된 행의 개수 얻기
				int deleteBoardcnt = this.boardService.deleteBoard( boardDTO );
				
					
				//System.out.println("boardController.boardDelProc 메소드 종료~");
				
				// 업데이트 된 행의 개수 리턴하기.
				return deleteBoardcnt;		
	
			}		
				
		
}
