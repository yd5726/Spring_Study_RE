package notice;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeVO {
	private int id, readcnt, no, root, step, indent;
	private String title, content, writer, name, filename, filepath;
	private Date writedate;
}
