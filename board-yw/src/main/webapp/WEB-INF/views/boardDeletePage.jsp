<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

function delete_submit(){
	$.ajax({		
		url : "boardDelete",
		type : "post",
		data : {	
					"objectId" : $("#objectId").val()
				},
		success : function(data){
			window.opener.refreshGo();
			window.close();
			
		}
		
	});
	
}

/* 부모창에 데이터 보내기 
  
 
 function goSubmit() {
	window.opener.name = "parentPage"; // 부모창의 이름 설정
	document.myForm.target = "parentPage"; // 타켓을 부모창으로 설정
	document.myForm.action = "/what/goWhat.do";
	document.myForm.submit();
	self.close();
} */



function delete_close(){
	window.close();
	
}

</script>

</head>
<body>
			<!-- hidden -->
			<div>
				<input type="hidden" id="objectId" value="${word.objectId}">
			</div>
			
			
			<!-- 삭제될 Data 보이기- readonly -->
			<div>단어명</div>
			<div>
				<input type="text" id="wordName" readonly="readonly" value="${word.wordName}">
			</div>
			<div>단어영문명</div>
			<div>
				<input type="text" id="wordEnName" readonly="readonly" value="${word.wordEnName}">
			</div>
			<div>정의</div>
			<div>
				<input type="text" id="wordDefine" readonly="readonly" value="${word.wordDefine}">
			</div>
			<div>신청자</div>
			<div>
				<input type="text" id="writer" readonly="readonly" value="${word.writer}">
			</div>
			
			
			<!-- 버튼 -->
			<div>
				<input type="button" value="확인" onclick="delete_submit()">
				<input type="button" value="닫기" onclick="delete_close()">
			</div>
</body>
</html>