package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//------------------------------------------------------------
// [서비스 클래스]인 [BoardServiceImpl 클래스] 선언
	// @Service			=> [서비스 클래스] 임을 지정하고 스프링이 이 객체를 관리하도록 등록한다.
	// @Transactional 	=> [서비스 클래스] 의 메소드 내부에서 일어나는 모든 작업에는 [트랜잭션]이 걸린다.
//------------------------------------------------------------

@Service
@Transactional
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO boardDAO;					/// BoardService 가 BoardDAO에 접속하는 과정이 필요
	

	// [1개의 게시판 글]을 리턴하는 메소드 선언				
  /// 상세 DB 연동 간접 지시를 하는 작업 (세부적인 DB 연동 지시가 보이는 구문)
  		/// 해야할 작업 1. 게시판 상세보기 작업을 할 때 조회수를 1 증가시켜줘야 함.  2. 게시판 글 가져오기
  
	public BoardDTO getBoard( int b_no,  boolean isBoardDetailForm ) {			
		/// 2번째 인자값인 boolean이 true 일 경우에만 상세보기화면으로 들어가면 됨.
		/// false 에선 조회수 증가 X. 수정화면으로 들어감.
		
		//System.out.println("BoardServiceImpl.getBoard 메소드 호출 시작");
		
		if( isBoardDetailForm ) {

			// [BoardDAOImpl 객체]의 updateReadCount 메소드를 호출, [조회수 증가] 하고 수정한 행의 개수를 얻는다.
			int updateCount = this.boardDAO.updateReadCount( b_no );
			
			
			// [조회수 증가] 하고 수정한 행의 개수가 0개면 (즉 글이 삭제되서 없을 경우)
      	/// 0 이 아니라면 반드시 조회수가 1 업데이트 됐을 것.
			if( updateCount==0 ) { return null; }			
		}	
		
		// [BoardDAOImpl 객체]의 getBoard 메소드를 호출하여 [1개의 게시판 글]을 얻는다.
		BoardDTO board = this.boardDAO.getBoard( b_no );
		
		
	//	System.out.println("BoardServiceImpl.getBoard 메소드 종료~");
    
		// [1개의 게시판 글]이 저장된 BoardDTO 객체 리턴하기
		return board;
		
	}

}