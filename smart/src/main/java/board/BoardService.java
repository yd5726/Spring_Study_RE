package board;

import java.util.List;

public interface BoardService {
	// CRUD
	// 방명록 글 신규 저장
	int board_insert(BoardVO vo);
	// 방명록 글 목록 조회(페이징 처리)
	BoardPageVO board_list(BoardPageVO page);
	// 방명록 선택한 글 조회
	BoardVO board_info(int id);
	// 방명록에 첨부된 각 파일 정보 조회
	BoardFileVO board_file_info(int id);
	// 방명록 글 조회수 처리
	int board_read(int id);
	// 방명록 선택한 글 변경 저장
	int board_update(BoardVO vo);
	// 방명록 선택한 글 삭제
	int board_delete(int id);
	// 첨부파일목록 삭제
	int board_file_delete(String removed);
	
	// 댓글 신규 저장 - C
	int board_comment_insert(BoardCommentVO vo);
	// 댓글 목록 조회 - R
	List<BoardCommentVO> board_comment_list(int board_id); //board_id
	// 댓글 변경 저장 - U
	int board_comment_update(BoardCommentVO vo);
	// 댓글 삭제 - D
	int board_comment_delete(int id);	//comment_id
}
