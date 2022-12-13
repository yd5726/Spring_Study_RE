<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>list</title>
</head>
<body>
	<h3>공지사항</h3>
	<div id='list-top'>
		<ul>
			<li><a class='btn-fill' href='new.no'>새 공지글 등록</a></li>
		</ul>
	</div>
	<div class='tb-wrap w-px800'>
		<table class='tb-list'>
			<colgroup>
				<col width='50px'>
				<col width='100px'>
				<col width='200px'>
				<col width='80px'>
				<col width='80px'>
				<col width='50px'>
			</colgroup>
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>내용</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
			<c:forEach items='${list}' var='vo'>
				<tr><td>${vo.id }</td>
					<td><a href='selected.no?id=${vo.id}'>${vo.title}</a></td>
					<td>${vo.content}</td>
					<td>${vo.writer}</td>
					<td>${vo.writedate}</td>
					<td>${vo.readcnt}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>