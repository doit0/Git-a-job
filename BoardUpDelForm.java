<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- JQuery 라이브러리 수입하기 -->

<script src = "/js/jquery-1.11.0.min.js"></script>
<script>

		
		// 웹브라우저가 body 태그를 모두 읽어들인 후 실행할 자바스크립트 코딩 설정하기
		$(function(){
			
			
			// [수정]버튼에 클릭이벤트가 발생하면 CheckBoardUpForm 함수 선언하기
			$(".BoardUpBtn").bind("click", function(){				
				CheckBoardUpForm();

			});	 	
			// [삭제]버튼에 클릭이벤트가 발생하면 CheckBoardDelForm 함수 선언하기
			$(".BoardDelBtn").bind("click", function(){				
				CheckBoardDelForm();

			});		
			
			
			
			// [목록 보기] 버튼에 클릭이벤트가 발생하면  name=boardListForm 을 가진 form 태그의 action 값의 URL 주소로 웹서버에 접속하기
			$(".BoardListBtn").bind("click", function(){		
				// location.replace("/boardList.do")			
				document.boardListForm.submit();		
				
			});		
				
		})
		
		
		
		// 수정화면 관련 유효성 체크. 비동기 방식으로 웹서버에 접속하는 함수 선언
		function CheckBoardUpForm(){

				
				// 수정 관련 유효성 체크( 추후 추가 예정)
  
				
				// 수정 여부를 묻기
				if( confirm("정말 수정하시겠습니까?") == false ){ return ;}

				
				
				// 현재 화면에서 비동기 방식으로 웹서버쪽 "/boardUpProc.do" 로 접속해서 수정하기
				
				$.ajax({
					

			 		url:"/boardUpProc.do"				    				 		
			 		, type:"post"
			 		, data: $("[name='boardUpDelForm']").serialize()	
		
			 		
					// 웹서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정.
				  // 익명함수의 매개변수에는 웹서버가 보내온 [새 글의 입력된 행의 개수]가 들어온다.			
				          	/// 발생할 수 있는 경우의 수 : 1. 수정 성공.  2. 기존의 암호와 불일치로 수정 실패   3. 게시글의 삭제로 인한 수정 실패.(암호의 유출 등등...)   

				 	, success:function( boardupCnt ){						
				 		
							if( boardupCnt == 1 ){
								alert("수정이 성공했습니다.");	
								document.boardListForm.submit();		/// 게시물 목록보기 화면으로 
								
							}
							else if( boardupCnt == 0 ){
								alert("삭제된 게시글입니다.")	
									document.boardListForm.submit();	/// 게시물 목록보기 화면으로 
							}
							else if( boardupCnt == -1 ){
								alert("암호가 틀립니다.")			
								$("[name=pwd]").val("");
								$("[name=pwd]").focus();
							}		
								else{
									alert("서버 접속 실패~")
						
								}
					 	}
			 		
					// 웹서버와 통신이 실패했을 경우 실행할 익명함수 설정.			
				 	, error: function( ){
						alert("웹서버 접속 실패! 관리자에게 문의 바람!");
				 	}
					
				});	// ajax 끝나는 곳 
				
			 } // function CheckBoardUpForm 끝나는 곳
			 
			 
		
		// 삭제 관련 비동기 방식으로 웹서버에 접속하는 함수 선언					
    /// 삭제는 유효성 체크 필요 X.
		function CheckBoardDelForm(){
				 
			
			// 삭제 여부를 물어보기
			if( confirm("정말 삭제하시겠습니까?") == false ){ return ;}

			// 현재 화면에서 비동기 방식으로 웹서버쪽 "/boardDelProc.do" 로 접속해서 삭제하기			
			// 		<주의> 웹서버쪽으로 삭제할  [ 게시판 고유번호( PK번호 ) ]와 [ 암호 ] 를 전달해줘야한다.
				// alert( $("[name='boardUpDelForm']").serialize()	 ); return;
			
				$.ajax({ 
-
			 		url:"/boardDelProc.do"			 		
			 		, type:"post"
			 		, data: $("[name='boardUpDelForm']").serialize()		
			 		// , data: {b_no, ${requestScope.boardDTO.b_no} }		
			 		
			 		
					// 웹서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정. 익명함수의 매개변수에는 웹서버가 보내온 [새 글의 입력된 행의 개수]가 들어온다.
				 	, success:function( boardDelCnt ){					
				 		//$("body").prepend( bodyDelCnt + <hr> )

							if( boardDelCnt == 1 ){
								alert("삭제에 성공했습니다.");	
								document.boardListForm.submit(); 	
							}
							else if( boardDelCnt == 0 ){
								alert("이미 삭제된 게시글입니다.")	
								document.boardListForm.submit();	
							}
							else if( boardDelCnt == -1 ){
								alert("삭제되지 않았습니다. 암호가 틀립니다.")			
								$("[name=pwd]").val("");
								$("[name=pwd]").focus();
							}
							else{
								alert("서버 접속 실패~")
					
							}
				 	}

					// 웹서버와 통신이 실패했을 경우 실행할 익명함수 설정.		
				 	, error: function( ){
						alert("웹서버 접속 실패! 관리자에게 문의 바람!");
				 	}
					
				});	// ajax 끝나는 곳 
					
			
		 } // function CheckBoardDelForm 끝나는 곳			 
	
		
</script>
</head>


<body>

<center>
	<form name="boardUpDelForm" >						 <!--  /// form 태그 안의 URL 주소는 동기 방식으로 설정할 때 사용하는 것.  -->
	<table border=1 cellpadding=5 style="border-collapse:collapse" align="center">
	<caption> [수정 / 삭제]</caption>
	<tr>
		<th bgcolor="lightgray"> 이 름 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<input type="text" size="10" name="writer" class="writer" maxlength="10" value="${requestScope.boardDTO.writer}">
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>		
	<tr>
		<th bgcolor="lightgray"> 제 목 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<input type="text" size="40" name="subject" class="subject" maxlength="30"  value="${requestScope.boardDTO.subject}">
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 이메일 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<input type="text" size="40" name="email" class="email" maxlength="30"  value="${requestScope.boardDTO.email}">
		<!-- ---------------------------------------------------------------- -->
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 내 용 </th>
		<td>
		<!-- ---------------------------------------------------------------- -->
		<textarea name="content" class="content" rows="14" cols="40"  maxlength="300"  > ${requestScope.boardDTO.content}    </textarea>
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
		
		<input type="hidden" name="b_no" value="${requestScope.boardDTO.b_no}" >	<!--  hidden 태그로 pk값을 넣어주는 태그  -->
	
	<div style="height:5px;"></div>
		<input type="button" value="수정" class="BoardUpBtn"  >		
		<input type="button" value="삭제" class="BoardDelBtn"  >
		<input type="button" value=" 목록 보기" class="BoardListBtn" >
		</form>
		
		
		<form name="boardListForm" method="post" action="/boardList.do"></form>		<!--  post 방식으로 보내려면 form 태그가 필요하다  -->
		
</center>
</body>
</html>


