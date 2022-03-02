package io;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import docobj.Employee;
import docobj.Store;

public class PayrollWriter {

	private String payrollPath;
	private PrintWriter writer;
	private ArrayList<Store> group;

	public PayrollWriter(String we, String payoutFilePath, ArrayList<Store> group) {
		payrollPath = payoutFilePath + "\\TipPayrollExport_" + we + ".csv";
		this.group = group;
	}

	public void writePayrollExportCSV() {
		try {
			writer = new PrintWriter(new File(payrollPath));
			for (Store s : group) {
				for (Employee e : s.getEmployees()) {
					if (s.getDollarTipPortion(e.getFullName()) > 0) {
						String companyID = getCompanyIDForStore(s.getStoreNum());
						String earDet = "E";
						String detCode = "ER";
						writer.println(String.format("%s,%d,%s,%s,,%.2f", companyID, e.getPayrollID(), earDet, detCode, s.getDollarTipPortion(e.getFullName())));
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getCompanyIDForStore(int storeNum) {
		switch(storeNum)
		{
		case 1131:
			return "33776";
		case 1300:
			return "33777";
		case 1528:
			return "5554";
		case 1740:
			return "113027";
		case 1741:
			return "113028";
		case 2048:
			return "N5094";
		case 2581:
			return "11276";
		case 3733:
			return "124875";
		}
		return "";
	}

}
