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
			

			// [목록 보기] 버튼에 클릭이벤트가 발생하면  name=boardListForm 을 가진 form 태그의 action 값의 URL 주소로 웹서버에 접속하기

			$(".BoardListBtn").bind("click", function(){			
				// location.replace("/boardList.do")					
				document.boardListForm.submit();		/// 이렇게 하면 boardListForm 태그의 전송방법을 사용함.

			});		
			
		
			//-------------------------------------------------
			// [수정/삭제]버튼에 클릭이벤트가 발생하면 goBoardregForm 함수 선언하기
			//-------------------------------------------------
			$(".BoardUpDelBtn").bind("click", function(){				
				goBoardUpDelForm();

			});	

		
		// 게시판 수정/삭제 화면으로 이동하는 함수 선언
		function goBoardUpDelForm(){
			
			
			// name=boardUpDelForm 를 가진 form 태그의 action 값의 URL 주소로 서버에 접속하라
			// 현재는 hidden 태그에 입력된 게시판 번호가 전송된다.				/// 게시판 번호 = b_no (pk 값)
			document.boardUpDelForm.submit();
		}
			
			//-------------------------------------------------
			// [댓글 쓰기]버튼에 클릭이벤트가 발생하면 goBoardregForm 함수 선언하기
			//-------------------------------------------------
			$(".BoardRegFormBtn").bind("click", function(){				
				goBoardRegForm();

			});

			
				// 게시판 댓글 쓰기 화면으로 이동하는 함수 선언
				function goBoardRegForm(){
			
					document.boardRegForm.submit();
			
				}

	});
</script>
</head>
<body>

<center>
	<form name="boardDetailForm" >						 <!-- form 태그 안에 입력양식이 존재하지 않지만 만에하나 웹서버로 전송될 데이터가 생길지도 모르니.. form 태그를 만들어놓은 것. -->
	<table border=1 cellpadding=5 style="border-collapse:collapse" align="center">
	<caption> [게시판 상세 글 보기]</caption>
	<tr>
		<th bgcolor="lightgray"> 이 름 </th>
		<td>
				${requestScope.boardDTO.writer}
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 제 목 </th>
		<td>
				${requestScope.boardDTO.subject}
		</td>
	</tr>	
		<tr>
		<th bgcolor="lightgray"> 조회수 </th>
		<td>
				${requestScope.boardDTO.readcount}
		</td>
	</tr>
	<tr>
		<th bgcolor="lightgray"> 이메일 </th>
		<td>
				${requestScope.boardDTO.email}
		</td>
	</tr>	
	<tr>
		<th bgcolor="lightgray"> 내 용 </th>
		<td>
			<div style="width:300px; height:300px; background-color:lightgreen; padding:7px">
				${requestScope.boardDTO.content}
			</div>
		</td>
	</tr>
	</table>		
	<div style="height:5px;"></div>			

		<input type="button" value=" 목록 보기" 	class="BoardListBtn" >
		<input type="button" value=" 댓글 쓰기" 	class="BoardRegFormBtn" >		<!--  댓글이 완전 댓글이 아니라 새글에 아래로 나오는 글임! 잘 생각하기! -->
		<input type="button" value=" 수정/삭제하기" class="BoardUpDelBtn" >
		</form>
		
		
		<form name="boardListForm" method="post" action="/boardList.do"></form>				<!--  목록화면 이동용 -->
				
		<form name="boardUpDelForm" method="post" action="/boardUpDelForm.do">				<!--  수정/삭제화면 이동용, PK 값을 가지고 웹서버한테 가야함. -->
			<input type="hidden" name="b_no" value="${requestScope.boardDTO.b_no}">			<!--  pk 값을 EL 태그로 넣어주기 -->
			</form>		
</center>
</body>
</html>

