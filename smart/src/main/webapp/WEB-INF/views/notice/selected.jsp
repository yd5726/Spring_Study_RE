<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>selected</title>
	<style type="text/css">
		table td { text-align: left; }
	</style>
</head>
<body>
	<h3>선택된 공지글</h3>
	<table class='w-px1200'>
		<tr>
			<th class='w-px140'>제목</th>
			<td colspan='5'>${vo.title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.name}</td>
			<th class='w-px160'>작성일자</th>
			<td class='w-px160'>${vo.writedate}</td>
			<th class='w-px140'>조회수</th>
			<td class='w-px140'>${vo.readcnt}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan='5'>${fn: replace(vo.content, crlf, '<br>')}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan='5'>${vo.filename}
				<c:if test='${not empty vo.filename }'>
					<a id='download'><i class='font-b fa-solid fa-file-arrow-down'></i></a>
				</c:if>
			</td>
		</tr>
	</table>
	<div class='btnSet'>
		<a class='btn-fill' href='list.no'>공지글 목록</a>
		<!-- 작성자가 로그인한 경우만 수정/삭제 가능 -->
		<c:if test='${loginInfo.userid eq vo.writer}'>
			<a class='btn-fill' href='modify.no?id=${vo.id}'>공지글 수정</a>
			<a class='btn-fill remove'>공지글 삭제</a>
		</c:if>
		<!-- 로그인한 경우 답글쓰기 가능 -->
		<c:if test='${ ! empty loginInfo }'>
			<a class='btn-fill' href='reply.no?id=${vo.id}'>답글쓰기</a>
		</c:if>
	</div>
	<script type="text/javascript">
		$('.remove').click(function(){
			if(confirm('[${vo.writer}]님, 정말 삭제하시겠습니까?')){
				location='delete.no?id=${vo.id}';
			}
		})
		$('#download').on('click', function(){
			$(this).attr('href','download.no?id=${vo.id}&url='+ $(location).attr('href'));
		});
	</script>
</body>
</html>