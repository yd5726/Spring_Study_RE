package notice;

import java.util.List;

/* 역할 : Command interface */
public interface NoticeService {
	// 공지글 신규 생성 - C
	int notice_insert(NoticeVO vo);
	
	// 공지글 목록 조회 - R
	List<NoticeVO> notice_list();
	
	// 선택한 공지글 조회 - R
	NoticeVO notice_info(int id);
	
	// 선택한 공지글 조회수 변경 - R
	int notice_read(int id);
	
	// 공지글 수정 저장 - U
	int notice_update(NoticeVO vo);
	
	// 공지글 삭제 - D
	int notice_delete(int id);
	
	// 작성자 목록 조회
	List<NoticeVO> hanul_notice_list();
}
