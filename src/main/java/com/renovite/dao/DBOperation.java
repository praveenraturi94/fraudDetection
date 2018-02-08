package com.renovite.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.renovite.model.Employee;

@Transactional
public interface DBOperation {

	List<Employee> findAll();

	void addEmployee(Employee employee);
}
