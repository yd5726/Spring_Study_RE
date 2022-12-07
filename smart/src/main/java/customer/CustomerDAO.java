package customer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/* DAO =유사= repository */
/* 컴포넌트(Component) : 프로그래밍에 있어 재사용이 가능한 각각의 독립된 모듈*/

@Repository
public class CustomerDAO implements CustomerService{
	@Autowired private SqlSession sql;  // SqlSession 객체 담기, 내부 자원 회수 안해도 됨

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
