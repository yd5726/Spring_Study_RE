<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>modify</title>
</head>
<body>
	<h3>사원정보수정</h3>
	<form method="post" action="update.hr">
		<table class='w-px600'>
			<tr><th class='w-px140'>사번</th>
				<td>
					<input type='text' name='employee_id' value='${vo.employee_id}' readonly>
				</td>
			</tr>
			<tr><th>사원명</th>
				<td>
					성 <input type='text' name='last_name' value='${vo.last_name}' placeholder="성">
					명 <input type='text' name='first_name' value='${vo.first_name}' placeholder="명">
				</td>
			</tr>
			<tr><th>이메일</th>
				<td>
					<input type='text' name='email' value='${vo.email}'>
				</td>
			</tr>
			<tr><th>전화번호</th>
				<td>
					<input type='text' name='phone_number' value='${vo.phone_number}'>
				</td>
			</tr>
			<tr><th>부서</th>
				<td>	
					<select name='department_id' class='w-px200'>
						<option value='-1'>부서선택</option>
						<c:forEach items='${departments}' var='d'>
							<option 
								<c:if test='${vo.department_id eq d.department_id}'>selected</c:if>
								value='${d.department_id}' >${d.department_name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr><th>업무명</th>
				<td>
					<select class='w-px300' name='job_id'>
						<c:forEach items='${jobs}' var='j'>
							<option ${vo.job_id eq j.job_id ? 'selected' : '' } 
								value='${j.job_id}'>${j.job_title}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr><th>급여</th>
				<td>
					<input type='text' name='salary' value='${vo.salary}'>
				</td>
			</tr>
			<tr><th>입사일자</th>
				<td>
					<input type='text' name='hire_date' value='${vo.hire_date}'>
				</td>
			</tr>
		</table>
	</form>
	<div class='btnSet'>
		<a class='btn-fill' href='javascript:$("form").submit()'>저장</a>
		<!-- <a class='btn-fill' onclick='$("form").submit()'>저장</a> -->
		<a class='btn-empty' href='info.hr?id=${vo.employee_id}'>취소</a>
	</div>
</body>
<style>
	input[name=employee_id] {
		border: none; padding: 0;
	}
	input[name=last_name], input[name=first_name] { width: 150px }
	table td { text-align: left; }
</style>
</html>