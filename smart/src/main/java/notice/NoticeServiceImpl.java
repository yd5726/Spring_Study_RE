package notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("notice")
public class NoticeServiceImpl implements NoticeService{
	@Autowired private NoticeDAO dao;
	public NoticeServiceImpl(NoticeDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public int notice_insert(NoticeVO vo) {
		dao.notice_insert(vo);
		return 0;
	}

	@Override
	public List<NoticeVO> notice_list() {
		return dao.notice_list();
	}

	@Override
	public NoticeVO notice_info(int id) {
		return dao.notice_info(id);
	}

	@Override
	public int notice_update(NoticeVO vo) {
		dao.notice_update(vo);
		return 0;
	}

	@Override
	public int notice_delete(int id) {
		dao.notice_delete(id);
		return 0;
		
	}

	@Override
	public List<NoticeVO> hanul_notice_list() {
		return dao.hanul_notice_list();
	}

	@Override
	public int notice_read(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
}
