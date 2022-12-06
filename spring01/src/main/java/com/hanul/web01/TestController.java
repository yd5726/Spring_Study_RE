package com.hanul.web01;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import member.MemberVO;

@Controller
public class TestController {
	// 로그인 처리 요청
	@RequestMapping("/login_result")
	public String login(String id, String pw) {	//@RequestParam 생략됨
		// 테스트 용) id: admin, pw: 1234 이면 성공
		if(id.equals("admin") && pw.equals("1234")){
			// 로그인 성공 - welcome 화면으로
			//return "home"; 		// forward 방식
			return "redirect:/";	// redirect 방식
		}else {
			// 로그인 실패 - login 화면으로
			//return "member/login"; 	// forward 방식
			return "redirect:login";	// redirect 방식
		}
	}
	
	// 로그인 화면 요청
	@RequestMapping("/login")
	public String login() {
		// 응답화면연결
		return "member/login";
	}
	
	// 회원가입 - @PathVariable 방식(경로의 형태)으로 파라미터 접근
	@RequestMapping("/joinPath/{name}/{g}/{email}")
	public String join(@PathVariable String name, Model model,		/*데이터 오버로딩되도록 파라미터 순서 바꿈*/ 
					   @PathVariable("g") String gender, 			/*String,String,String,Model이 이미 있어서*/
					   @PathVariable String email) {
		// 비지니스로직 - 경로를 통해 받은 정보를 화면에 출력할 수 있도록 모델에 담는다.
		model.addAttribute("name",name);
		model.addAttribute("email",email);
		model.addAttribute("gender",gender);
		model.addAttribute("method","@PathVariable 방식");
		
		// 응답화면연결
		return "member/info";
	}
	
	// 회원가입 - 데이테 객체 방식으로 파라미터 접근
	@RequestMapping("/joinDataObject")
	public String join(Model model, MemberVO vo) {
		// 비지니스로직 - 파라미터 정보를 화면에 출력할 수 있도록 모델에 담는다.
		model.addAttribute("method","데이터 객체 방식");	/*데이터 객체 : DTO, VO, 도메인*/
		model.addAttribute("vo",vo);
		
		// 응답화면연결
		return "member/info";
	}
	
	// 회원가입 - @RequestParam 방식으로 파라미터 접근
	@RequestMapping("/joinParam")
	public String join(@RequestParam String name, 
					   @RequestParam("email") String mail,    /* 이름이 같아야한다. 또는 다른 이름 인지시키기.*/
					   @RequestParam("gender") String code,
					   Model model) {
		// 비지니스로직 - 파라미터 정보를 화면에 출력할 수 있도록 모델에 담는다.
		model.addAttribute("name",name);
		model.addAttribute("gender",code);
		model.addAttribute("email",mail);
		model.addAttribute("method","@RequestParam 방식");
		
		/// 응답화면연결
		return "member/info";
	}
	
	// 회원가입 - HttpServletRequest 방식으로 파라미터 접근
	@RequestMapping("/joinRequest")
	public String join(HttpServletRequest request, Model model) {
		// 비지니스로직 - 파라미터 정보를 화면에 출력할 수 있도록 모델에 담는다.
		model.addAttribute("name", request.getParameter("name"));
		model.addAttribute("gender", request.getParameter("gender"));
		String mail = request.getParameter("email");                 /* 이름이 같아야한다. 또는 다른 이름 인지시키기.*/
		model.addAttribute("email",mail);
		model.addAttribute("method","HttpServletRequest 방식");
		
		// 응답 화면 연결
		return "member/info";
	}

	
	@RequestMapping("/member")
	public String member() {
		return "member/join";
	}
	
	
	@RequestMapping("/third")
	public ModelAndView third() {
		//비지니스 로직
		//현재 날짜, 시각을 화면에 출력할 수 있도록 한다.
		ModelAndView model = new ModelAndView();
		String today
		= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		model.addObject("todayNow", today); //데이터 저장
		model.setViewName("index");  //화면연결
		return model;
	}
	
	
	@RequestMapping("/second")
	public String second(Model model) {
		//비지니스 로직
		//현재 시각을 화면에 출력할 수 있도록 Model타입의 변수에 attribute 로 담는다
		String now
		= new SimpleDateFormat("HH:mm:ss").format(new Date());
		model.addAttribute("now", now);
		return "index";
	}
	
	//웹브라우저 주소에 /first 라고 요청할 때 이 메소드를 실행하게 하고자 한다.
	//요청에 대해 이 메소드를 연결: @RequestMapping
	@RequestMapping("/first")
	public String first( Model model ) {
		//비지니스 로직
		//오늘 날짜를 화면에 출력할 수 있도록 저장한다
		//: Model타입에 attribute로 저장(addAttribute)
		String today 
		= new SimpleDateFormat("yyyy-MM-dd").format( new Date() );
		model.addAttribute("today", today);
		
		//응답화면연결
		return "index";
	}
	
}