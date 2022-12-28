<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>info</title>
	<style>
		table td { text-align: left }
	</style>
</head>
<body>
	<h3>방명록 안내</h3>
	<table class="w-px1200">
		<colgroup>
			<col width='140px'>
			<col>
			<col width='160px'>
			<col width='160px'>
			<col width='100px'>
			<col width='100px'>
		</colgroup>
		<tr><th>제목</th>
			<td colspan='5'>${vo.title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.name}</td>
			<th>작성일자</th>
			<td>${vo.writedate}</td>
			<th>조회수</th>
			<td>${vo.readcnt}</td>
		</tr>
		<tr><th>내용</th>
			<td colspan='5'>${vo.content}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan='5'></td>
		</tr>
	</table>
</body>
</html>