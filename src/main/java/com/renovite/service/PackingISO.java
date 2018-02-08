package com.renovite.service;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.renovite.model.*;

public class PackingISO {
	public int mappingISO() {
		int ruleFailed = 0;
		// String data = "16010200 D4000000 00000000 00000000 00000000 00000100 722464D1
		// 28E09816 10403456 78901234 56010000 00000000 40000129 10554000 00330309
		// 60110840 90100006 C4F0F0F0 F0F0F0F0 F00B0123 45678901 20403456 78901234
		// 56D03091 23123450 00F8F0F2 F9F1F0F0 F0F0F0F3 F3E3C5D9 D4C9C4F0 F1C3C1D9
		// C440C1C3 C3C5D7E3 D6D94040 C1C3D8E4 C9D9C5D9 40D5C1D4 C5404040 40404040
		// 40404040 40C3C9E3 E840D5C1 D4C54040 4040E4E2 084095E5 47F8774A 24D42001
		// 01010000 00000122 09800000 00000000 00E80580 00000002";
		// String data =
		// "0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR021ABCDEFGHIJ
		// 1234567890";
		// String data =
		// "16010200D400000000000000000000000000000000000100722464D128E09816104034567890123456010000000000004000012910554000003303096011084090100006C4F0F0F0F0F0F0F0F00B012345678901204034567890123456D030912312345000F8F0F2F9F1F0F0F0F0F0F3F3E3C5D9D4C9C4F0F1C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E2084095E547F8774A24D420010101000000000122098000000000000000E8058000000002";
		String data = "16010200D40000000000000000000000 0000000000000100722464D128E09816 10403456789012345601000000000000 40000129105540000033030960110840 90100006C4F0F0F0F0F0F0F0F00B0123 45678901204034567890123456D03091 2312345000F8F0F2F9F1F0F0F0F0F0F3 F3E3C5D9D4C9C4F0F1C3C1D9C440C1C3 C3C5D7E3D6D94040C1C3D8E4C9D9C5D9 40D5C1D4C54040404040404040404040 40C3C9E3E840D5C1D4C540404040E4E2 084095E547F8774A24D4200101010000 00000122098000000000000000E80580 00000002";
		new PackingISO().parsingData(data);

		try {
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("drool-rule");

			Employee emp = new Employee(1, "praveensssssssssssssssssssss", "Praveenraturi3@yahoo.com", 10000, 11);
			Address address = new Address();
			address.setHno(48);
			address.setStreet("sailok");
			address.setCity("dehradunfffffffffffdddddfffff");
			address.setState("uttarakhand");
			address.setPincode(248001);

			kSession.insert(emp);
			kSession.insert(address);
			ruleFailed = kSession.fireAllRules();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruleFailed;
	}

	public void parsingData(String data) {
		try {

			System.out.println("DATAgfhgfgh : " + data);

			GenericPackager packager = new GenericPackager("basic.xml");
			ISOMsg isoMsg = new ISOMsg();
			isoMsg.setPackager(packager);
			isoMsg.unpack(data.getBytes());
			try {
				// System.out.println(" MTI : " + isoMsg.getMTI());
				// System.out.println(isoMsg.getValue());
				for (int i = 1; i <= isoMsg.getMaxField(); i++) {
					if (isoMsg.hasField(i)) {
						System.out.println("    Field-" + i + " : " + isoMsg.getString(i));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("--------------------");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

}
