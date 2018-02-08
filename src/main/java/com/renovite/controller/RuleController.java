package com.renovite.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.renovite.dao.DBOperation;
import com.renovite.model.*;
import com.renovite.service.HazelService;

@RestController
public class RuleController {


	    @Autowired
	    DBOperation dbOperations;
	    
	    @Autowired
	    HazelService hazel;
	    
	@RequestMapping("/")
	public String welcome() {
		return "Welcome to Spring Boot Tutorials";
	}

	@RequestMapping("/populateHazel")
	public String populateHazel() {
		hazel.PopulateHazelcast();
		return "Hazel data successfully populated";
	}
	
	@RequestMapping("/fetchAllHazelData")
	public List<Employee> fetchAllHazelData() {
		return hazel.FetchAllHazelcastData();
	}

	@RequestMapping("/executeRules")
	public String executeRules() {
		
		return "";
	}
	
	@RequestMapping("/fetchAll")
	public List<Employee> FetchAllRecords() {
		return dbOperations.findAll();
	}
}
