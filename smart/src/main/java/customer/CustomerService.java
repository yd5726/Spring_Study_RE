package customer;

import java.util.List;

/* 역할 : Command interface */
public interface CustomerService {
	/* 접근제한자 : public abstract */
	/* 비지니스로직 : CRUD(Create, Read, Update, Delete) */
	// 신규고객정보저장 - C
	void customer_insert(CustomerVO vo);
	
	// 고객목록조회 - R
	List<CustomerVO> customer_list();
	
	// 선택한 고객정보조회 - R
	CustomerVO customer_info(int id); 
	
	// 고객정보수정저장 - U
	void customer_update(CustomerVO vo);
	
	// 고객정보삭제 - D
	void customer_delete(int id);
	
}
