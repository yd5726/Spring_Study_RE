package customer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/* DAO =유사= repository */
/* 컴포넌트(Component) : 프로그래밍에 있어 재사용이 가능한 각각의 독립된 모듈, @Component*/
/* 컴포넌트(@Component)를 특성에 따라 이름을 줌 : @Repository, @Controller, @Service.. 등등*/

@Repository
public class CustomerDAO implements CustomerService{
	// 방법 1. DI(스프링 의존성 주입)  // 방법 3. Getter&Setter는 권장 안함
	//@Autowired private SqlSession sql;  // SqlSession 객체 담기, 내부 자원 회수 안해도 됨
	// 방법 2. 생성자
	private SqlSession sql;
	public CustomerDAO(SqlSession sql) {
		this.sql = sql;
	}

	@Override
	public void customer_insert(CustomerVO vo) {
		sql.insert("customer.insert", vo);
	}

	@Override
	public List<CustomerVO> customer_list() {
		
		return sql.selectList("customer.list");
	}

	@Override
	public CustomerVO customer_info(int id) {
		
		return sql.selectOne("customer.info", id);
	}

	@Override
	public void customer_update(CustomerVO vo) {
		sql.update("customer.update", vo);
		
	}

	@Override
	public void customer_delete(int id) {
		sql.delete("customer.delete",id);
		
	}

}
