package notice;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

/* DB : hanul */
@Getter @Setter
public class NoticeVO {
	private int id, readcnt, no;
	private String title, content, writer, name, filename, filepath;
	private Date writedate;
}