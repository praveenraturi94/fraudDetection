package com.renovite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.renovite.dao.DBOperation;
import com.renovite.model.Employee;

@Service
public class HazelService {
	
	 @Autowired
	    DBOperation dbOperations;
	 
	public void PopulateHazelcast() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.addAddress("localhost:5701");
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		 List<Employee> clientList = client.getList("employees");
		 clientList.addAll(dbOperations.findAll());
	}
	
	public List<Employee> FetchAllHazelcastData() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.addAddress("localhost:5701");
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		 List<Employee> clientList = client.getList("employees");
		 clientList.addAll(dbOperations.findAll());
		 return clientList;
	}
	
}
