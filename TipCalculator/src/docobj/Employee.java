package docobj;

import java.util.ArrayList;

public class Employee {
	private String firstName, lastName, fullName;
	private int payrollID;
	private ArrayList<Shift> shifts = new ArrayList<Shift>();
	
	/**
   * @param firstName
   * @param lastName
   * @param empID
   * @param shifts
   */
  public Employee(String firstName, String lastName, int empID)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    fullName = lastName + firstName;
    this.payrollID = empID;
  }

  public void addShift(Shift s)
	{
		shifts.add(s);
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

  /**
   * @return the firstName
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * @return the fullName
   */
  public String getFullName()
  {
    return fullName;
  }

  /**
   * @return the empID
   */
  public int getPayrollID()
  {
    return payrollID;
  }

  /**
   * @return the shifts
   */
  public ArrayList<Shift> getShifts()
  {
    return shifts;
  }

	
}
