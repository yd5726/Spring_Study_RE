<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>login</title>
</head>
<body>
	<div class='center'>
		<div class='box'>
			<ul>
				<li><input type='text' id='userid' class='chk' placeholder='아이디'></li>
				<li><input type='password' id='userpw' class='chk' placeholder='비밀번호'></li>
				<li><input type='button' value='로그인' class='login'></li>
			</ul>
		</div>
	</div>
	<script>
		$('.login').click(function(){
			login();
		});
		$('#userpw').keydown(function(e){
			if(e.keyCode == 13){
				login();
			}
		})
		function login(){
			if(emptyCheck()){
				alert('확인!!!')			
			}
		}
	</script>	
</body>
</html>