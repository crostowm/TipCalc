package io;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import docobj.Employee;
import docobj.Shift;
import docobj.Store;

public class SalesSummaryReader {

	private Scanner scanner;
	private ArrayList<Employee> employees = new ArrayList<Employee>();
	private Store store;
	private int storeNum;

	public SalesSummaryReader(File file) {
		storeNum = Integer.parseInt(file.getName().substring(0, 4));
		try {
			scanner = new Scanner(file);

			while (scanner.hasNext()) {
				String line = scanner.nextLine();

				ArrayList<String> consolidatedList = splitButConsolidateSpecial(line.split(","), '"');
				if (consolidatedList.size() >= 1) {
					if (consolidatedList.get(0).equals("Employee #")) {
						int id = Integer.parseInt(consolidatedList.get(11));
						String name = consolidatedList.get(12);
						Employee e = getEmployee(name);
						if (e == null) {
							e = new Employee(name, id);
							employees.add(e);
						}

						double payHour = Double.parseDouble(consolidatedList.get(15));
						String jobType = consolidatedList.get(16);
						double rate = Double.parseDouble(removeDollar(consolidatedList.get(19)));
						System.out.println(String.format("%d %s %s %f", id, name, jobType, payHour));
						e.addShift(new Shift(jobType, payHour, rate));
					}
				}

			}
			store = new Store(storeNum);
			for (Employee e : employees) {
				store.addEmployee(e);
			}
			scanner.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	private Employee getEmployee(String name) {
		for (Employee e : employees) {
			if (e.getName().equals(name))
				return e;
		}
		return null;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	private double convertToDouble(String string) {
		return Double.parseDouble(removePerc(removeDollar(removeQuotes(string))));
	}

	private String removePerc(String string) {
		string = string.trim();
		if (string.charAt(string.length() - 1) == '%')
			return (string.substring(0, string.length() - 1)).trim();
		return string;
	}

	private String removeDollar(String string) {
		string = string.trim();
		if (string.charAt(0) == '$')
			return (string.substring(1)).trim();
		return string;
	}

	private String removeQuotes(String string) {
		string = string.trim();
		if (string.charAt(0) == '"')
			return (string.substring(1, string.length() - 1)).trim();
		return string;
	}

	private ArrayList<String> splitButConsolidateSpecial(String[] line, char special) {
		ArrayList<String> tokens = new ArrayList<String>();
		String temp = "";
		for (int ii = 0; ii < line.length; ii++) {
			temp = line[ii].trim();
			if (temp.length() > 0) {
				// Found beginning quote
				if (temp.charAt(0) == special) {
					// Iterate until end quote
					while (temp.charAt(temp.length() - 1) != special) {
						if ((ii++) < line.length) {
							// Add all tokens til end
							temp += line[ii].trim();
						}
					}
				}
				tokens.add(temp);
			}
		}
		return tokens;
	}

	public Store getStore() {
		return store;
	}
}
