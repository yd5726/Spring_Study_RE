package member;

import java.util.HashMap;
import java.util.List;

public interface MemberService {
	// 회원가입 시 회원정보 저장
	int member_join(MemberVO vo);
	
	// 로그인 처리
	MemberVO member_login(HashMap<String,String> map);
	
	// 회원가입 시 아이디 중복 확인
	int member_idCheck(String userid);
	
	// 내 정보 보기 : 마이 페이지
	MemberVO member_myInfo(String userid);
	
	// 마이페이지에서 회원정보 변경 저장
	int member_myInfo_update(MemberVO vo);
	
	// 회원 탈퇴시 회원정보 삭제
	int member_delete(String userid);
	
	// 관리자모드에서는 전체 회원목록 조회
	List<MemberVO> member_list();
}
