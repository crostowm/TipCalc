import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import docobj.Employee;
import docobj.Store;
import io.AttendanceReader;
import io.BranchReader;

public class Main
{
  public static ArrayList<Store> group = new ArrayList<Store>();
  private static String attendanceFilePath = "C:\\Users\\crost\\OneDrive\\Documents\\Jimmy Johns\\TipAttendance";
  private static String payoutFilePath = "C:\\Users\\crost\\OneDrive\\Documents\\Jimmy Johns\\TipPayout";

  public static void main(String[] args)
  {
    File dataDirectory = new File(attendanceFilePath);

    // Read Attendance Reports
    for (File f : dataDirectory.listFiles())
    {
      if (!f.getName().equals("Branch.csv"))
      {
        AttendanceReader attendanceData = new AttendanceReader(f);
        group.add(attendanceData.getStore());
      }
    }

    // Read Branch Data
    BranchReader branchReader = new BranchReader(new File(attendanceFilePath + "\\Branch.csv"));
    HashMap<String, Integer> branchData = branchReader.getBranchInfo();
    for(String s: branchData.keySet())
    {
      System.out.println(s);
    }
    System.out.println("_______________");

    // Gather Tip Data
    Scanner keyboard = new Scanner(System.in);
    /*for (Store s : group)
    {
      //System.out.println("Total Tips For " + s.getStoreNum());
      //s.setTotalTips(keyboard.nextDouble());
      //s.setTotalTips(120);
    }*/
    group.get(0).setTotalTips(62.95);
    group.get(1).setTotalTips(39.72);
    group.get(2).setTotalTips(22.78);
    group.get(3).setTotalTips(45.57);
    group.get(4).setTotalTips(16.46);
    group.get(5).setTotalTips(43);
    group.get(6).setTotalTips(56.85);
    group.get(7).setTotalTips(19.38);

    printStores();
    writeStores();

    BranchWriter branchWriter = new BranchWriter("2_15", payoutFilePath, group, branchData);
    branchWriter.writeBulkDisbursementCSV();
    
    keyboard.close();
  }

  private static void printStores()
  {
    for (Store s : group)
    {
      System.out.println(s.getStoreNum());
      for (Employee e : s.getEmployees())
      {
        System.out.println(
            String.format("%25s total Inshop Hours: %5.2f Tip Portion: %1.6f Actual Amount: %4.2f",
                e.getFullName(), e.getTotalInshopHours(), s.getTipPortion(e.getFullName()),
                s.getDollarTipPortion(e.getFullName())));
      }
      System.out.println("Total Hours: " + s.getTotalInshopHours());
      System.out.println();
    }
  }

  private static void writeStores()
  {
    for (Store s : group)
    {
      s.writeToNote(payoutFilePath);
    }
  }
}
