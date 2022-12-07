package customer;

import java.util.List;

import org.springframework.stereotype.Service;

//import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CutomerServiceImpl implements CustomerService {
	// 1. 자동 주입시키는 방법
	/* @Autowired private CustomerDAO dao; */
	private CustomerDAO dao;
	
	// 2. 생성자로 주입시키는 방법
	public CutomerServiceImpl(CustomerDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void customer_insert(CustomerVO vo) {
		

	}

	@Override
	public List<CustomerVO> customer_list() {
		
		return null;
	}

	@Override
	public CustomerVO customer_info(int id) {
		
		return null;
	}

	@Override
	public void customer_update(CustomerVO vo) {
		

	}

	@Override
	public void customer_delete(int id) {
		

	}

}
