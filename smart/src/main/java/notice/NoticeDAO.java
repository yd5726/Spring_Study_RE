package notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO implements NoticeService{
	private SqlSession sql;
	public NoticeDAO(@Qualifier("hanul") SqlSession sql) {
		this.sql = sql;
	}
	@Override
	public void notice_insert(NoticeVO vo) {
		sql.insert("notice.insert", vo);
		
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
	public void notice_update(NoticeVO vo) {
		sql.update("notice.update", vo);
		
	}

	@Override
	public void notice_delete(int id) {
		sql.delete("notice.delete", id);
	}
	@Override
	public List<NoticeVO> hanul_notice_list() {
		return sql.selectList("notice.writer_list");
	}

}
