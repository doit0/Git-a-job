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
				//----------------------------------
				// 수정 관련 유효성 체크
				//----------------------------------
			//---------------------------------------------------
			// 게시판 [새 글 쓰기] 또는 [댓글 쓰기] 관련 입력양식의 유효성 체크하기
			// 	boardRegForm writer		subject		email		content		pwd
			//---------------------------------------------------
			
			var formObj = $("[name='boardUpDelForm']");
			
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
	
			/// 수정시 암호에 대한 구체적인 조건을 나열할 필요가 없으므로 암호에 대해 자세히 설명하지 않아야함.
			if(typeof(pwd)!= "string" )	{ pwd==""; }	
			pwd = $.trim(pwd);
			formObj.find(".pwd").val(pwd);
			
			if( pwd=="" ){				
				alert("암호를 입력하세요.");
				formObj.find(".pwd").val("");
				
				return;
			}	
  
				
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


