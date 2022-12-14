<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- <link href="resources/css/common.css" rel="stylesheet" type="text/css"> -->
<%-- <link href="css/common.css?<%=new java.util.Date() %>" rel="stylesheet" type="text/css"> --%>
<style>
header {
	border-bottom: 1px solid #aaa;
	align-items: center; 
	justify-content: space-between;
	padding: 0 100px;
	display: flex;
}
header nav, header ul { display: flex; }
header nav ul { font-size: 18px; font-weight: bold; }
header nav ul li:not(:first-child) { margin-left: 50px; }
header nav li a.active, header nav li a:hover { color: #0040ff; font-weight: bold; }
header div li:not(:first-child){ margin-left: 5px; }
</style>
<!-- <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.1.min.js"></script>  -->
<header>
<nav>
	<ul>
		<li><a href='<c:url value="/"/>'><img src='img/hanul.logo.png'></a></li>
		<!-- 해당 카테고리 변화 -->
		<li><a href='list.cu' ${category eq 'cu' ? 'class="active"' : ''}>고객관리</a></li>
		<li><a href='list.hr' ${category eq 'hr' ? 'class="active"' : ''}>사원관리</a></li>
		<li><a href='list.no' ${category eq 'no' ? 'class="active"' : ''}>공지사항</a></li>
		<li><a>방명록</a></li>
		<li><a>공공데이터</a></li>
		<li><a>시각화</a></li>
	</ul>
</nav>
<div>
	<ul>
		<!-- 로그인하지 않은 경우 -->
		<c:if test="${empty loginInfo}">
			<li><a class='btn-fill' href='login'>로그인</a></li>
			<li><a class='btn-fill' href='member'>회원가입</a></li>
		</c:if>
		<!-- 로그인한 경우 -->
		<c:if test="${not empty loginInfo}">
			<li><strong>${loginInfo.name}</strong></li>
			<li><a class='btn-fill' href='logout'>로그아웃</a></li>
		</c:if>
	</ul>
</div>
</header>