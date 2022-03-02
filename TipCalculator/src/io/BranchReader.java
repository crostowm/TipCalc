package io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BranchReader
{
  private Scanner scanner;
  private HashMap<String, Integer> branchInfo;

  public BranchReader(File file)
  {
    branchInfo = new HashMap<String, Integer>();
    try
    {
      scanner = new Scanner(file);

      while (scanner.hasNext())
      {
        String line = scanner.nextLine();

        ArrayList<String> consolidatedList = splitButConsolidateSpecial(line.split(","), '"');
        if (consolidatedList.size() > 3)
        {
          try
          {
            branchInfo.put(consolidatedList.get(3), Integer.parseInt(consolidatedList.get(2)));
          }
          catch (NumberFormatException nfe)
          {
            System.out.println("Could Not Parse Branch Data For:\n" + consolidatedList.get(3) + " "
                + consolidatedList.get(2));
          }
        }

      }
      scanner.close();
    }
    catch (

    Exception e)
    {
      e.printStackTrace();
    }
  }

  public HashMap<String, Integer> getBranchInfo()
  {
    return branchInfo;
  }

  private ArrayList<String> splitButConsolidateSpecial(String[] line, char special)
  {
    ArrayList<String> tokens = new ArrayList<String>();
    String temp = "";
    for (int ii = 0; ii < line.length; ii++)
    {
      temp = line[ii].trim();
      if (temp.length() > 0)
      {
        // Found beginning quote
        if (temp.charAt(0) == special)
        {
          // Iterate until end quote
          while (temp.charAt(temp.length() - 1) != special)
          {
            if ((ii++) < line.length)
            {
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
}
