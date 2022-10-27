package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO {


	// SqlSessionTemplate 객체를 생성해 속성변수 sqlSession 에 저장  
	// @Autowired 어노테이션을 붙이면 속성변수 자료형에 맞는 SqlSessionTemplate 객체를 생성한 후 객체의 메모리 위치 주소 값을 속성변수에 저장한다.
	// (이게 바로 IOC. 제어의 역전. 스프링이 제어를 해주는 것)
	
	@Autowired					              /// 따로 트랜잭션을 위한 객체를 만들지 않아도 됨.
	private SqlSessionTemplate sqlSession;

	// [검색한 게시판 목록] 리턴하는 메소드 선언
	public List<Map<String, String>> getBoardList( ){
		

		// SqlSessionTemplate 객체의 selectList 메소드 호출
	List<Map<String, String>> boardList = this.sqlSession.selectList(
				

				// 게시판 글을 가져오는 SELECT SQL 구문의 위치 지정하기.		
      	/// (  mapper namespace의 이름. select태그의 id값 ) 으로 이루어져 있어야한다.
				"com.naver.erp.BoardDAO.getBoardList"							
					

			/*				
		List<Map<String, String>> boardList =  new ArrayList<String, String>( );
		Map<String, String> map1= new HashMapMap<String, String>();
		map1.put("b_no", 1 );
		map1.put("subject", "제목1");
		map1.put("content", "내용1");
		map1.put("writer", "작성자1");
		boardList.add(map1);
		
		
		
		처럼 DB 연동이 안 될 때는 이런식으로 하나씩 HashMap 객체를 넣을 수 있음.
		*/
				);
		return boardList;
		
		
	}
	

	// [게시판 글 입력 후 입력 적용 행의 개수] 리턴하는 메소드 선언
	public int insertBoard(BoardDTO boardDTO) {		
	

		// 게시판 글 입력 SQL 구문을 실행하고 입력 성공 행의 개수 얻기.
		int boardRegCnt = sqlSession.insert(
		

		// 실행할 SQL 구문의 위치 지정하기.			
		"com.naver.erp.BoardDAO.insertBoard"
				

		// 실행할 SQL 구문에서 사용되는 데이터	
			, boardDTO
		);
		return boardRegCnt;
	}
	
	

	 // [게시판 글 조회수 증가하고 수정하는 행의 개수] 리턴하는 메소드 선언
	 public int updateReadCount(int b_no) {
		 
			// System.out.println("BoardDAOImpl.updateReadCount 메소드 호출 시작!");
     
			// SqlSessionTemplate 객체의 update(~,~) 메소드 호출로 [조회수 증가]하기
		 int updateCount = this.sqlSession.update(
				 
	              			 "com.naver.erp.BoardDAO.updateReadCount"
		                		 , b_no
				 );
		 
	//	 System.out.println("BoardDAOImpl.updateReadCount 메소드 호출 종료~");
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
	 

		 
		 
}
