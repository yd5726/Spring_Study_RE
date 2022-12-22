/**
 * 공통 함수 선언
 */
 
 $(function(){
 	if($('.date').length > 0){
 		// 달력 UI 속성지정
		$.datepicker.setDefaults({
			dateFormat: 'yy-mm-dd',
			dayNamesMin: ['일','월','화','수','목','금','토'],
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			showMonthAfterYear: true,	//년도 뒤에 월 표시
			changeYear: true,
			changeMonth: true
		});
	}
	
	// 파일 첨부 관련 처리
	$('#attach-file').change(function(){
		console.log(this.files[0]); // 선택한 파일 정보
		var attached = this.files[0];
		// 파일을 실제 선택한 경우
		if(attached){
			//선택한 파일명이 보이게
			$('#file-name').text(attached.name);
			//선택한 파일삭제할 버튼 보이게
			$('#delete-file').css('display', 'inline');
			// 미리보기 태그가 있는 경우 선택한 이미지 파일을 보이게 처리
			if($("#preview").length > 0){
				// 실제 선택한 파일이 이미지인 경우만
				if(isImage(attached.name)){
					$("#preview").html('<img class="profile">');
					var reader = new FileReader();
					reader.onload = function(e){
						$('#preview img').attr('src', e.target.result);
					}
					reader.readAsDataURL(attached);
				}else{
					// 프로필이미지처럼 반드시 이미지만 첨부해야 하는 경우는 
					// 이미지가 아닌 파일을 선택했다면 쓰레기통이 안보이게 accept="image/*"
					if($(this).attr('accept')=='image/*'){ 
							$('#delete-file').css('display', 'none');
					}
					// 첨부한 파일 정보 없애기 - 회원 가입시 이미지 아닌거 저장 안되게 테스트
					//$('#attach-file').val('');
					//이미지가 아니면 미리보기태그만 삭제
					$('#preview').empty();
				}
			}else{
				//파일 첨부 클릭후 취소한 경우
				initAttach();	
			}
		}
	});
	
	function initAttach(){
		//보여졌던 파일명 없애기
		$('#file-name').text('');
		// 첨부한 파일 정보 없애기
		$('#attach-file').val('');
		// 삭제 버튼 안 보이게
		$('#delete-file').css('display','none');
		// 이미지 안 보이게
		$('#preview img').remove();	
	}
	
	// 파일 삭제 버튼 클릭 시
	$('#delete-file').click(function(){
		// 실제 첨부한 파일정보 없애기
		$('#delete-file').val('');
		// 삭제 버튼 안 보이게
		$(this).css('display','none');
		// 방법 1) 이미지 안 보이게
		//$('#preview').html();
		// 방법 2) 이미지 안 보이게
		$('#preview img').remove();
	});
});

function isImage(filename){
	// 파일의 확장자로 이미지 파일인지 판단
	var ext = filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
	var imgs = ['png','jpg','jpeg','bmp','gif','webp'];
	if(imgs.indexOf(ext) == -1){
		return false;	// 이미지가 아니다.
	}else{
		return true;	// 이미지 이다.
	}	
}
 
 // 필수입력항목 입력여부확인 함수 - 회원가입, 공지글
 function emptyCheck(){
	var ok = true;
	$('.chk').each(function(){
		if($.trim($(this).val()) == ''){
			var title = $(this).attr('placeholder') 
						?  $(this).attr('placeholder') : $(this).attr('title');
			alert(title + ' 입력하세요!');			
			$(this).val('');
			$(this).focus();
			ok = false;
			return ok;
		}
	});
	return ok;
}