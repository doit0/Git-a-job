package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO {

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// SqlSessionTemplate 객체를 생성해 속성변수 sqlSession 에 저장  
	// @Autowired 어노테이션을 붙이면 속성변수 자료형에 맞는 SqlSessionTemplate 객체를 생성한 후
	// 객체의 메위주를 속성변수에 저장
	// (이게 바로 IOC. 제어의 역전. 스프링이 제어를 해주는 것)
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Autowired							
  /// select는 트랜잭션의 대상이 아니기 때문에 아이디 암호의 존재개수를 알기 위해선 따로 트랜잭션 클래스를 만들지 않아도 됨.
	private SqlSessionTemplate sqlSession;
	
	
	
	// [검색한 게시판 목록] 리턴하는 메소드
	public List<Map<String, String>> getBoardList( ){
		
		// SqlSessionTemplate 객체의 selectList 메소드 호출로 List<Map<String, String>> 로 받아오기			
	List<Map<String, String>> boardList = this.sqlSession.selectList(
				
				
				// 게시판 글을 가져오는 SELECT SQL 구문의 위치 지정하기.	
				
				"com.naver.erp.BoardDAO.getBoardList"						

				);
		return boardList;
		
		
	}
	
	// [게시판 글 입력 후 입력 적용 행의 개수] 리턴하는 메소드 선언
	public int insertBoard(BoardDTO boardDTO) {		
    /// 인터페이스의 메소드를 구현하지 않으면 구현한 클래스마저 추상클래스가 된다. (그러니 조심!! 꼭 인터페이스에 메소드를 먼저 추가해줘야함!!) 
	
		
		// SqlSessionTemplate 객체의 insert 메소드 호출로
		// 게시판 글 입력 SQL 구문을 실행하고 입력 성공 행의 개수 얻기.
		
		int boardRegCnt = sqlSession.insert(
		
		
		"com.naver.erp.BoardDAO.insertBoard"		
				
		// 실행할 SQL 구문에서 사용되는 데이터				
			, boardDTO
		);
		return boardRegCnt;
	}
	
	
	
	 
	 // [게시판 글 조회수 증가하고 수정하는 행의 개수] 리턴하는 메소드 선언
	
	 public int updateReadCount(int b_no) {
		 
			//System.out.println("BoardDAOImpl.updateReadCount 메소드 호출 시작!");
			
			// SqlSessionTemplate 객체의 update(~,~) 메소드 호출로 [조회수 증가]하기
			
		 int updateCount = this.sqlSession.update(
				 
				
				 "com.naver.erp.BoardDAO.updateReadCount"
				 , b_no
				 );
		 
		 //System.out.println("BoardDAOImpl.updateReadCount 메소드 호출 종료~");
			return updateCount;
	 }					
	 
	 
	 // [1개의 게시판 글 정보]을 리턴하는 메소드 선언	
  public BoardDTO getBoard( int b_no ){
    
	//	 System.out.println("BoardDAOImpl.getBoard 메소드 호출 시작!");
    
    
			// SqlSessionTemplate 객체의 selectOne(~,~) 메소드 호출로 [[1개의 게시판 글 정보]얻기
		 BoardDTO board = this.sqlSession.selectOne(
				 
				 "com.naver.erp.BoardDAO.getBoard"

				 , b_no
			 );
		 
		// System.out.println("BoardDAOImpl.getBoard 메소드 호출 종료~");
		 return board;
	}
		//--------------------------------------------------
		//  수정/삭제 할 게시판의 [존재 개수] 리턴하는 메소드 선언
		//--------------------------------------------------
		 public int getBoardCnt( int b_no ){
			 
				
			// SqlSessionTemplate 객체의 selectOne(~,~) 메소드 호출로 [게시판 존재 개수] 얻기
			 int boardCnt = this.sqlSession.selectOne(   				 /// 게시판의 존재 개수 확인 (1행 1열의 select 결과물)

						 "com.naver.erp.BoardDAO.getBoardCnt"
						 , b_no
		 
					 );
			 return boardCnt;				/// 항상 특정 데이터(게시물 등) 의 존재개수를 알아야함.
		 }
	 
		//--------------------------------------------------
		// 수정/삭제 할 게시판의 [비밀번호 존재 개수] 리턴하는 메소드 선언
		//--------------------------------------------------
	 	public int getBoardPwdCnt( BoardDTO boardDTO  ) {
	 		
			
		// SqlSessionTemplate 객체의 selectOne(~,~) 메소드 호출로 [게시판 존재 개수] 얻기
		 int boardPwdCnt = this.sqlSession.selectOne(   	
					 "com.naver.erp.BoardDAO.getBoardPwdCnt"
					 , boardDTO					
					 // 2번째 인자값의 데이터는 한 >>>코딩상 1개만<< 있어야함. 따로따로 넘겨줄 수 없음.   따라서 pk 값 따로, 암호 따로 하는 게 아니라 boardDTO 로 한꺼번에 받는 게 좋음
				 );
		 return boardPwdCnt;				
	 }
	 
	 	
		 
		//--------------------------------------------------
		// 게시판 수정 명령한 후 수정 적용행의 개수를 리턴하는 메소드 선언
		//--------------------------------------------------
		 	public int updateBoard( BoardDTO boardDTO  ) {

			// SqlSessionTemplate 객체의 update(~,~) 메소드 호출로 [게시판 수정 명령] 한 후 수정 적용행의 개수 얻기
			 int updateBoardCnt = this.sqlSession.update(   	
						 "com.naver.erp.BoardDAO.updateBoard"
						 , boardDTO					
		 
					 );
			 return updateBoardCnt;				
		 }
	 

			//--------------------------------------------------
			// 수정/삭제 할 게시판의 [비밀번호 존재 개수] 리턴하는 메소드 선언
			//--------------------------------------------------
		 	public	int deleteBoard( BoardDTO boardDTO  ) {
		 	// System.out.println( "deleteBoard 메소드 호출!" );
			 
			 // [SqlSessionTemplate 객체]의 delete(~,~) 를 호출하여  [게시판 삭제 명령]한 후 삭제 적용행의 개수얻기
			 int deleteBoardCnt = this.sqlSession.delete(   	
							 "com.naver.erp.BoardDAO.deleteboard"
							 , boardDTO				
			 
						 );
				 return deleteBoardCnt ;	
				
			}  // deleteBoard 가 끝나는 곳

		 	
		
	
		 	
} //  class BoardDAOImpl 이 끝나는 곳
		 	
		 	
		 	
