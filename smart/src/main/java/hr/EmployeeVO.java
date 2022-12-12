package hr;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeVO {
	private int employee_id, department_id, salary;
	private String last_name, first_name, name, department_name
				, job_id, job_title, phone_number, email;
	private Date hire_date;
}