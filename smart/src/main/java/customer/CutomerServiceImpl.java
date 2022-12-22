package customer;

import java.util.List;

import org.springframework.stereotype.Service;

//import org.springframework.beans.factory.annotation.Autowired;

// 이름 명명 => CustomerController.java에 설명 작성해둠
@Service("customer")
public class CutomerServiceImpl implements CustomerService {
	// 1. 자동 주입시키는 방법 : DI(스프링 의존성 주입)
	/* @Autowired private CustomerDAO dao; */
	private CustomerDAO dao;
	
	// 2. 생성자로 주입시키는 방법
	public CutomerServiceImpl(CustomerDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void customer_insert(CustomerVO vo) {
		dao.customer_insert(vo);
	}

	@Override
	public List<CustomerVO> customer_list() {
		
		return dao.customer_list();
	}

	@Override
	public CustomerVO customer_info(int id) {

		return dao.customer_info(id);
	}

	@Override
	public void customer_update(CustomerVO vo) {
		dao.customer_update(vo);
	}

	@Override
	public void customer_delete(int id) {
		dao.customer_delete(id);
	}

}
