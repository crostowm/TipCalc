package io;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import docobj.Employee;
import docobj.Shift;
import docobj.Store;

public class AttendanceReader {

	private Scanner scanner;
	private ArrayList<Employee> employees = new ArrayList<Employee>();
	private Store store;
	private int storeNum;

	public AttendanceReader(File file) {
		storeNum = Integer.parseInt(file.getName().substring(0, 4));
		try {
			scanner = new Scanner(file);

			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] consolidatedList = line.split(",");
				if (consolidatedList.length >= 1) {
					if (consolidatedList[0].equals("Employee #")) {
						String lastName = consolidatedList[12].substring(1);
						String firstName = consolidatedList[13].substring(1, consolidatedList[13].length() - 1).trim();
						int id = -1;
						if (consolidatedList[11].equals("Not Set"))
							System.out.println(firstName + " " + lastName + " id not set");
						else
							id = Integer.parseInt(consolidatedList[11]);
						Employee e = getEmployee(firstName, lastName);
						if (e == null) {
							e = new Employee(firstName, lastName, id);
							employees.add(e);
						}

						double payHour = Double.parseDouble(consolidatedList[16]);
						String jobType = consolidatedList[18];
						double rate = Double.parseDouble(removeDollar(consolidatedList[20]));
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

	private Employee getEmployee(String firstName, String lastName) {
		for (Employee e : employees) {
			if (e.getFullName().equals(lastName + firstName))
				return e;
		}
		return null;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	private String removeDollar(String string) {
		string = string.trim();
		if (string.charAt(0) == '$')
			return (string.substring(1)).trim();
		return string;
	}

	public Store getStore() {
		return store;
	}

}
