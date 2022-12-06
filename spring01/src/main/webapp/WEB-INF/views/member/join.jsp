<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>join</title>
</head>
<body>
	<h3>회원가입</h3>
	<form method='post' action='joinRequest'>
		<div>성명: <input type='text' name='name'></div>
		<div>성별: <input type='radio' name='gender' value='남'>남
				  <input type='radio' name='gender' value='여' checked>여
		</div>
		<div>이메일: <input type='text' name='email'></div>
		<input type='submit' value='HttpServletRequest'>
		<input type='submit' value='@RequestParam' onclick='action="joinParam"'> <!-- 액션값 변경 -->
		<input type='submit' value='데이터객체' onclick='action="joinDataObject"'> <!-- 액션값 변경 -->
		<input type='submit' value='@PathVariable' 
			   onclick='fn_submit(this.form)'> <!-- 액션값 변경, 경로에 데이터 담아서 보내기 -->
	</form>
	<script>
		function fn_submit(f){
			//action="joinPath/홍길동/남/hong@naver.com"
			f.action ="joinPath/" + f.name.value + "/" + f.gender.value + "/" + f.email.value;
		}
	</script>
</body>
</html>