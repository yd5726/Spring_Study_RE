package co.kr.smart;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import customer.CutomerServiceImpl;

/*Spring 실행순서 : 사용자 - 컨트롤러 - 서비스 - DAO - 매퍼 - 서비스 - 뷰 - 사용자*/
@Controller
public class CustomerController {
	private CutomerServiceImpl service;
	public CustomerController(CutomerServiceImpl service) {
		this.service = service;
	}
	
	// 고객목록화면 요청
	@RequestMapping("/list.cu")
	public String list(HttpSession session) {
		// 해당 카테고리 변화
		session.setAttribute("category", "cu");
		// 비지니스로직
		// DB에서 고객목록을 조회해와 화면에 출력할 수 있도록 Model에 attribute로 담는다.
		
		// 응답화면연결
		return "customer/list";
	}
}
