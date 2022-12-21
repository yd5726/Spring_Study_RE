<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>selected</title>
</head>
<body>
	<h3>선택된 공지글</h3>
	<table class='tb-list w-px600'>
		<tr><th>글번호</th>
			<td>${vo.id}</td>
		</tr>
		<tr><th>제목</th>
			<td>${vo.title}</td>
		</tr>
		<tr><th>내용</th>
			<td>${vo.content}</td>
		</tr>
		<tr><th>작성자</th>
			<td>${vo.writer}</td>
		</tr>
		<tr><th>작성일</th>
			<td>${vo.writedate}</td>
		</tr>
		<tr><th>조회수</th>
			<td>${vo.readcnt}</td>
		</tr>
	</table>
	<div class='btnSet'>
		<!-- 작성자가 로그인한 경우만 수정/삭제 가능 -->
		<c:if test='${loginInfo.userid eq vo.writer}'>
			<a class='btn-fill' href='modify.no?id=${vo.id}'>공지글 수정</a>
			<a class='btn-fill remove'>공지글 삭제</a>
		</c:if>
		<a class='btn-fill' href='list.no'>공지글 목록</a>
	</div>
	<script>
		$('.remove').click(function(){
			if(confirm('[${vo.writer}]님, 정말 삭제하시겠습니까?')){
				location='delete.no?id=${vo.id}';
			}
		})
	</script>
</body>
</html>