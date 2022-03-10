import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import docobj.Employee;
import docobj.Store;
import io.AttendanceReader;
import io.BranchReader;
import io.BranchWriter;
import io.PayrollWriter;
import io.TotalTipReader;

public class Main
{
  public static ArrayList<Store> group = new ArrayList<Store>();
  private static String baseFilePath = "C:\\Users\\crost\\OneDrive\\Documents\\Jimmy Johns\\TipResources";
  private static String attendanceFilePath = baseFilePath + "\\TipAttendance";
  private static String payoutFilePath = baseFilePath + "\\TipPayout";

  public static void main(String[] args)
  {
    // Gather Tip Data
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Payout Period (mm-dd-yyyy_mm-dd-yyyy");
    // String payPeriod = keyboard.nextLine();
    // String payPeriod = "02-14-2022_02-15-2022";
    String payPeriod = "02-16-2022_03-01-2022";

    // Read Attendance Files
    File TipAttendanceFolder = new File(attendanceFilePath + payPeriod);

    // Read Attendance Reports
    for (File f : TipAttendanceFolder.listFiles())
    {
      AttendanceReader attendanceData = new AttendanceReader(f);
      group.add(attendanceData.getStore());
    }
    // Read Total Tips from txt or have them enter via keyboard
    try
    {
      TotalTipReader ttr = new TotalTipReader(baseFilePath + "\\TotalTips" + payPeriod + ".txt");
      ttr.applyTotalTipsToStores(group);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      for (Store s : group)
      {
        System.out.println("Total Tips For " + s.getStoreNum());
        s.setTotalTips(keyboard.nextDouble());
      }
    }

    // Read Branch Data
    BranchReader branchReader = new BranchReader(new File(baseFilePath + "\\Branch.csv"));
    HashMap<String, Integer> branchData = branchReader.getBranchInfo();
    for (String s : branchData.keySet())
    {
      System.out.println(s);
    }
    System.out.println("_______________");

    printStores();
    writeStores(payPeriod);

    BranchWriter branchWriter = new BranchWriter(payPeriod, payoutFilePath, group, branchData);
    branchWriter.writeBulkDisbursementCSV();

    PayrollWriter payrollWriter = new PayrollWriter(payPeriod, payoutFilePath, group);
    payrollWriter.writePayrollExportCSV();

    keyboard.close();
  }

  private static void printStores()
  {
    String warnings = "";
    double sum = 0;
    for (Store s : group)
    {
      System.out.println(s.getStoreNum() + " $" + s.getTotalTips() + " Increase per hour: $"
          + (s.getTotalTips() / s.getTotalInshopHours()));
      for (Employee e : s.getEmployees())
      {
        System.out.println(
            String.format("%25s total Inshop Hours: %5.2f Tip Portion: %1.6f Actual Amount: %4.2f",
                e.getFullName(), e.getTotalInshopHours(), s.getTipPortion(e.getFullName()),
                s.getDollarTipPortion(e.getFullName())));

        if (s.getDollarTipPortion(e.getFullName()) > 100)
        {
          warnings += s.getStoreNum() + ": $" + s.getTotalTips() + "|" + s.getTotalInshopHours() + "hrs"
              + String.format(
                  "%25s total Inshop Hours: %5.2f Tip Portion: %1.6f Actual Amount: %4.2f",
                  e.getFullName(), e.getTotalInshopHours(), s.getTipPortion(e.getFullName()),
                  s.getDollarTipPortion(e.getFullName())) + "\n";
        }
      }
      System.out.println("Total Hours: " + s.getTotalInshopHours());
      System.out.println();
      sum += s.getTotalTips();
    }
    System.out.println("Average hourly increase for all stores: $" + getAverageHourlyIncrease()
        + " Total: $" + sum);
    
    System.out.println("\nWarnings");
    System.out.println(warnings);
  }

  private static Double getAverageHourlyIncrease()
  {
    double sumTotalTips = 0;
    double sumInshopHours = 0;
    for (Store s : group)
    {
      sumTotalTips += s.getTotalTips();
      sumInshopHours += s.getTotalInshopHours();
    }
    return sumTotalTips / sumInshopHours;
  }

  private static void writeStores(String weekending)
  {
    for (Store s : group)
    {
      s.writeToNote(payoutFilePath, weekending);
    }
  }
}
