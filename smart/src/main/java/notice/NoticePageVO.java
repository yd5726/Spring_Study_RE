package notice;

import java.util.List;
import common.PageVO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticePageVO extends PageVO{
	// 공지글 10건의 데이터를 담을 필드를 선언한다.
	private List<NoticeVO> list;
}
