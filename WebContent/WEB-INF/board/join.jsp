<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Join Member!</title>
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.1.js"></script>
<c:if test="${errCode == null}">
	<c:set var="errCode" value="\"\""></c:set>
</c:if>
<script  type="text/javascript">
	function errCodeCheck() {
		var errCode = ${errCode};
		if(errCode != null || errCode != "") {
			switch (errCode) {
			case 1:
				alert("이미 가입된 이메일 주소입니다!");
				break;
			case 2:
				alert("회원가입 처리가 실패하였습니다. 잠시 후 다시 시도해 주십시오.");
				break;
			}
		}
	}
	
	function passwordCheck() {
		if($("#userPw").val() != $("#userPwCheck").val()) {
			alert("패스워드 입력이 일치하지 않습니다");
			$("#userPwCheck").focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body onload="errCodeCheck()">
<div class="wrapper">
	<h3>회원가입</h3>
	<spring:hasBindErrors name="MemberModel" />
	<form:errors path="MemberModel" />
	<%-- <spring:hasBindErrors> 커스텀 태그는 name 속성에 명시한 커맨드 객체와 에러 정보를 --%> 
	<%-- <form:errors> 커스텀 태그에서 사용할 수 있도록 설정한 후 --%> 
	<%-- <form:errors> 태그를 이용하여 특정 프로퍼티와 관련된 에러 Message를 출력할 수 있다. --%>
	<form action="join.do" method="post" onsubmit="return passwordCheck()">
		<fieldset> <!-- 폼양식에서 관계된 요소들끼리 묶어주면 관리요소 주위에 박스를 그림 -->
			<label for="userId">&nbsp;메일주소 : </label> <!-- 양식 입력창을 설명하는 이름표 -->
			<input type="text" id="userId" name="userId" class="loginInput" />
			<span class="error"><form:errors path="MemberModel.userId" /></span><br/>
			<!-- path로 property를 받아온다 -->
			<!-- MemberModel.userId에 error가 있으면 아이디를 입력하세요 오류 메시지 출력-->
			<label for="userPw">&nbsp;비밀번호 : </label>
			<input type="password" id="userPw" name="userPw" class="loginInput" />
			<span class="error"><form:errors path="MemberModel.userPw" /></span><br/>
			<label for="userPw">&nbsp;비밀번호 확인 : </label>
			<input type="password" id="userPwCheck" name="userPwCheck" class="loginInput" /><br/>
			<label for="userName">&nbsp;회원이름 : </label>
			<input type="text" id="userName" name="userName" class="loginInput" />
			<span class="error"><form:errors path="MemberModel.userName" /></span><br/><br/>
			<center>
			<input type="submit" value="확인" class="submitBt" />
			<input type="reset" value="재작성" class="submitBt" /><br/><br/>
			<a href="<%=request.getContextPath()%>/login.do">로그인 페이지로 돌아가기</a>	
			</center>
		</fieldset>
		</form>
</div>
</body>
</html>