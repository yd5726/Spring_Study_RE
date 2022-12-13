<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<h3>선택한 공지글 수정</h3>
	<form method="post" action="update.no">
		<input type='hidden' name='id' value='${vo.id}'>
		<table class='tb-list w-px600'>
			<tr><th class='w-px140'>제목</th>
				<td><input type='text' name='title' value='${vo.title}'></td>
			</tr>
			<tr><th>내용</th>
				<td><input type='text' name='content' value='${vo.content}'></td>
			</tr>
		</table>
	</form>
	<div class='btnSet'>
		<a class='btn-fill'onclick="$('form').submit()">저장</a>
		<a class='btn-empty' href='selected.no?id=${vo.id}'>취소</a>
	</div>
</body>
</html>