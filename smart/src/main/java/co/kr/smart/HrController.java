package co.kr.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.EmployeeVO;
import hr.HrService;

@Controller
public class HrController {
//	@Autowired private HrService service;
	private HrService service;
	public HrController(HrService hr) {
		this.service = hr;
	}
	
	// 사원목록화면 요청
	@RequestMapping("/list.hr")
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "hr");
		// 비지니스로직
		// DB에서 사원목록을 조회해서
		// 화면에 출력할 수 있도록 Model에 attribute로 담는다.
		/*
		List<EmployeeVO> list = service.employee_list();
		model.addAttribute("list",list);
		*/
		model.addAttribute("list",service.employee_list());
		
		// 응답화면연결
		return "employee/list";
	}

}
