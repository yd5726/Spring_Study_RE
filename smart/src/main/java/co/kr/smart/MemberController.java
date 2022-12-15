package co.kr.smart;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.CommonService;
import member.MemberService;
import member.MemberVO;

@Controller
public class MemberController {
//	@Autowired private MemberServiceImpl service;
	@Autowired private MemberService member;
	@Autowired private CommonService common;
//	private MemberService member;
//	public MemberController(MemberService member) {
//		this.member = member;
//	}
	
	// 비밀번호 변경 화면 요청
	@RequestMapping("/changePW")
	public String changePW() {
		return "member/change";
	}
	
	//비밀번호 재발급처리 요청 - 이 자체가 응답(@ResponseBody)
	@ResponseBody @RequestMapping(value = "/reset", produces = "text/html; charset = UTF-8")
	public String reset(MemberVO vo) {
		// 비지니스로직
		// 화면에서 입력한 아이디/이메일이 일치하는 회원에게
		// 임시 비번을 발급해준다.
		// 임시 비번을 생성한 후 회원정보에 변경저장하고, 임시 비번을 회원에게 이메일로 알려준다.
		String name = member.member_userid_email(vo);
		if(name == null) {
			StringBuffer msg = new StringBuffer("<script>");	
			msg.append("alert('아이디나 이메일이 맞지 않습니다. \\n확인하세요!');");
			msg.append("history.go(-1);");  // 이전 화면으로 이동
			msg.append("</script>");
			return msg.toString();
		}
		// 임시 비번을 생성
		String pw = UUID.randomUUID().toString(); //afsfisf-safsaff-safsbion12
		pw = pw.substring(pw.lastIndexOf("-")+1); //safsbion12
		
		// 임시 비번 암호화
		String salt = common.generateSalt();
		vo.setSalt(salt);
		vo.setUserpw(common.getEncrypt(salt, pw));
		vo.setName(name);
		
		// 회원정보에 변경저장 + 이메일 보내는 처리
		StringBuffer msg = new StringBuffer("<script>");
		if(member.member_myInfo_update(vo) == 1 && common.sendPassword(vo, pw)) {
			msg.append("alert('임시 비밀번호가 발급되었습니다. \\n이메일을 확인하세요.');");
			msg.append("location='login';"); // 임시 비번으로 로그인하도록, 로그인 화면으로 이동
		}else {
			msg.append("alert('임시 비밀번호 발급이 실패하였습니다...');");
			msg.append("history.go(-1);"); // 이전 페이지로 이동
		}
		msg.append("</script>");
		
		return msg.toString();
	}
	
	// 비밀번호 찾기 화면 요청 - 비밀번호 재발급 (임시비번발급) 화면
	@RequestMapping("/find")
	public String find() {
		return "default/member/find";
	}
	
	// 로그인처리 요청
	@ResponseBody @RequestMapping("/smartLogin")
	public boolean login(String id, String pw, HttpSession session) {
		// 비지니스로직
		// 화면에서 입력한 아이디/비먼이 일치하는 회원정보를 DB에서 조회해온
		String salt = member.member_salt(id);
		// slat를 사용해 화면에서 입력한 비번을 암호화 한다.
		pw = common.getEncrypt(salt, pw);
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pw", pw);
		MemberVO vo = member.member_login(map);
		// 화면에 출력할 수 있도록 세션에 attribute로 담는다.
		session.setAttribute("loginInfo", vo);
		
		return vo == null ? false : true;
	}
	
	// 로그인화면 요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("category", "login");
		return "default/member/login";
	}
	
	// 로그아웃화면 요청
	@RequestMapping("/logout")
	public String loginout(HttpSession session) {
		// 비지니스로직
		// 세션에 있는 로그인 정보를 삭제한다.
		session.removeAttribute("loginInfo");
		
		// 응답화면 연결
		return "redirect:/";
	}
}