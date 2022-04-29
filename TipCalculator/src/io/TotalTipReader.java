package io;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import docobj.Store;

public class TotalTipReader {
	ArrayList<Double> totalTipAmounts = new ArrayList<Double>();

	public TotalTipReader(String filePath) throws Exception {
		Scanner scanner = new Scanner(new File(filePath));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			totalTipAmounts.add(Double.parseDouble(line));
		}
		scanner.close();
	}

	public void applyTotalTipsToStores(ArrayList<Store> group) {
		if(totalTipAmounts.size() == group.size())
		{
			for(int ii = 0; ii < group.size(); ii++)
			{
				group.get(ii).setTotalTips(totalTipAmounts.get(ii));
			}
		}
		else
		{
			System.out.println("Read in tips size does not match group size");
			System.out.println(totalTipAmounts.size() + "  " + group.size());
		}
		
	}
}
