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
				
	
	) {
  
 			if( boardSearchDTO.getDate() != null) {
			for( int i=0 ; i< boardSearchDTO.getDate().size() ; i++) {
				System.out.println(  boardSearchDTO.getDate().get(i) );
			}
		}
			
  
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
			BoardDTO boardDTO 
			, BindingResult bindingResult
		){
		int boardRegCnt = 0;
			
		String msg = check_BoardDTO( boardDTO, bindingResult, "reg" );
			if( msg!=null &&  msg.equals("") ) {	
				boardRegCnt = this.boardDAO.insertBoard( boardDTO );
			}
			
		// 이 HashMap 객체는 비동기 방식으로 접속하는 웹브라우저에게 던져줄 데이터이다.			
		Map<String,String> response_map = new HashMap<String,String>();
			// HashMap 객체에 경고문자 저장하기. 
			response_map.put("msg", msg);
			response_map.put("boardRegCnt", boardRegCnt+"");
			return response_map;
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
		
		
		//**************************************************
		// /boardUpProc2.do 로 접근하면 호출되는 메소드 선언		/// [수정하기]에 관련된 모든 데이터를 들고 오는 메소드
		//**************************************************
		@RequestMapping ( 									/// 비동기방식으로 접속하는 방법.
				value="/boardUpProc2.do"						/// 웹브라우저에서 유효성체크를 하지 않을 때 사용할 URL 주소				
				, method=RequestMethod.POST							
				, produces="application/json;charset=UTF-8"		
				)
		
		@ResponseBody
		public Map<String,String> boardUpProc2(							/// 비동기방식으로 접속하는 방법.  ( @ResponseBody )	/// **** 댓글은 누군가를 밀어내서 update가 일어난 후 insert 가 나와야함. 	
				//**************************************
				// 파라미터값을 저장할 [BoardDTO 객체]를 매개변수로 선언
				//**************************************
				
				BoardDTO boardDTO							/// boardDTO 같은 수정물이 다 들어와야함.삭제할 때만 pk 번호 필요
				//*******************************************
				// Error 객체를 관리하는 BindingResult 객체가 저장되어 들어오는 매개변수 bindingResult 선언
				// 매개변수에 BindingResult 객체가 있으면 내부에서 유효성 체크 코드가 나온다.
				
				/// 해당 코드는 다른 코드에서도 사용하니... 메소드를 빼고 클래스 
				//*******************************************
			, BindingResult bindingResult		/// BindingResult 객체의 메위주를 매개변수 bindingResult 에 넣음.   스프링에서 유효성 체크가 쉽도록 BindingResult 객체를 제공해줌.
			
		) {			
			
			
			int boardUpCnt =0;
			//*******************************************
			// 동료메소드 check_BoardDTO를 호출하여 [유효성 체크]하고 경고문자 얻기
			//*******************************************			
			String msg = check_BoardDTO( boardDTO, bindingResult, "up"  );			/// check_BoardDTO 라는 동료메소드 를 update 할 때 호출.
			
			//*******************************************
			// 만약 msg 안에 "" 가 저장되어 있으면, 즉 유효성 체크를 통과했으면
			//*******************************************
			if( msg!=null &&  msg.equals("") ) {
				
				// System.out.println("boardDTO getB_no =>" + boardDTO.getB_no() );
				
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				// [BoardDAOImpl 객체]의 insertBoard 메소드 호출로 
				// 게시판 글 입력하고 [게시판 입력 적용행의 개수] 얻기
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@			/// 새글쓸 때는 DAO 로 바로 가도 됐지만 댓글쓰기는 Service 클래스를 들러야한다. 따라서 이를 변경한다.
				boardUpCnt = this.boardService.updateBoard( boardDTO );				/// insertBoard의 매개변수로 다량의 데이터를 담고 있는 boardDTO. DB 연동할 때 그냥 넘겨주면 됨.
																					/// boardDAO 인터페이스를 오버라이딩한 메소드가 아니여서 에러가 터짐.
																					
																					/// 웹서버에서 유효성 체크를 한 후 경고문자를 받아야함 현재 입력 적용행의 개수를 얻어버림. 따라서 에러가남.
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

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 게시판 입력 또는 수정 시 게시판 입력글의 입력양식의 유효성을 검사하고 
	// 문제가 있으면 경고 문자를 리턴하는 메소드 선언.
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm	
	public String check_BoardDTO( BoardDTO boardDTO, BindingResult bindingResult, String upRegMode  ) {
		
		String msg = "";			/// 경고문구를 담을 변수 선언. 현재는 길이가 없는 문자데이터
		
		
		// BoardDTO 객체에 저장된 데이터의 유효성 체크할 BoardValidator 객체 생성하기
		// BoardValidator 객체의 validate 메소드 호출하여 유효성 체크 실행하기.
		
		///  BoardValidator 객체는 개발자가 생성함. 그러나 스프링이 제공하는 Validator 라는 인터페이스를 구현하여 인위적으로 유효성 체크 
		///  		-> 이를 통해 획일화된 코딩을 통해 유효성 체크를 할 수 있게 됨.
		BoardValidator boardValidator = new BoardValidator( upRegMode );
		boardValidator.validate(
				boardDTO					// 유효성 체크할 DTO 객체
				,bindingResult					// 유효성 체크 결과를 관리하는 BindingResult 객체   (Error 객체를 관리함 )
				);
		
		// 만약 BindingResult 객체의 hasErrors() 메소드 호출하여 true 값을 얻으면
		if( bindingResult.hasErrors() ) {		/// hasErrors() 라는 메소드를 호출했을 때 true?   == 그 말인 즉슨 bindingResult 객체 안에 뭔가 error 가 발생했다는 뜻이다.
												/// hasErrors() 		"				  false?  == 에러 없음~ 유효성체크 통과~
			
			// 변수 msg 에 BoardValidator 객체에 저장된 경고문구 얻어 저장하기
			msg = bindingResult.getFieldError().getCode();
		}
		
		// [msg] 안의 문자 리턴하기
		return msg;								/// 유효성 체크에 걸리지 않았을 경우 길이가 없는 문자데이터를 return함.
	}	
	
	
	-
		
}  // BoardController 끝
