package hr;

import java.util.List;

public interface HrService {
	// 신규사원등록
	void employee_insert(EmployeeVO vo);
	List<EmployeeVO> employee_list();
	// 사원목록
	EmployeeVO employee_info(int employee_id);
	// 사원정보수정
	void employee_update(EmployeeVO vo);
	// 사원정보삭제
	void employee_delete(int employee_id);
	
	// 회사 전체부서 목록
	List<DepartmentVO> hr_department_list();
	// 회사 전체업무 목록
	List<JobVO> hr_job_list();
}