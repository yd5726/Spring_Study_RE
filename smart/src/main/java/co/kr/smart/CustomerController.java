package co.kr.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import customer.CustomerService;
import customer.CustomerVO;

/*Spring 실행순서 : 사용자 - 컨트롤러 - 서비스 - DAO - 매퍼 - 서비스 - 뷰 - 사용자*/
@Controller
public class CustomerController {
	//private CutomerServiceImpl service;
	private CustomerService service;	// 다형성 적용
	/*
	public CustomerController(CutomerServiceImpl service) {
		this.service = service;
	}
	*/
	// 에러 발생 => 해결 : 이름 명명(@Service("customer")) 
	/*
	public CustomerController(CustomerService service) {
		this.service = service;
	}
	*/
	// 이름 명명 이유 : CutomerServiceImpl, CustomerDAO implements CustomerService
	//              두 개 중에 어떤 것을 선택할 것인지 구분해주어야함
	// 이름 명명은 CutomerServiceImpl.java에 해둠
	public CustomerController(CustomerService customer) {
		this.service = customer;
	}
	
	// 고객목록화면 요청
	@RequestMapping("/list.cu")
	public String list(HttpSession session, Model model) {
		// 해당 카테고리 변화
		session.setAttribute("category", "cu");
		// 비지니스로직
		// DB에서 고객목록을 조회해와 화면에 출력할 수 있도록 Model에 attribute로 담는다.
		List<CustomerVO> list = service.customer_list();
		model.addAttribute("list",list);
		// 응답화면연결
		return "customer/list";
	}
	
	// 고객정보화면 요청
	@RequestMapping("/info.cu")
	public String info(int id, Model model) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 조회해와서
		CustomerVO vo = service.customer_info(id);
		// 화면에 출력할 수 있도록 Model에 attribute에 담는다.
		model.addAttribute("vo",vo);
		
		// 응답화면연결
		return "customer/info";
	}
	
	// 고객정보수정화면 요청
	@RequestMapping("/modify.cu")
	public String modify(int id, Model model) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 조회해와서
		CustomerVO vo = service.customer_info(id);
		// 수정화면에 출력할 수 있도록 Model에 attribute에 담는다.
		model.addAttribute("vo",vo);
		
		// 응답화면연결
		return "customer/modify";
	}
	
	// 고객정보수정저장처리 요청
	@RequestMapping("/update.cu")
	public String update(CustomerVO vo) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 변경저장한 후
		service.customer_update(vo);
	
		// 응답화면연결
		// input hidden을 통해 id 값 넘김
		return "redirect:info.cu?id=" + vo.getId();
	}
	
	// 고객정보삭제처리 요청
	@RequestMapping("/delete.cu")
	public String delte(int id) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 삭제한 후
		service.customer_delete(id);
		
		// 응답화면연결
		return "redirect:list.cu";
	}
	
	// 신규고객정보등록화면 요청
	@RequestMapping("/new.cu")
	public String customer() {
		// 응답화면연결
		return "customer/new";
	}
	
	// 신규고객정보저장처리 요청
	@RequestMapping("/insert.cu")
	public String insert(CustomerVO vo) {
		// 비지니스로직
		// 화면에서 입력한 고객정보를 DB에서 신규저장처리한 후
		service.customer_insert(vo);
		
		// 응답화면연결
		return "redirect:list.cu";
	}
}
