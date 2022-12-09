<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<h3>사원목록</h3>
	<div class='tb-wrap w-px800'>
		<table class='tb-list'>
			<colgroup>
				<col width='80px'>
				<col width='200px'>
				<col width='100px'>
				<col>
				<col width='120px'>
			</colgroup>
			<tr><th>사번</th>
				<th>사원명</th>
				<th>부서코드</th>
				<th>업무코드</th>
				<th>입사일자</th>
			</tr>
			<c:forEach items='${list}' var='vo'>
				<tr><td>${vo.employee_id }</td>
					<td>${vo.last_name } ${vo.first_name }</td>
					<td>${vo.department_id }</td>
					<td>${vo.job_id }</td>
					<td>${vo.hire_date }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>