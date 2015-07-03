<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<!-- <script type="text/javascript" src="http:////code.jquery.com/jquery-2.1.4.min.js"></script> -->
 <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script> 

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
	/* .ui-state-hover{text-decoration:underline;color:#123042;} */
	.ui-state-hover{background:beige;border:1px solid #f40; }
	.qqqq{background: rgba(0, 0, 255, 0.2)}
	table{padding:0px;}
	td{border :1px solid;}
	.a1{float:left;}
	.a2{float: right;}
	.a3{height: 30px; width: 470px;}
	.a4{width: 500px;}
</style>


<script type="text/javascript">


/* 
 * 윈도우 ( 창 조절 )팝업창 띄우기
 */
 
window.openWinCenter = $.openWinCenter = function(url, windowName, wopt){
	
    var woptlist = wopt.replace(/ /g, "").split(",");
    var wHeight;
    var wWidth;
    
    for (var i = 0; i < woptlist.length; i++){
    	
        woptlist[i]=woptlist[i].split('=');
        
        if (woptlist[i][0].toUpperCase() == 'WIDTH'){
        	
        	wWidth = woptlist[i][1];
        }else if (woptlist[i][0].toUpperCase() == 'HEIGHT'){
        	
        	wHeight = woptlist[i][1];
        }//end if
    }//end for
    
    $.defaultOpen(url, windowName, wWidth, wHeight);
}


/* 
 * 윈도우 팝업 기본셋팅
 */
 
$.defaultOpen = function(url, windowName, width, height){
	
	setTimeout(function(){
		var popupWindow = window.open(url, windowName, 'width=' + width +' , height=' + height 
								+ ' , status=yes , toolbar=no , menubar=no , location=no, resizable=yes'  );	
	}, 1);
}


/* 
 * 단어 추가 찹업창 띄우기
 */ 

function insertPage(){
	openWinCenter('boardInsertPage', 'boardInsertPage', 'width=500, height=300, status=yes, toolbar=no, menubar=no, location=no, status=no, resizable=yes') 
}


/* 
 * 단어 삭제 팝업창 띄우기
 */
 
function deletePage(){
	
	var save = $(".qqqq").children().children("input").val();
	
	if (save != null){
		openWinCenter('boardDeletePage?objectId='+save, 'boardDeletePage', 'width=500,height=300,status=yes , toolbar=no , menubar=no , location=no, status=no, resizable=yes')
	}else {
		alert("삭제할항목을 클릭해주세요");
	}		  
}


/* 
 * 단어 수정 팝업창 띄우기
 */
 
function updatePage(){
	
	var save = $(".qqqq").children().children("input").val();
	
	if (save != null){
		openWinCenter('boardUpdatePage?objectId='+save, 'boardUpdatePage', 'width=500,height=300,status=yes , toolbar=no , menubar=no , location=no, status=no, resizable=yes')
	}else {
			alert("수정할 항목을 클릭해주세요!");
	}	
}


/*
 * 리스트 만들기.
 */
function content(data){
	
	var content = "<table>";
	
	content += "<tr><td>단어명</td><td>단어영문명</td><td>정의</td><td>신청자</td></tr>";  // 컬럼
	
	
	/* 존재하는 레코드 보여주기 */
	for (var i = 0 ; i<data.length ; i++){ 
		content += "<tr class='cc'>";
		content += "<td><input type='hidden' value="+data[i].objectId+" class='power'>"
		content += 	data[i].wordName+"</td><td>"+data[i].wordEnName+"</td>";
		content += "<td>"+data[i].wordDefine+"</td>"
		content += "<td>"+data[i].writer+"</td></tr>"
	}
							
	content += "</table>";	
	
	return content;
}


/* 
 * 단어 목록 불러오기
 */
function listPage(){
	
	var save;
	
	$.ajax({		
		url : "boardList",
		dataType : "json",
		success : function(data){
							
			$("#show").html(content(data));
		
			/* 마우스 포인터 위치에 따른 이벤트 */
			$('.cc').mouseover(function(){
		   		$(this).addClass('ui-state-hover');                              
		    	}).mouseout(function(){
		   		$(this).removeClass('ui-state-hover');
		    	});
			} 

	}).done(function(){ //ajax 성공 후 실행.

		 $(".cc").click(function(){
			save = -1;
			
			$(".cc").removeClass("qqqq");	
			$(this).addClass('qqqq');
			
			save = $(".qqqq").children().children("input").val();	
		}); 
	});
	
}

/*
 * 자식창에서 리스트 새로고침을 위한 함수
 */
function refreshGo(){
	listPage();  
}


/*
 * 단어 검색
 */
function wordSearch(){
		
	/* if ($("#w_search").val() == ""){
		alert("전체 검색을 수행 합니다.");
	}else {
		alert($("#w_search").val());
	} */
		
	
	$.ajax({
		url : "searchWord" ,
		type : "post",
		data : {
				"str" : $("#w_search").val()
				},
		
		success : function(data){
							
			$("#show").html(content(data));
			
			/* 마우스 포인터 위치에 따른 이벤트 */
			$('.cc').mouseover(function(){
		   			$(this).addClass('ui-state-hover');                              
		    }).mouseout(function(){
		   			$(this).removeClass('ui-state-hover');
		    });
		}
	
	}).done(function(){  // ajax 성공시 수행.

		 $(".cc").click(function(){    
			save = -1;
			$(".cc").removeClass("qqqq");	
			
			$(this).addClass('qqqq');
			save = $(".qqqq").children().children("input").val();	
		}); 
	});
}


/*
 * 목록접기
 */
function listPage11(){
	$("#show").empty();
}



/* *******************************[ 2015/07/03 수정 ]************************************ 
 * Author : HLEE
 * -- 1) XML 파일을 읽어 들여 "TextArea" 에 보이기  --- 진행상황 (?)
 */
 
function domTest() {
	 
	 //alert($("#xmlView").val());
	 
	 var file_path = $("#xmlView").val();
	 
	 $.ajax({
			url : "xmlReader" ,
			type : "get",
			data : {
						"file_path" : file_path
					},
			success : function(data){
				alert(data.val());
			}
		
		});
 
}


</script>
</head>

<body>
	<div class="a4">
		<div>
			<input id="xmlView" type="file" style="width: 300px;"><br><br>
			<input type="button" value="XML VIEW" onclick="domTest()">
			<input type="button" value="VIEW Close" onclick="domTestClose()">
			<p></p>
		</div>
		<hr>
		<div>
			<h1>word</h1>
		</div>

		<div class="a3">
			<span class="a1">단어명</span> <input type="text" id="w_search"
				class="a1">
			<div>
				<input type="button" value="검색" onclick="wordSearch()">
			</div>
		</div>
		<div>
			<input type="button" value="추가" onclick="insertPage()" >
			<input type="button" value="삭제" onclick="deletePage()" >
			<input type="button" value="수정" onclick="updatePage()" >
			<input type="button" value="목록" onclick="listPage()" >
			<!-- <input type="button" value="목록접기" onclick="listPage11()" > -->
		</div>
		<br>
		
		<div id="show"></div>
		<div id="result"></div>
		<!-- <div id="show_search"></div> -->
	</div>
</body>
</html>