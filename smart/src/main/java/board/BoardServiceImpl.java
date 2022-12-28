package board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired private BoardDAO dao;

	@Override
	public int board_insert(BoardVO vo) {
		return dao.board_insert(vo);
	}

	@Override
	public BoardPageVO board_list(BoardPageVO page) {
		return dao.board_list(page);
	}

	@Override
	public BoardVO board_info(int id) {
		return dao.board_info(id);
	}

	@Override
	public int board_read(int id) {
		return dao.board_read(id);
	}

	@Override
	public int board_update(BoardVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int board_delete(int id) {
		return dao.board_delete(id);
	}

	@Override
	public BoardFileVO board_file_info(int id) {
		return dao.board_file_info(id);
	}

}
