<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

function insert_submit(){
 	if ($("#wordName").val() == ""){
		alert("wordName 은 null 값을 가질수없습니다.");
	}else if ($("#wordEnName").val() == ""){
		alert("wordEnName 은 null 값을 가질수없습니다.");
	}else if ($("#wordDefine").val() == ""){
		alert("wordDefine 은 null 값을 가질수없습니다.");
	}else if ($("#writer").val() == ""){
		alert("writer 은 null 값을 가질수없습니다.");
	}else{
	
		$.ajax({
		
			url:"boardInsert",
			type : "post",
			data : {	
						"wordName" : $("#wordName").val(),
						"wordEnName" : $("#wordEnName").val(),
						"wordDefine" : $("#wordDefine").val(),
						"writer" : $("#writer").val()
					},
			success : function(data){
				 window.opener.refreshGo(); 
				 window.close();  
				
			},errors : function(data){
				alert("1");
			}
		
		});
	}	
 } 

function insert_close(){
	window.close();
	
}

</script>

</head>
<body>
	<form action="boardInsert" method="post">
	<table>
		<tr>
			<td>단어명</td><td><input type="text" id="wordName"></td>
		</tr>
		<tr>
			<td>단어영문명</td><td><input type="text" id="wordEnName"></td>
		</tr>
		<tr>
			<td>정의</td><td><input type="text" id="wordDefine"></td>
		</tr>
		<tr>
			<td>신청자</td><td><input type="text" id="writer"></td>
		</tr>
		
	</table>
	
	<div><input type="button" value="신청" onclick="insert_submit()"><input type="reset" value="리셋"><input type="button" value="닫기" onclick="insert_close()"></div>
	</form>  
	
	<%-- <form:form commandName="word">
	<table>
		<tr>
			<td>단어명</td><td><input type="text" id="wordName" name="wordName"><form:errors path="word.wordName" /></td>
		</tr>
		<tr>
			<td>단어영문명</td><td><input type="text" id="wordEnName" name="wordEnName"><form:errors path="Word.wordEnName"/></td>
		</tr>
		<tr>
			<td>정의</td><td><input type="text" id="wordDefine"></td>
		</tr>
		<tr>
			<td>신청자</td><td><input type="text" id="writer" name="writer"><form:errors path="Word.writer"/></td>
		</tr>
		
	</table>
	<div><input type="button" value="신청" onclick="insert_submit()"><input type="reset" value="리셋"><input type="button" value="닫기" onclick="insert_close()"></div>
	</form:form>  --%>
</body>
</html>