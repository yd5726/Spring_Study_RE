package notice;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("notice")
public class NoticeServiceImpl implements NoticeService{
	private NoticeDAO dao;
	public NoticeServiceImpl(NoticeDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void notice_insert(NoticeVO vo) {
		dao.notice_insert(vo);
		
	}

	@Override
	public List<NoticeVO> notice_list() {
		return dao.notice_list();
	}

	@Override
	public NoticeVO notice_selected(int id) {
		return dao.notice_selected(id);
	}

	@Override
	public void notice_update(NoticeVO vo) {
		dao.notice_update(vo);
	}

	@Override
	public void notice_delete(int id) {
		dao.notice_delete(id);
		
	}

	@Override
	public List<NoticeVO> hanul_notice_list() {
		return dao.hanul_notice_list();
	}
}
