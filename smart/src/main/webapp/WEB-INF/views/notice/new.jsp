<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>new</title>
</head>
<body>
	<h3>새 공지글 등록</h3>
	<form method="post" action="insert.no">
		<table class='tb-list w-px600'>
			<tr><th class='w-px140'>제목</th>
				<td><input type='text' name='title'></td>
			</tr>
			<tr><th>내용</th>
				<td><input type='text' name='content'></td>
			</tr>
			<tr><th>작성자</th>
				<td>
					<select name='writer' class='w-px200'>
						<c:forEach items='${notice}' var='n'>
							<option value='${n.writer}'>${n.writer}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</form>
	<div class='btnSet'>
		<a class='btn-fill' onclick="$('form').submit()">저장</a>
		<a class='btn-empty' href='list.no'>취소</a>
	</div>
</body>
</html>