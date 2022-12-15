package co.kr.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.CommonService;
import member.MemberService;
import member.MemberVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired private MemberService member;
	@Autowired private CommonService common;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		//암호화하지 않은 비번에 대해 암호화해서 저장하는 처리----
		/*
		  List<MemberVO> list = member.member_list();
		  for(MemberVO vo : list) {
			  // 비밀번호가 있는 회원(소셜로그인 아닌 회원)에 대해 암호화에 사용할 salt를 만든다.
			  if(vo.getUserpw() != null) {
				  String salt = common.generateSalt();
				  String pw = common.getEncrypt(salt, vo.getUserpw());
				  // 회원정보 변경 처리
				  vo.setSalt(salt);
				  vo.setUserpw(pw);
				  member.member_myInfo_update(vo);
			  }
		  }
		*/
		//------------------------------------------
		
		/* 해당 카테고리 변화 */
		//session.setAttribute("category", "");
		session.removeAttribute("category");
		return "home";
	}
	
}
