package com.and.middle;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import and_member.AndMemberVO;

@RestController
public class AndMemberController {
	@Autowired @Qualifier("hanul") private SqlSession session;
	@RequestMapping(value = "/login.me", produces ="text/html;charset=UTF-8")	
	public String login_me(AndMemberVO vo) {
		AndMemberVO temp_vo = session.selectOne("me.login", vo);
		
		if(temp_vo != null) {
			System.out.println(temp_vo.getEmail());
		}else {
			System.out.println("로그인 실패");
		}
		return new Gson().toJson(temp_vo);
	}
	
	@RequestMapping(value = "/social.me", produces ="text/html;charset=UTF-8")	
	public String social_me(String email) {
		System.out.println(email);
		return new Gson().toJson("");
	}
}
