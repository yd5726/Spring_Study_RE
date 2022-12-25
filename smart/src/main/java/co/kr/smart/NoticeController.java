package co.kr.smart;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import member.MemberServiceImpl;
import member.MemberVO;
import notice.NoticePageVO;
import notice.NoticeService;
import notice.NoticeVO;

@Controller
public class NoticeController {
	private NoticeService service;
	@Autowired private CommonService common;
	@Autowired private MemberServiceImpl member;
	
	public NoticeController(NoticeService notice) {
		this.service = notice;
	}
	
	// 공지글목록화면 요청
	@RequestMapping("/list.no")
	public String notice_list(HttpSession session, Model model, NoticePageVO page) {
		// 테스트를 위한 임시 로그인 처리 ---------------
		String userid="admin", userpw="Admin1";
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("id", userid);
		// 입력한 비번을 salt를 사용해 암호화한 후 map에 담는다.
		String salt = member.member_salt(userid);
		userpw = common.getEncrypt(salt, userpw);
		map.put("pw", userpw);
		MemberVO vo = member.member_login(map);
		session.setAttribute("loginInfo", vo);
		// -----------------------------------
		// 카테고리 선택시 변화 이벤트
		session.setAttribute("category", "no");
		// 비지니스로직
		// DB에서 공지글목록을 조회하고
		// 목록화면에 출력하도록 Model에 attribute로 담는다.
			// 이건 전체 페이지 조회한 것이다.
			//model.addAttribute("list",service.notice_list());
			// 페이징 처리해서 조회한다.
			page.setCurPage(page.getCurPage());
			model.addAttribute("page", service.notice_list(page));
		// 응답화면연결
		return "notice/list";
	}
	
	// 선택한 공지글화면 요청
	@RequestMapping("/selected.no")
	public String notice_selected(int id, Model model) {
		// 목적 : 공지글 개행
		// 화면에서 사용할 수 있도록 enter키값을 담아둔다.
		model.addAttribute("crlf", "\r\n");
		
		// 조회수 증가처리
		service.notice_read(id);
		
		// DB에서 해당 공지글을 조회한다.
		// 화면에 출력하도록 Model에 attribute로 담는다.
		model.addAttribute("vo", service.notice_selected(id));
		
		return "notice/selected";
	}
	
	// 공지글 삭제처리 요청
	@RequestMapping("/delete.no")
	public String delte(int id, HttpServletRequest request) {
		// 삭제하기 전에 미리 파일경로정보 빼놓기
		NoticeVO vo= service.notice_selected(id);
		// 비지니스로직
		// 선택한 공지글을 DB에서 삭제한 후
		service.notice_delete(id);
		// 첨부파일이 있었다면 물리적 파일도 삭제
		fileDelete(vo.getFilepath(), request);
		
		// 응답화면연결
		return "redirect:list.no";
	}
	
	// 파일 삭제
	private void fileDelete(String filepath, HttpServletRequest request) {
		if( filepath != null ) {
			//DB:  http://localhost/smart/upload/myinfo/2022/12/20/afdlj_abc.png
			//실제: d://app/smart/upload/myinfo/2022/12/20/afdlj_abc.png 	
			filepath = filepath.replace( common.appURL(request)
										, "d://app" + request.getContextPath() );			
			File file = new File( filepath );
			if( file.exists() ) {
				file.delete();
			}
		}
	}	
	
	// 고객정보수정화면 요청
	@RequestMapping("/modify.no")
	public String modify(int id, Model model) {
		// 비지니스로직
		// 선택한 고객정보를 DB에서 조회해와서
		//NoticeVO vo = service.notice_info(id);
		// 수정화면에 출력할 수 있도록 Model에 attribute에 담는다.
		//model.addAttribute("vo",vo);
		model.addAttribute("vo", service.notice_selected(id));
		
		// 응답화면연결
		return "notice/modify";
	}
	
	// 선택한 공지글 수정저장처리 요청
	@RequestMapping("/update.no")
	public String update(NoticeVO vo, MultipartFile file
									,HttpServletRequest request) {
		// 수정전 공지글 정보를 조회
		NoticeVO before = service.notice_selected(vo.getId());
		
		// 파일을 첨부하는 경우 : 원래 x -> 새로 첨부, 원래o -> 바꿔 첨부
		if(! file.isEmpty()) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.fileUpload("notice", file, request));
			// 수정전 첨부되어 있는 파일이 있으면 물리적 파일도 삭제
			fileDelete(before.getFilepath(), request);
		}else {
			// 파일을 첨부하지 않는 경우
			// 3가지 : 원래o 그대로 두는 경우[원래 없는 경우 포함], 원래 없는 경우, 원래o -> 삭제x
			if(vo.getFilename().isEmpty()) {
				// 원래o -> 삭제x (modify.jsp)
				fileDelete(before.getFilepath(), request);	
			}else {
				// 원래o 그대로 두는 경우[원래 없는 경우 포함]
				vo.setFilename(before.getFilename());
				vo.setFilepath(before.getFilepath());	
			}
		}
		
		// 비지니스로직
		// 선택한 고객정보를 DB에서 변경저장한 후
		service.notice_update(vo);
	
		// 응답화면연결
		// input hidden을 통해 id 값 넘김
		return "redirect:selected.no?id=" + vo.getId();
	}
	
	// 새 공지글 등록 화면 요청
	@RequestMapping("/new.no")
	public String customer() {
		// 응답화면연결
		return "notice/new";
	}
	
	// 새 공지글 저장처리 요청
	@RequestMapping("/insert.no")	  //new.jsp name=file
	public String insert(NoticeVO vo, MultipartFile file, HttpServletRequest request) {
		// 첨부된 파일이 있는 경우
		if(! file.isEmpty()) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.fileUpload("notice", file, request));
		}	
		// 비지니스로직
		// 화면에서 입력한 고객정보를 DB에서 신규저장한다.
		service.notice_insert(vo);
		
		// 응답화면연결
		return "redirect:list.no";
	}
	// 첨부파일 다운로드 요청
	@ResponseBody @RequestMapping(value = "/download.no", produces = "text/html; charset=UTF-8")
	public String download(int id, String url
							, HttpServletRequest request
							, HttpServletResponse response) throws Exception{
		NoticeVO vo = service.notice_selected(id);
		boolean download = common.fileDownload(vo.getFilename(), vo.getFilepath(), request, response);
		if(!download) { // 첨부된 파일이 실제 물리적으로 존재하지 않는 경우
			StringBuffer msg = new StringBuffer("<script>");
			msg.append("alert('다운로드할 파일이 없습니다!'); ");
			// 화면이 원래 화면으로 이동해야한다.
			// 주소창의 데이터(url)를 가져온다. <= info.jsp
			msg.append("location='").append(url).append("'");
			msg.append("</script>");
			return msg.toString();
		}else {
			return null;
		}
	}
}
