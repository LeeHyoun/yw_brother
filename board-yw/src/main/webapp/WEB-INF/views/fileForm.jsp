<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
 <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script> 
 <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>file Form</title>

<script type="text/javascript">

$(function(){
	$("#uploadfileBTN").click(function(){
		
		/* 
		function(event){}	
		event.preventDefault(); //이벤트를 막음. */
		 
		alert("file upload");
		
		/* var frm 		= document.fileUpload;
		
		var name 		= frm.name.value;
		var pwd 		= frm.pwd.value;
		var title 		= frm.title.value;
		var content 	= frm.content.value;
		var uploadFile	= frm.uploadFile; */
		
		var form = $('form')[0];
		var formData = new FormData( form );
		alert("formData : " + formData);
		
		$.ajax({
			url      	: 		"fileUpload",
			type     	: 		"POST",
			processData : 		false,
			contentType : 		false,
			data     	: 		formData,
			success  	: 	function(data) {
								alert("upload 완료");			
							}
		}).fail(function(){
			 alert("error in ajax form submission");
		}); //ajax		
	}); //click
})

</script>

</head>


<body>
	<form name="file_up" id="file_up" method="POST" enctype="multipart/form-data">
		<fieldset>
			<table>
				<tr>
					<th>이름</th>
					<td><input type="text" 		name="name" 		required="required" placeholder="이름"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" 	name="pwd" 			required="required" placeholder="비밀번호"></td>
				</tr>
				<tr>
					<th>파일</th>
					<td><input type="file" 		name="uploadFile" 	required="required" style="width: 450px;"></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" 		name="title" 		required="required" placeholder="제목"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea rows="20" cols="40" name="content" required="required" placeholder="내용"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" id="uploadfileBTN" value="작성">
						<input type="reset" 	value="취소">
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>