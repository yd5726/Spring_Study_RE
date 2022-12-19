<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>join</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
</head>
<style>
	table td { text-align: left; padding: 5px 0 5px 10px; }
	p { width: 600px; text-align: right; margin: 10px auto; color: #ff0000; }
	form span{ color: #ff0000; margin-right: 5px; }
	[name=address] { margin-top: 3px; }
	.ui-datepicker tr{ height: inherit; }
</style>
<body>
	<h3>회원 가입</h3>
	<p>*는 필수 입력 항목입니다.</p>
	<form method='post' action='join'>
		<table class='w-px600'>
			<tr><th class='w-px140'><span>*</span>성명</th>
				<td><input type='text' name='name' autofocus></td>
			</tr>
			<tr><th><span>*</span>아이디</th>
				<td>
					<input type='text' name='userid' class='chk'>
					<a class='btn-fill' id='btn-userid'>아이디 중복확인</a>
					<div class='valid'>아이디를 입력하세요(영문소문자, 숫자만)</div>
				</td>
			</tr>
			<tr><th><span>*</span>비밀번호</th>
				<td>
					<input type='password' name='userpw'>
					<div class='valid'>비밀번호를 입력하세요(영문대/소문자, 숫자 모두 포함)</div>
				</td>
			</tr>
			<tr><th>비밀번호 확인</th>
				<td>
					<input type='password' name='userpw_ck'>
					<div class='valid'>비밀번호를 다시 입력하세요</div>
				</td>
			</tr>
			<tr><th>프로필 이미지</th>
				<td><div class='align'>
						<label>
							<input type='file' id='attach-file' accept="image/*" name='profile_image'>
							<a><i class='font-b fa-regular fa-address-card'></i></a>
						</label>
						<span id='preview'></span>
						<a id='delete-file'><i class='font-r fa-solid fa-trash-can'></i></a>
					</div>
				</td>
			</tr>
			<tr><th><span>*</span>성별</th>
				<td>
					<label><input type='radio' name='gender' value='남' checked>남</label>
					<label><input type='radio' name='gender' value='여'>여</label>
				</td>
			</tr>
			<tr><th>이메일</th>
				<td>
					<input type='text' name='email' class='chk'>
					<div class='valid'>이메일을 입력하세요</div>
				</td>
			</tr>
			<tr><th>생년월일</th>
				<td>
					<input type='text' name='birth' class='date' readonly>
					<a id='delete'><i class='font-r fa-regular fa-calendar-xmark'></i></a>
				</td>
			</tr>
			<tr><th>전화번호</th>
				<td><input type='text' name='phone' maxlength='13'></td>
			</tr>
			<tr><th>주소</th>
				<td>
					<a class='btn-fill' id='post'>우편번호 찾기</a>
					<input type='text' name='post' class='w-px60' readonly>
					<input type='text' name='address' class='full'readonly>
					<input type='text' name='address' class='full'>
				</td>
			</tr>
		</table>
	</form>
	<!-- 생년월일 - https://jqueryui.com/datepicker/ -->
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
	<!-- 주소 - https://postcode.map.daum.net/guide -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src='js/member.js?<%=new java.util.Date()%>'></script>
	<script>
		// 다음 우편번호 찾기 API로 우편번호와 기본주소를 조회한다.
		$('#post').click(function(){
			new daum.Postcode({
				oncomplete: function(data){
					console.log(data);
					$('[name=post]').val(data.zonecode);
					var address = data.userSelectedType == 'R' ? data.roadAddress : data.jibunAddress;
					if(data.buildingName != ''){
						address += ' ('+ data.buildingName +')';
					}
					$('[name=address]').eq(0).val(address);
				}
			}).open();
		});
		
		// 생년월일 특정날짜(만13세)까지만 선택가능하도록 제한
		var today = new Date();
		var endDay = new Date(today.getFullYear()-13, today.getMonth(), today.getDate()-1);
		var range = today.getFullYear()-80 + ':' + endDay.getFullYear();
		$('.date').datepicker({
			yearRange: range,
			maxDate: endDay
		});
		
		$('.chk').keyup(function(){
			var status = member.tag_status($(this));
			$(this).siblings('div').text(status.desc).removeClass().addClass(status.code);
		});
		
		// 날짜 변경시 날짜 삭제버튼 나오게
		$('.date').change(function(){
			$(this).next().css('display','inline');
		});
		
		// 날짜 삭제버튼 클릭시 날짜 없애고, 날짜 삭제 버튼도 안나오게
		$('#delete').click(function(){
			$(this).css('display','none');			
			$(this).siblings('.date').val('');			
		});
		
		// 체크 사항
		$('.chk').keyup(function(e){
			if($(this).attr('name') == 'userid' && e.keyCode == 13){	// 아이디에서 Enter 시 중복확인처리
				idCheck();
			}else{
				$(this).removeClass('chked');
				var status = member.tag_status($(this));
				$(this).siblings('div').text(status.desc).removeClass().addClass(status.code);
			}
		});
		
		// 아이디 중복 확인
		$('#btn-userid').click(function(){
			idCheck();
		});
		function idCheck(){
			var $userid = $('[name=userid]');
			// 이미 중복확인했다면 재확인이 불필요하다.
			if($userid_hasClass('chked')){
				return;
			}
			
			var status = member.tag_status($userid);
			if(status.code == 'invalid'){
				alert('아이디 중복확인이 필요하지 않습니다.\n' + status.desc);
				$userid.focus();
			}else{
				$.ajax({
					url: 'idCheck',
					data: { id: $userid.val() },
					success: function(response){
						// false : 아이디 존재x, true : 아이디 존재o
						status = response ? member.userid.unUsable : member.userid.usable;
						$userid.siblings('div').text(status.desc).removeClass().addClass(status.code);
						
						// 중복확인완료지정
						$userid.addClass('chked');
					}, error: function(req, text){
						alert(text+":"+req.status);
					}
				});
			}
		}
	</script>
</body>
</html>