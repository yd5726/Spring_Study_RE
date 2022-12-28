package board;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO implements BoardService {
	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public int board_insert(BoardVO vo) {
		int dml = sql.insert("board.insert", vo);
		// 첨부파일이 있는 경우 board_file 테이블에 첨부파일정보도 저장
		if(vo.getFileList() != null) {
			sql.insert("board.fileInsert", vo);
		}
		return dml; // dml이 1인 경우 - 저장o
	}

	@Override
	public BoardPageVO board_list(BoardPageVO page) {
		page.setTotalList(sql.selectOne("board.total"));
		page.setList(sql.selectList("board.list", page));
		
		return page;
	}

	@Override
	public BoardVO board_info(int id) {
		// 선택한 방명록에 대한 정보 조회
		BoardVO vo = sql.selectOne("board.info", id);
		// 선택한 방명록에 대한 첨부 파일 조회
		vo.setFileList(sql.selectList("board.file", id));;
		
		return vo;
	}

	@Override
	public int board_read(int id) {
		return sql.update("board.read", id);
	}

	@Override
	public int board_update(BoardVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int board_delete(int id) {
		return sql.delete("board.delete", id);
	}

	@Override
	public BoardFileVO board_file_info(int id) {
		return sql.selectOne("board.fineInfo", id);
	}

}
