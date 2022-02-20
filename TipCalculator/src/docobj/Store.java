package docobj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Store
{
  private ArrayList<Employee> employees = new ArrayList<Employee>();
  private int storeNum;
  private double totalTips;

  public Store(int storeNum)
  {
    super();
    this.storeNum = storeNum;
  }

  public ArrayList<Employee> getEmployees()
  {
    return employees;
  }

  public int getStoreNum()
  {
    return storeNum;
  }

  public void addEmployee(Employee e)
  {
    employees.add(e);
  }

  public double getTipPortion(String name)
  {

    return getEmployee(name).getTotalInshopHours() / getTotalInshopHours();
  }

  private Employee getEmployee(String name)
  {
    for (Employee e : employees)
    {
      if (e.getFullName().equals(name))
        return e;
    }
    return null;
  }

  public double getTotalInshopHours()
  {
    double sum = 0;
    for (Employee e : employees)
    {
      sum += e.getTotalInshopHours();
    }
    return sum;
  }

  public double getDollarTipPortion(String name)
  {
    return getTipPortion(name) * totalTips;
  }

  public void setTotalTips(double totalTips)
  {
    this.totalTips = totalTips;
  }

  public void writeToNote(String filePath)
  {
    try
    {
      PrintWriter pw = new PrintWriter(new File(filePath + "\\" + storeNum + "payout.txt"));
      pw.println(getStoreNum() + "   Total Hours: " + getTotalInshopHours() + "   Total Tips$"
          + totalTips);
      for (Employee e : getEmployees())
      {
        pw.println(String.format(
            "%25s total Inshop Hours: %5.2f Tip Portion: %1.6f Actual Amount: %4.2f", e.getFullName(),
            e.getTotalInshopHours(), getTipPortion(e.getFullName()), getDollarTipPortion(e.getFullName())));
      }
      pw.flush();
      pw.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }
}
