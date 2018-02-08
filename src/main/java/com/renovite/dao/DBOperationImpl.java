package com.renovite.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.renovite.model.*;

@Repository
public class DBOperationImpl implements DBOperation {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Find all Employee

	public List<Employee> findAll() {

		List<Employee> result = jdbcTemplate.query("SELECT * FROM employee_info",
				(rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
						rs.getInt("age"), rs.getInt("salary")));

		return result;

	}

	// Add new Employee

	public void addEmployee(Employee employee) {

		jdbcTemplate.update("INSERT INTO employee_info (id,name,age,salary,email) VALUES (?,?,?)", employee.getId(),
				employee.getName(), employee.getAge(), employee.getSalary(), employee.getEmail());

	}

}
