package com.renovite.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.renovite.dao.DBOperation;
import com.renovite.model.Employee;

@Service
public class HazelService {

	@Autowired
	DBOperation dbOperations;

	public void PopulateHazelcast() {
		HazelcastInstance client = getHazelCastClientInstance();
		List<Employee> clientList = client.getList("employees");
		clientList.addAll(dbOperations.findAll());
	}

	public List<Employee> FetchAllHazelcastData() {
		HazelcastInstance client = getHazelCastClientInstance();
		List<Employee> clientList = client.getList("employees");
		return clientList;
	}

	public void insertRule() {
		HazelcastInstance client = getHazelCastClientInstance();
		IMap<Object, Object> clientMap = client.getMap("rule");
		List<String> rules = readAllDRLFiles();
		for (String rule : rules) {
			clientMap.put("NameRule", rule);
		}
	}

	public String fetchRule() {
		HazelcastInstance client = getHazelCastClientInstance();
		IMap<Object, Object> clientMap = client.getMap("rule");
		return clientMap.get("NameRule").toString();
	}

	public HazelcastInstance getHazelCastClientInstance() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.addAddress("localhost:5701");
		return HazelcastClient.newHazelcastClient(clientConfig);
	}

	public List<String> readAllDRLFiles() {

		File folder = new File("src/main/resources/rules");
		File[] listOfFiles = folder.listFiles();
		List<String> rules = new ArrayList<String>();
		try {

			for (int i = 0; i < listOfFiles.length; i++) {
				File file = listOfFiles[i];
				BufferedReader br = new BufferedReader(new FileReader(file));

				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				String rule = sb.toString();

				br.close();
				rules.add(rule);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rules;
	}

}
