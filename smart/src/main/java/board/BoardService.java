package board;

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
}
