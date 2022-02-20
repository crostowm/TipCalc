import java.io.File;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import docobj.Employee;
import docobj.Store;

public class BranchWriter
{
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
  public BranchWriter(String we, String payoutFilePath, ArrayList<Store> group,
      HashMap<String, Integer> branchData)
  {
    this.we = we;
    branchPath = payoutFilePath + "\\BranchTipDisbursement" + we + ".csv";
    this.group = group;
    this.branchData = branchData;
  }

  public void writeBulkDisbursementCSV()
  {
    try
    {
      writer = new PrintWriter(new File(branchPath));
      for (Store s : group)
      {
        for (Employee e : s.getEmployees())
        {
          if (s.getDollarTipPortion(e.getFullName()) > 0)
          {
            int branchID = branchData.get(e.getFullName());
            String type = "tips";
            int transID = 1;
            GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
            String date = c.toZonedDateTime().format(DateTimeFormatter.ofPattern("YYYY-mm-dd"));
            writer.println(String.format("%d,%s,%s,%s,%.2f,%d,%d,%s,Tip Portion W/E %s", branchID,
                e.getFirstName(), e.getLastName(), type, s.getDollarTipPortion(e.getFullName()),
                transID, s.getStoreNum(), date, we));
          }
        }
      }
      writer.flush();
      writer.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

}
