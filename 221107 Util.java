package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

/// 반복적으로 요긴하게 사용할 메소드들을 저장하는 Util 클래스
public class Util {
	
	/// static 이 붙었기 때문에 객체 생성을 하지 않아도 (객체명.메소드명) 의 형식으로 불러올 수 있다.
	public static Map<String,Integer > getPagingMap(		/// 페이징 처리에 관한 데이터는 int지만. int라고 쓰면 안됨. HashMap에선 객체만 가능. 기본형 절대 XXX >>> Integer를 넣어준다.<<<
															/// 기본형을 관리하는 객체 => Wrapper 클래스 가 있어야한다.
		int selectPageNo				/// 선택된 페이지번호
		, int rowCntPerPage				/// 페이지당 보여줄 행의 개수
		, int totCnt					/// 총 개수
	) {
		
		
		Map<String,Integer > map = new HashMap<String,Integer >();
		try {
				if( totCnt==0 ) {			/// 검색된 것이 0, 검색결과가 없을 경우 비어있는 HashMap 객체를 return 해야한다.

					map.put("selectPageNo"		  , selectPageNo)	;		/// 페이징 보정 작업이 일어났기 때문.
					map.put("begin_rowNo"		  , 0)	;
					
					map.put("serialNo_asc"		 , 0)	;
					map.put("serialNo_desc"		 , 0)	;
					
					map.put("end_rowNo"		      , 0)	;
					map.put("last_pageNo"		  , 0)	;
					map.put("begin_pageNo_perPage", 0)	;
					map.put("end_pageNo_perPage"  , 0)	;
					
					return map;				/// null 리턴해도 상관 없지만 상황에 따라 다름.
				
				}	
				
				
				/// 로그인해서 들어갈 때 selectPageNo, rowCntPerPage 가 0이다. 데이터가 들어가지 않았기 때문에... 행 개수가 무너지고 모든 게 무너지고...  따라서 따로 보정작업을 거친다.
				if( selectPageNo<=0 ) {	selectPageNo=1; }	
				if( rowCntPerPage<=0 ) { rowCntPerPage=10; }
			//--------------------------------------------------------------------
				int last_pageNo =   totCnt/rowCntPerPage  ;			/// 나올 수 있는 최대 마지막 페이지의 번호
			 	if(totCnt%rowCntPerPage>0)	{ last_pageNo ++; }			
				/// 삼항연산자로 쓸 수도 있다.
				///	int last_pageNo = totCnt/rowCntPerPage + (totCnt%rowCntPerPage==0?0:1) 처럼 나머지가 0이상일 경우 페이지를 하나 더한다.. 뭐 그런 얘기
				/// int last_pageNo = (int)(Math.ceil(totCnt*1.0/rowCntPerPage)	);			처럼 소수점을 일부러 붙여줘서 ceil을 사용할 수도 있다.	
			
			 	
				///****** 이 바로 밑 코딩은 [다음] 버튼을 위한 것.
			 	/// last_pageNo 를 먼저 구해야한다. 혹시라도  ( last_pageNo<selectPageNo )의 상황이 생길지도 모르니 이런 일을 차선에 예방하는 것이다.
				 if( last_pageNo<selectPageNo ) { selectPageNo = last_pageNo; }	
			 	
				
		//--------------------------------------------------------------------						

				int end_rowNo	=  ( selectPageNo * rowCntPerPage );				/// 55인 경우 올림을 해서 60 만들어주기
				int begin_rowNo =  ( end_rowNo - rowCntPerPage )+ 1 ;
					if( end_rowNo > totCnt ) { end_rowNo = totCnt;}				///  마지막행번호가 검색 총 개수보다 작으면 검색 총 개수 와 같게 만들어주기

			//--------------------------------------------------------------------		
				int pageNoCnt_perPage = 10;					
				int begin_pageNo_perPage = (int) Math.floor( (selectPageNo-1)/pageNoCnt_perPage  )*pageNoCnt_perPage +1 ;		/// 자바에선 Math.floor의 반환값은 실수기 때문에 cast 연산자 (int)를 써줘야한다.
				/// 선택한 페이지 범위에서 시작하는 페이지번호.		/// selectPageNo 안에 1~10까지의 페이지가 들어가면 0이 반환됨.		
				
				
				int end_pageNo_perPage = begin_pageNo_perPage + pageNoCnt_perPage -1;			/// 이랬다가 잘못된 수가 return 될 수 있으니 보정작업필요함
					if( end_pageNo_perPage>last_pageNo ) {			/// 현화면당마지막페이지번호가 마지막페이지번호보다 더 크게 리턴된다면
						end_pageNo_perPage=last_pageNo;				/// 현화면당마지막페이지번호가 많지 않게 맞춰주기.
					}	
				
			//--------------------------------------------------------------------	


					
			map.put("selectPageNo"		  , selectPageNo)	;			/// 페이징 보정 작업이 일어났기 때문.
			map.put("last_pageNo"		  , last_pageNo)	;	
			
			map.put("begin_rowNo"		  , begin_rowNo)	;
			map.put("end_rowNo"		      , end_rowNo)	;
			
			
			map.put("serialNo_asc"		 , begin_rowNo)	;				/// 정순 일련번호 = 시작행 번호
			map.put("serialNo_desc"		 ,  totCnt-begin_rowNo+1);
			
			map.put("begin_pageNo_perPage", begin_pageNo_perPage)	;
			map.put("end_pageNo_perPage"  , end_pageNo_perPage)	;			/// 이 데이터들은 후에 BoardController.java와 boardList.jsp 페이지에서 사용할 예정
			//--------------------------------------------------------------------
			return map; 				/// 페이징 처리에 관한 데이터를 리턴하는 HashMap 객체.

				
		}
		catch(Exception ex) {
			return new HashMap<String, Integer>(); 	
		}
		
	}


} // Util 클래스 끝나는 곳
