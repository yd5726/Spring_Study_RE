package board;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardFileVO {
	private int id, board_id;
	private String filename, filepath;
	private List<BoardFileVO> fileList;
}
