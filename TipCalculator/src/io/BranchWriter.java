package io;

import java.io.File;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import docobj.Employee;
import docobj.Store;

public class BranchWriter {
	private String we, branchPath;
	private PrintWriter writer;
	private ArrayList<Store> group;
	private HashMap<String, Integer> branchData;

	/**
	 * @param we
	 * @param payoutFilePath
	 * @param group
	 * @param branchData
	 */
	public BranchWriter(String we, String payoutFilePath, ArrayList<Store> group, HashMap<String, Integer> branchData) {
		this.we = we;
		branchPath = payoutFilePath + "\\BranchTipDisbursement_" + we + ".csv";
		this.group = group;
		this.branchData = branchData;
	}

	public void writeBulkDisbursementCSV() {
		try {
			writer = new PrintWriter(new File(branchPath));
			writer.println("ID,First Name,Last Name,Type,Amount,Transaction ID,Location (optional),Disbursement Date (YYYY-MM-DD) (optional),Description (optional),Shift Timestamp (optional)");
			for (Store s : group) {
				for (Employee e : s.getEmployees()) {
					try {
						if (s.getDollarTipPortion(e.getFullName()) > 0) {
							int branchID = branchData.get(e.getFullName());
							String type = "tips";
							GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
							String transID = we + s.getStoreNum() + branchID;
							String date = c.toZonedDateTime().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
							writer.println(String.format("%d,%s,%s,%s,%.2f,%s,%s,%s,Tip Portion %s", branchID,
									e.getFirstName(), e.getLastName(), type, s.getDollarTipPortion(e.getFullName()),
									transID, "Jimmy Johns #" + s.getStoreNum(), date, we));
						}
					} catch (Exception ex) {
						System.out.println(s.getStoreNum() + " " + e.getFullName() + " " + e.getPayrollID());
						ex.printStackTrace();
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
