<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>새 글 쓰기</title>
<link href="<%=request.getContextPath()%>/css/board.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.1.js"></script>
<script type="text/javascript">
	function writeFormCheck() {
		if($("#subject").val() == null || $("#subject").val()==""){
			alert("제목을 입력해 주세요!");
			$("#subject").focus();
			return false;
		}
		if($("#content").val()==null || $("#content").val()==""){
			alert("내용을 입력해 주세요!");
			$("#content").focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<div class="wrapper">
	<h3>새 글 쓰기</h3>
	<form action="write.do" method="post" onsubmit="return writeFormCheck()" enctype="multipart/form-data">
	<!-- enctype : <input type="file">을 위한. 폼에있는 데이터를 어떻게 Encoding해서 서버에 보낼지 -->
	<!-- 파일 업로드가 필요한 경우 HTML 폼의 속성을 "multipart/form-data"로 설정 -->
	<!-- 	@RequestMapping(value="/write.do", method = RequestMethod.POST) -->
	<table class="boardWrite">
		<tr>
			<th><label for="subject">제목</label></th>
			<td>
				<input type="text" id="subject" name="subject" class="boardSubject" />
				<input type="hidden" id="writer" name="writer" value="${userName}" />
				<input type="hidden" id = "writerId" name="writerId" value="${userId}" />
				<!-- LoginController에서 session에 userName과 userId를 올려놨기 때문에 쓸 수 있음! -->
			</td>
		</tr>
		<tr>
			<th><label for="content">내용</label></th>
			<td><textarea id="content" name="content" class="boardContent"></textarea></td>
		</tr>
		<tr>
			<th><label for="file">파일</label></th>
			<td><input type="file" id="file" name="file" /><span class="date">&nbsp;&nbsp;*&nbsp;임의로 파일명이 변경될 수 있습니다.</span></td>
		</tr>
	</table>
	<br/>
	<input type="reset" value="재작성" class="writeBt" />
	<input type="submit" value="확인" class="writeBt" />
	</form>
</div>
</body>
</html>