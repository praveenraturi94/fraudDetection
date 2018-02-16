package com.renovite.service;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.renovite.model.Employee;

@Service
public class RuleService {

	 @Autowired
	    HazelService hazel;
	 
	public int executeRule(String rule) {
	KieSession	ksession =  createKieSession(rule);
	
	List<Employee> employeeList = hazel.FetchAllHazelcastData();
	
	for(Employee emp: employeeList) {
		ksession.insert(emp);
	}
	int failedRules = executeRule(ksession);
		return failedRules;
	}

	public KieSession createKieSession(String rule) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		Collection<org.kie.internal.definition.KnowledgePackage> pkgs;
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		StatefulKnowledgeSession ksession;
		Resource myResource = ResourceFactory.newReaderResource((Reader) new StringReader(rule));
		kbuilder.add(myResource, ResourceType.DRL);

		if (kbuilder.hasErrors()) {
			System.out.println(kbuilder.getErrors().toString());
			throw new RuntimeException("Unable to compile drl\".");
		}

		pkgs = kbuilder.getKnowledgePackages();

		kbase.addKnowledgePackages(pkgs);

		ksession = kbase.newStatefulKnowledgeSession();

		return ksession;
	}
	
public int executeRule(KieSession ksession){
	return     ksession.fireAllRules();
}

}
