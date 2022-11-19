<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- JQuery 라이브러리 수입하기 -->
<script src = "/js/jquery-1.11.0.min.js"></script>
<script>


		$(function(){

			
			// [저장]버튼에 클릭이벤트가 발생하면 regFormCheck 함수 선언하기
			$(".regBtn").bind("click", function(){			
				regFormCheck();

			});		
			
			
			// [목록 보기] 버튼에 클릭이벤트가 발생하면 
			// name=boardListForm 을 가진 form 태그의 action 값의 URL 주소로 웹서버에 접속하기
			
			$(".BoardListBtn").bind("click", function(){	
				// location.replace("/boardList.do")
				
				
				document.boardListForm.submit();	
				// document.boardListForm.method="post" ;
				// document.boardListForm.action="/boardList.do" ;
				
			});		
				
		})
		
		//*********************************************************************
		// 유효성 체크. 비동기 방식으로 웹서버에 접속하는 함수 선언
		//*********************************************************************
		function BoardregBtnCheck(){
		/*		
			//---------------------------------------------------
			// 게시판 [새 글 쓰기] 또는 [댓글 쓰기] 관련 입력양식의 유효성 체크하기
			// 	boardRegForm writer		subject		email		content		pwd
			//---------------------------------------------------
			
			var formObj = $("[name='boardRegForm']");
			
			// 작성자 이름 가져와서 변수 writer  에 저장하기
			var writer = formObj.find(".writer").val();
			// 작성자 이름이 한글 로 2~10자 보다 크면 경고하고 함수 중단
			var regExp = new RegExp(/^[가-힣]{2,10}$/); 	/// 한글 체크
			if( regExp.test(writer)==false ){
				alert("작성자 이름은 한글 2~10까지 입력해야합니다.");
				formObj.find(".writer").val("");
				return;
			}		
			
			//---------------------------------------------------
			// 제목	가져와서 변수 subject 에 저장하기
			var subject = formObj.find(".subject").val();
			
				// 만약에 subject 변수안의 데이터가 String 이 아니면 "" 저장하기
				if( typeof(subject)!="string" ){subject = ""; }		/// String 객체가 들어왔는지 확인하는 절차 (만약 string 객체가 아니라면 길이가 없는 문자 데이터 넣어주기) 
			
				// subject 변수안의 데이터의 앞뒤 공백 제거하기
				subject = $.trim(subject);								/// 앞뒤 공백 제거
				
				// subject 변수안의 데이터를 웹화면에 삽입하기
				formObj.find(".subject").val(subject);						/// 자스 안의 데이터가 앞뒤 공백 제거된 것. 그러니 변수 안의 데이터에도 공백 제거한 것 넣어주기
				
				// 제목이 한글 또는 영문으로 15자 보다 크면 경고하고 함수 중단
				if( subject.length>15 ){								/// 문자열 패턴 검사할 필요 X.따라서 바로 이렇게 해야함.
					alert("제목은 15자 까지 입력해야합니다.");
				
						if( confirm(" 15자 까지만 입력하시겠습니까?") ){ 
							
							// 제목에서 15자까지 잘라내어 웹화면에 삽입하기
							formObj.find(".subject").val( subject.substring(0,15) );			/// String 객체의 메소드. (시작인덱스번호, 끝인덱스번호이전). 0부터 시작하니까 15번째 이전 이라는 의미로 14.
						
						}else {
							formObj.find(".subject").val("");
						}
					return;			/// 괜찮은지 안괜찮은지 다시 한 번 확인하기
	
				}
				// 제목이 ""라면 경고하고 함수 중단
				else if( subject.length==0 ){						/// 겹치지 않는 조건일 경우 else if 라고 쓰지 않아도 상관 x. 그래도 집어넣어주자.
					alert("제목은 1~15자 까지 입력해야합니다.");
					return;
				}
			

			/// 제목에  script 태그를 못 넣게 해야함
			if( subject.toUpperCase().indexOf( "script".toUpperCase() )>=0 ){			///	0과 같거나 큼 -> 그렇게 되면 indexOf 안에 있는 문자열이 있다는 뜻. (없으면 -1 리턴.)
				alert("제목에 <script> 태그가 들어갈 수 없습니다.");
				formObj.find(".subject").val("");
				return;
			}
			
			
			
			
			//---------------------------------------------------
			// 이메일 가져와서 변수 email  에 저장하기
			var email = formObj.find(".email").val();
		
			// 이메일 형식이 아니면 경고하고 함수 중단
			regExp = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');		/// 같은 이름의 변수를 같은 자료형으로 2번 이상 선언할 수 있음. (좋은 건 아님..)
			
			if( regExp.test(email)==false ){
				alert("이메일 형식이 아닙니다. 재입력 해야합니다.");
				formObj.find(".email").val("");
				
				return;
			}	
			
			//---------------------------------------------------
			// 내용 가져와서 변수 content  에 저장하기
			var content = formObj.find(".content").val();

			// 만약에 subject 변수안의 데이터가 String 이 아니면 "" 저장하기
			if( typeof(content)!="string" ){content = ""; }		/// String 객체가 들어왔는지 확인하는 절차 (만약 string 객체가 아니라면 길이가 없는 문자 데이터 넣어주기) 
		
			// subject 변수안의 데이터의 앞뒤 공백 제거하기
			content = $.trim(content);								/// 앞뒤 공백 제거
			
			// subject 변수안의 데이터를 웹화면에 삽입하기
			formObj.find(".content").val(content);						/// 자스 안의 데이터가 앞뒤 공백 제거된 것. 그러니 변수 안의 데이터에도 공백 제거한 것 넣어주기
		
				// 내용이 한글 또는 영문으로 15자 보다 크면 경고하고 함수 중단
				if( content.length>2000 ){								/// 문자열 패턴 검사할 필요 X.따라서 바로 이렇게 해야함.
					alert("내용은 2000자 까지 입력해야합니다.");
					if( confirm(" 15자 까지만 입력하시겠습니까?") ){ 
						
						// 내용에서 15자까지 잘라내어 웹화면에 삽입하기
						formObj.find(".content").val( content.substring(0,2000) );			/// String 객체의 메소드. (시작인덱스번호, 끝인덱스번호이전). 0부터 시작하니까 15번째 이전 이라는 의미로 14.
					
					}else {
						formObj.find(".content").val("");
					}
					return;			/// 괜찮은지 안괜찮은지 다시 한 번 확인하기
		
				}
				// 내용이 ""라면 경고하고 함수 중단
				else if( content.length==0 ){						/// 겹치지 않는 조건일 경우 else if 라고 쓰지 않아도 상관 x. 그래도 집어넣어주자.
					alert("내용은 1~2000자 까지 입력해야합니다.");
					return;
				}
			
	
			/// 내용에  script 태그를 못 넣게 해야함
			if( content.toUpperCase().indexOf( "script".toUpperCase() ) >=0 ){			///	0과 같거나 큼 -> 그렇게 되면 indexOf 안에 있는 문자열이 있다는 뜻. (없으면 -1 리턴.)
				alert("내용에 <script> 태그가 들어갈 수 없습니다.");
				formObj.find(".content").val("");
				return;
			}		
			
			
			
			
			//---------------------------------------------------
			// 암호 가져와서 변수 pwd  에 저장하기
			var pwd = formObj.find(".pwd").val();
			// 암호가 영문/숫자로 구성되고 네글자 보다 크면 경고하고 함수 중단
			regExp = new RegExp(/^[0-9a-z]{4}$/);		
			
			if( regExp.test(pwd)==false ){
				alert("암호 형식이 아닙니다. 재입력 해야합니다.");
				formObj.find(".pwd").val("");
				
				return;
			}	
			 
			
	*/		
			

				if (confirm("${empty param.mom_b_no?'새글':'댓글'}을 정말 쓰시겠습니까?") == false) { return;}
				
			// 비동기 방식으로 웹서버에 접속하여 게시판 [새 글 쓰기] 관련 입력양식의 데이터 전송
			 	$.ajax({
        
					 // 웹서버에 접속할 때 사용할 URL 주소 지정.
			 		url:"/boardRegProc.do"					
						
						// 웹서버에 전송할 데이터를 보내는 방법 지정.		 				 		
				 		, type:"post"				
				 		
						// 웹서버에 전송할 파라미터명(입력양식name 값)과 파라미터 값(입력양식value 값)을 아래와 같은 형식의 문자열로 조합 & 설정하기.	
				 		, data: $("[name='boardRegForm']").serialize()	
				 		

						// 웹서버와 통신한 후 웹서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정.		
					 	, success:function( json ){	
						
						// 웹서버가 응답해준 JSON 객체에서 경고 문구 꺼내서 변수 msg에 저장하기
						// 입력된 행의 개수 꺼내서 변수  boardRegCnt 에 저장하기
						
						var msg = json["msg"];
						var boardRegCnt = json["boardRegCnt"];						
							if( msg=="" && boardRegCnt==1 ){
								alert("${empty param.mom_b_no?'새글':'댓글'}  쓰기 성공!")
									if (confirm("${empty param.mom_b_no?'새글':'댓글'}을 정말 쓰시겠습니까?") == false){
										location.replace("/boardList.do");  
									}
									else{
										document.boardRegForm.reset();			// 다시 한 번 새 글을 쓰게 함		
									}
								
							}
								// 웹서버에 "/boardList.do" 주소로 접속 시도	
								else{
									alert(  msg +  ".{empty param.mom_b_no?'새글':'댓글'} 쓰기 실패! 아이디 또는 암호가 틀립니다. 재입력 해주십시오!")
						
								}
					 	}
							// 웹서버와 통신이 실패했을 경우 실행할 익명함수 설정.
						 	, error: function( ){
								alert("웹서버 접속 실패! 관리자에게 문의 바람!");
						 	}
							
							
						
				 }) // ajax 끝나는 곳
			 } // function BoardregBtnCheck 끝나는 곳
	
		
</script>
</head>


<body>

<center>
	<form name="boardRegForm" >					
	<table border=1 cellpadding=5 style="border-collapse:collapse" align="center">
	<caption> 	<!--  mom_b_no 라는 파라미터명에 해당하는 값이 없으면 새글쓰기 라는 문자를 현재 JSP 페이지에 표현하기
	 		 					     있으면 댓글쓰기 라는 문자를 현재 JSP 페이지에 표현하기 -->
			<!--  삼항연산자의 형식으로 만들어봄 -->
			 ${empty param.mom_b_no?"[새글쓰기]":"[댓글쓰기]"}			
	</caption>
	<tr>
		<th bgcolor="lightgray"> 이 름 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<input type="text" size="10" name="writer" class="writer" maxlength="10">
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 제 목 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<input type="text" size="40" name="subject" class="subject" maxlength="30">
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 이메일 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<input type="text" size="40" name="email" class="email" maxlength="30">
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 내 용 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<textarea name="content" class="content" rows="14" cols="40"  maxlength="300"></textarea>
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 비밀번호 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<input type="password" size="8" name="pwd" class="pwd" maxlength="4">
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>
	</table>		
		
		
		
		
		<!-- 커스텀태그 중에  C코어 if 문 태그를 사용했을 경우 -->
		<!--   mom_b_no에 엄마글 (이하 부모글)의 pk 번호가 존재한다면 input 태그 활성화. 존재하지 않는다면 input 태그 비활성화 -->
		<c:if test="${!empty param.mom_b_no}">	
			<input type="hidden"  name="b_no" value="${param.mom_b_no}">
		 </c:if>					
		 
		
		
		
		
		
		
	<div style="height:5px;"></div>
		<input type="button" value="저장" class="regBtn"  >
		<input type="reset" value="다시 작성" >
		<input type="button" value=" 목록 보기" class="BoardListBtn" >
		</form>
		
		
		<form name="boardListForm" method="post" action="/boardList.do"> 
			
		<form name="boardListForm" method="post" action="/boardList.do"> <!-- [목록보기] 누르면 → 검색화면 으로 갈 때 검색조건을 계속 담고 있어야하는 입력양식을.. 넣어야함 -->
		
					<input type="hidden" name="keyword1"  value="${param.keyword1}">						<!--  여기 담기는 입력양식태그들은 웹서버로 값을 보내기만!! 해야하니까 hidden으로 타입을 바꿔주기 -->
					<input type="hidden" name="keyword2" value="${param.keyword2}">
					
				
				 	<input type="hidden" name="or_And" 		class="or_And" 	value="${param.or_And}">
				 	<input type="hidden" name="selectPageNo"   class="selectPageNo"   value="${param.selectPageNo}">				<!--  EL 태그를 value 값으로 넣어줌 → 검색화면에서 검색조건 다시 보여줌 -->
					<input type="hidden" name="rowCntPerPage" class="rowCntPerPage"    value="${param.rowCntPerPage}">
				 	

		
		
		
		</form>
		
</center>
</body>
</html>


