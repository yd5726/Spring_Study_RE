package hr;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class HrDAO implements HrService {
	//@Autowired private SqlSession sql;
	private SqlSession sql;
	public HrDAO(@Qualifier("hr") SqlSession sql) {
		this.sql = sql;
	}
	
	@Override
	public void employee_insert(EmployeeVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EmployeeVO> employee_list() {
		return sql.selectList("hr.list");
	}

	@Override
	public EmployeeVO employee_info(int employee_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void employee_update(EmployeeVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void employee_delete(int employee_id) {
		// TODO Auto-generated method stub

	}

}
