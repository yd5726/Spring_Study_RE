package notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO implements NoticeService{
	/*
	private SqlSession sql;
	public NoticeDAO(@Qualifier("hanul") SqlSession sql) {
		this.sql = sql;
	}
	*/
	
	@Qualifier("smart01") @Autowired private SqlSession sql;
	
	@Override	
	public int notice_insert(NoticeVO vo) {
		return sql.insert("notice.insert", vo);
	}

	@Override
	public List<NoticeVO> notice_list() {
		return sql.selectList("notice.list");
	}

	@Override
	public NoticeVO notice_selected(int id) {
		return sql.selectOne("notice.selected", id);
	}

	@Override
	public int notice_update(NoticeVO vo) {
		return sql.update("notice.update", vo);
	}

	@Override
	public int notice_delete(int id) {
		return sql.delete("notice.delete", id);
	}
	
	@Override
	public int notice_read(int id) {
		return sql.update("notice.read", id);
	}

	@Override
	public NoticePageVO notice_list(NoticePageVO page) {
		// 총 공지글 수를 조회
		page.setTotalList(sql.selectOne("notice.count"));
		// 현 페이지에 출력할 10건의 공지글 조회해서 list에 담기
		page.setList(sql.selectList("notice.list", page));
		
		return page;
	}

}
