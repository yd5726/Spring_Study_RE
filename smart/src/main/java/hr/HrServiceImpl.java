package hr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("hr")
public class HrServiceImpl implements HrService {
	//@Autowired private HrDAO dao;
	private HrDAO dao;
	public HrServiceImpl(HrDAO hr) {
		this.dao = hr;
	}
	
	@Override
	public void employee_insert(EmployeeVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EmployeeVO> employee_list() {
		return dao.employee_list();
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
