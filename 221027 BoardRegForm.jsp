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
		
		
		
		// 유효성 체크. 비동기 방식으로 웹서버에 접속하는 함수 선언
		
		function regFormCheck(){
			
			// 비동기 방식으로 웹서버에 접속하여 게시판 [새 글 쓰기] 관련 입력양식의 데이터 전송
			 	$.ajax({
        
					 // 웹서버에 접속할 때 사용할 URL 주소 지정.
			 		url:"/boardRegProc.do"					
						
						// 웹서버에 전송할 데이터를 보내는 방법 지정.		 				 		
				 		, type:"post"				
				 		
						// 웹서버에 전송할 파라미터명(입력양식name 값)과 파라미터 값(입력양식value 값)을 아래와 같은 형식의 문자열로 조합 & 설정하기.	
				 		, data: $("[name='boardRegForm']").serialize()	
				 		

						// 웹서버와 통신한 후 웹서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정.		
					 	, success:function( regCnt ){		
							if( regCnt ==1){
								alert("게시판 새 글 쓰기 성공!")
									if( confirm("다시 새 글을 작성하시겠습니까?")==false ){
										location.replace("/boardList.do");  
									}
									else{
										document.boardRegForm.reset();			
									}
								
							}
								// 웹서버에 "/boardList.do" 주소로 접속 시도	
								else{
									alert("게시판 새 글 쓰기 실패! 아이디 또는 암호가 틀립니다. 재입력 해주십시오!")
						
								}
					 	}
							// 웹서버와 통신이 실패했을 경우 실행할 익명함수 설정.
						 	, error: function( ){
								alert("웹서버 접속 실패! 관리자에게 문의 바람!");
						 	}
							
							
						
				 }) // ajax 끝나는 곳
			 } // function regFormCheck 끝나는 곳
	
		
</script>
</head>


<body>

<center>
	<form name="boardRegForm" >					
	<table border=1 cellpadding=5 style="border-collapse:collapse" align="center">
	<caption> [새글쓰기]</caption>
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
	<div style="height:5px;"></div>
		<input type="button" value="저장" class="regBtn"  >
		<input type="reset" value="다시 작성" >
		<input type="button" value=" 목록 보기" class="BoardListBtn" >
		</form>
		
		
		<form name="boardListForm" method="post" action="/boardList.do">  </form>
		
</center>
</body>
</html>


