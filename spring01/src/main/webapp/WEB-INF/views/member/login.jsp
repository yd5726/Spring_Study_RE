<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>login</title>
</head>
<body>
	<h1>로그인</h1>
	<form method='post' action='login_result'>
		<div>아이디: <input type='text' name='id'></div>
		<div>비밀번호: <input type='text' name='pw'></div>
		<input type='submit' value="로그인">
	</form>
</body>
</html>