package co.kr.smart;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import customer.CustomerVO;
import notice.NoticeService;
import notice.NoticeVO;

@Controller
public class NoticeController {
	private NoticeService service;
	public NoticeController(NoticeService notice) {
		this.service = notice;
	}
	
	// 공지글목록화면 요청
	@RequestMapping("/list.no")
	public String notice_list(HttpSession session, Model model) {
		// 공지글관리 정보를 session에 담는다. -> 카테고리 선택시 변화 이벤트
		session.setAttribute("category", "no");
		// 비지니스로직
		// DB에서 공지글목록을 조회하고
		// 목록화면에 출력하도록 Model에 attribute로 담는다.
		model.addAttribute("list",service.notice_list());
		
		// 응답화면연결
		return "notice/list";
	}
	
	// 선택한 공지글화면 요청
	@RequestMapping("/selected.no")
	public String notice_selected(int id, Model model) {
		model.addAttribute("vo", service.notice_info(id));
		
		return "notice/selected";
	}
	
	// 고객정보삭제처리 요청
	@RequestMapping("/delete.no")
	public String delte(int id) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 삭제한 후
		service.notice_delete(id);
		
		// 응답화면연결
		return "redirect:list.no";
	}
	
	// 고객정보수정화면 요청
	@RequestMapping("/modify.no")
	public String modify(int id, Model model) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 조회해와서
		NoticeVO vo = service.notice_info(id);
		// 수정화면에 출력할 수 있도록 Model에 attribute에 담는다.
		model.addAttribute("vo",vo);
		
		// 응답화면연결
		return "notice/modify";
	}
	
	// 선택한 공지글 수정저장처리 요청
	@RequestMapping("/update.no")
	public String update(NoticeVO vo) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 변경저장한 후
		service.notice_update(vo);
	
		// 응답화면연결
		// input hidden을 통해 id 값 넘김
		return "redirect:selected.no?id=" + vo.getId();
	}
	
	// 새 공지글 등록화면 요청
	@RequestMapping("/new.no")
	public String customer(Model model) {
		// 비지니스 로직
		model.addAttribute("notice", service.hanul_notice_list());
		// 응답화면연결
		return "notice/new";
	}
	
	// 새 공지글 저장처리 요청
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo) {
		// 비지니스로직
		// 화면에서 입력한 고객정보를 DB에서 신규저장처리한 후
		service.notice_insert(vo);
		
		// 응답화면연결
		return "redirect:list.no";
	}
}
