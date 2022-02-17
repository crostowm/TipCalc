package docobj;

import java.util.ArrayList;

public class Employee {
	private String name;
	private int empID;
	private ArrayList<Shift> shifts = new ArrayList<Shift>();
	public Employee(String name, int empID) {
		super();
		this.name = name;
		this.empID = empID;
	}
	
	public void addShift(Shift s)
	{
		shifts.add(s);
	}

	
	
	
	public String getName() {
		return name;
	}

	public int getEmpID() {
		return empID;
	}

	public ArrayList<Shift> getShifts() {
		return shifts;
	}

	public double getTotalInshopHours() {
		double sum = 0;
		for(Shift s: shifts)
		{
			if(s.getShiftType().equals("In Shop"))
			{
				sum += s.getPayHours();
			}
		}
		return sum;
	}

}
