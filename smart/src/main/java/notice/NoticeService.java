package notice;

import java.util.List;

/* 역할 : Command interface */
public interface NoticeService {
	// 공지글 신규 저장 - C
	int notice_insert(NoticeVO vo);
	
	// 공지글 목록 조회 - R
	List<NoticeVO> notice_list();
	
	// 페이지처리한 공지글 목록조회
	NoticePageVO notice_list(NoticePageVO page);
	
	// 선택한 공지글 조회 - R
	NoticeVO notice_selected(int id);
	
	// 선택한 공지글 조회수 변경 - R
	int notice_read(int id);
	
	// 공지글 수정 저장 - U
	int notice_update(NoticeVO vo);
	
	// 공지글 삭제 - D
	int notice_delete(int id);
	
}
