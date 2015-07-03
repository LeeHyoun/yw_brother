<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

function update_submit(){
	
	if ($("#wordName").val() == ""){
		
		alert("wordName 은 null 값을 가질수없습니다.");
	}else if ($("#wordEnName").val() == ""){
		
		alert("wordEnName 은 null 값을 가질수없습니다.");
	}else if ($("#wordDefine").val() == ""){
		
		alert("wordDefine 은 null 값을 가질수없습니다.");
	}else if ($("#writer").val() == ""){
		
		alert("writer 은 null 값을 가질수없습니다.");
	}else {
	    
		/* 단어 수정 */
		$.ajax({		
			url : "boardUpdate",
			type : "post",
			data : {	
						"objectId" : $("#objectId").val(),
						"wordName" : $("#wordName").val(),
						"wordEnName" : $("#wordEnName").val(),
						"wordDefine" : $("#wordDefine").val(),
						"writer" : $("#writer").val()
					},
			success : function(data){
				window.opener.refreshGo(); //home.jsp (부모창)에 있는 함수를 실행함으로써 새로고침 효과를 봄. 
				window.close(); 
			}
		}); //end ajax - boardUpdate
	}
}


/*
 * boardUpdatePage.jsp 팝업창 닫기
 */
 
function update_close(){
	window.close();
}

</script>

</head>
<body>
	
	<!-- hidden  -->
	<div>
		<input type="hidden" id="objectId" value="${word.objectId}">
	</div>
	
	
	<!-- 단어 관련 정보 입력. -->
	<div>단어명</div>
	<div>
		<input type="text" id="wordName" placeholder="${word.wordName}">
	</div>
	
	<div>단어영문명</div>
	<div>
		<input type="text" id="wordEnName" placeholder="${word.wordEnName}">
	</div>
	
	<div>정의</div>
	<div>
		<input type="text" id="wordDefine" placeholder="${word.wordDefine}">
	</div>
	
	<div>신청자</div>
	<div>
		<input type="text" id="writer" placeholder="${word.writer}">
	</div>
	
	
	<!-- 확인 취소 닫기 버튼. -->
	<div>
		<input type="button" value="확인" onclick="update_submit()">
		<input type="reset" value="취소">
		<input type="button" value="닫기" onclick="update_close()">
	</div>
	
</body>
</html>