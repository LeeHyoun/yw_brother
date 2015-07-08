<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<!DOCTYPE html >
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
	openWinCenter('boardInsertPage', 'boardInsertPage', 
			'width=500, height=300, status=yes, toolbar=no, menubar=no, location=no, status=no, resizable=yes') 
}


/* 
 * 단어 삭제 팝업창 띄우기
 */
 
function deletePage(){
	
	var save = $(".qqqq").children().children("input").val();
	
	if (save != null){
		openWinCenter('boardDeletePage?objectId='+save, 'boardDeletePage', 
				'width=500,height=300,status=yes , toolbar=no , menubar=no , location=no, status=no, resizable=yes')
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
		openWinCenter('boardUpdatePage?objectId='+save, 'boardUpdatePage', 
				'width=500,height=300,status=yes , toolbar=no , menubar=no , location=no, status=no, resizable=yes')
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





/* *******************************[ 2015/07/03 추가 ]************************************ 
 * Author : HLEE
 * -- + 1) XML 파일을 읽어 들여 "TextArea" 에 보이기  --- 진행상황 (---------10)
 * 	  	+-- 파일을 읽어 Console 창에 출력 (O)
 *	  	+-- home.jsp 단에 TextArea 에 뿌리기. (O)
 *    	+-- HTML 형식으로 Data 처리 (O)
 */
function domTest() {
	 
	 //alert("XML VIEW");
	 
	 var file_path = $("#xmlView").val();
	 
	 //alert($("#xmlView").val());
	 if (file_path == "" || file_path == null){
		 alert("XML 파일을 선택해 주세요!");
		 return;
	 }
	 
	 $.ajax({
			url      : 		"xmlReader",
			type     : 		"post", //****
			dataType : 		"json",
			data     : {
							"file_path" : file_path
					   },
			
			success  : function(data){
				
				//alert("ajax data : " + data[1].name);
				
				var content = "";
			
				content += "<tr><td>번호</td><td>이름</td><td>전화번호</td><td>주소</td></tr>";  // 컬럼				
				
				for (var i = 0; i < data.length; i++) {
					content += 	"<tr class='cc'>";
					content += 	"<td>" + i               				 + "</td>";
					content += 	"<td>" + stringSlice(data[i].name, 5)    + "</td>";
					content += 	"<td>" + data[i].tel     				 + "</td>";
					content += 	"<td>" + data[i].address 				 + "</td>";
					
					content += 	"<input id='num'     type='hidden' value='" + i  			  + "'>";
					content += 	"<input id='name'    type='hidden' value='" + data[i].name    + "'>";
					content += 	"<input id='tel'     type='hidden' value='" + data[i].tel  	  + "'>";
					content += 	"<input id='address' type='hidden' value='" + data[i].address + "'></tr>";
					
				}// for 
				
				$('#xmlData').html(content);
				
				/* 마우스 포인터 위치에 따른 이벤트 */
				$('.cc').mouseover(function(){
		   				$(this).addClass('ui-state-hover');                              
			    }).mouseout(function(){
			   			$(this).removeClass('ui-state-hover');
			    }); // end mouse event
				
			}// success
	 }).done(function(){  // ajax 성공시 수행.

		 $(".cc").click(function(){    
			save = -1;
			$(".cc").removeClass("qqqq").removeClass("xmlUpdate");	
			
			$(this).addClass('qqqq');
			save = $(".qqqq").children().children("input").val();	
				
			//alert($(".qqqq #num").val()); //선택한 리스트의 번호
			
			var num     = $(".qqqq #num"    ).val();
			var name    = $(".qqqq #name"   ).val();
			var tel     = $(".qqqq #tel"    ).val();
			var address = $(".qqqq #address").val();
			
			var content = "";		
			var frm     = document.updatefrm;
			
			content += "<form name='updatefrm' id='updatefrm'>";	
			content += 	"번호    :	<input name='num2' 		type='text' value='" + num     + "'><br>";
			content += 	"이름    :	<input name='name2' 	type='text' value='" + name    + "'><br>";
			content += 	"전화번호:	<input name='tel2' 		type='text' value='" + tel     + "'><br>";
			content += 	"주	   소:	<input name='address2' 	type='text' value='" + address + "'><br>";
			content += 	"<input type='button' value='수정' onclick='updateXML()'>";

			//content +=  'onclick="updateXML(' + "'" + frm.num2.value + "','" + frm.name2.value + "','" + frm.tel2.value + "','" + frm.address2.value + "')" + '">';
		
			content += '</tr></form>';
			
			//alert(content);
			$('#xmlUpdate').html(content);
			
		}); // click
	}); // done
	
} //  domTest
/* ************************************************************************** 2015/07/03 End */


 
/* *******************************[ 2015/07/06 추가 ]************************************ 
 * Author : HLEE
 * -- + 1) 읽어온 Data를 XML 파일로 저장해보기  --- 진행상황 (----- ---8-)
 * -- + 2) 읽어온 레코드 선택하여 수정 해보기   --- 진행상황 (1---- -----)
 * -- 	+ 2-1) 읽어온 레코드 선택하여 수정 해보기   --- 수정중... ( 2015/07/07 )
 */ 

// domTest 새로고침
function refreshXML(){
		domTest();  
} 
 
function stringSlice(s, l) {
		
	return (s.length > l)?(s.substring(0, l) + "...") : s; // 최대길이를 넘어가면 최대길이 이후는 ... 으로 표현
} 		
 
//xml 파일 --> 다른이름으로 저장...
// 차후 수정된 파일 저장용 ..
function mkdom(){
	 
	var createName = $("#createName").val();	
	var file_path = $("#xmlView").val(); 
	
	//alert($("#xmlView").val());
	if (file_path == "" || file_path == null){
		alert("XML 파일을 선택해 주세요!");
		return;
	}
	
	if (createName == "" || createName == null){
		alert("생성할 파일 이름을 적어주세요!");
		return;
	}
	 
	alert("xml file 생성~");
	
	$.ajax({
		url      : 		"saveDOMToFile",
		type     : 		"post", //****
		dataType : 		"json",
		data     : {
						"file_path" : file_path,
						"createName": createName
				   },
		
		success  : function(data){
			alert("생성이 완료 되었습니다.");
		}
	});
} 

 
/* 레코드 하나를 선택하여 수정. */
function updateXML(){
	
	var frm        	= document.updatefrm;
	
	var num 	 	= frm.num2.value;
	var tel 	 	= frm.tel2.value;
	var name 	 	= frm.name2.value;
	var address		= frm.address2.value;
	
	var file_path 	= $("#xmlView").val(); 
	 
	//alert($("#xmlView").val());
	if (file_path == "" || file_path == null){
		alert("XML 파일을 선택해 주세요!");
		return;
	}
	
	/* alert("수정을 시작합니다.");
	alert(num + tel + name + address) */
	
	
	$.ajax({
		url      : 		"updateXML",
		type     : 		"post", //****
		//dataType : 		"json",
		data     : {
						"file_path" : file_path,
						"num" : num,
						"name" : name,
						"tel" : tel,
						"address" : address,
				   },
		success  : function(data) {
			alert("수정이 완료되었습니다.");
			refreshXML();
			
		}
	}).fail(function(){
		alert("ERROR: updateXML Ajax ");
	}); // fail ajax
}


function fileform(){
	//alert("file upload");
	
	var file = document.filefrm.file;
	
	$.ajax({
		url      : 		"file",
		type     : 		"post", //****
		//dataType : 		"json",
		data     : {
						"file" 		:	file
					},
		success  : function(data) {
			alert("upload 완료");			
		}
	}); //ajax
} 

</script>
</head>


<body>
	<div class="a4" style="width: 850px;">
		<div>
			<div><h1>파일 업로드</h1></div>
			<form name="filefrm" id="filefrm" method="POST" enctype="multipart/form-data">
				<div>
					<input type="file" name="file" id="file" size="50">
					<input type="button" value="File Form" onclick="fileform()">
				</div>
			</form>
			<div>
				<input type="button" value="파일작성" onclick="location.href='/fileUploadForm'">
				<a href="/download?path=C:/Users/NB1504PI03/Pictures&fileName=121212.jpg" > 이미지 </a>
			</div>
		</div>
		
		<hr>
		<div>
			<div>
				<h1>xml</h1>
			</div>
			<div>
				<input id="xmlView" type="file" style="width: 300px;">&nbsp;
				<input type="button" value="XML VIEW" onclick="domTest()"><br>
				<label for="createName">파일명</label>
				<input id="createName" type="text">
				<input type="button" value="Create XML" onclick="mkdom()">
				<p></p>
			</div>
			<div id="xmlData" style="font-size: 10pt; border:solid; 2px; height: 200px; width: 380px; overflow: auto;  margin-right:10px; float: left;"></div>
			<div id="xmlUpdate" style="border:solid; 2px; height: 200px; width: 280px; overflow: auto; float: none;"></div>
		</div>
		<hr>
		<div >
			<div>
				<h1>word</h1>
			</div>
	
			<div class="a3">
				<span class="a1">단어명</span> 
				<input type="text" id="w_search" class="a1">
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
	</div>
</body>
</html>