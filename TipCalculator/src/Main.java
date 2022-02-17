import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import docobj.Employee;
import docobj.Store;
import io.SalesSummaryReader;

public class Main {
	public static ArrayList<Store> group = new ArrayList<Store>();

	public static void main(String[] args) {
		final File dataDirectory = new File("C:\\Users\\crost\\OneDrive\\Documents\\Jimmy Johns\\TipAttendance");
		for (File f : dataDirectory.listFiles()) {

			SalesSummaryReader ssr = new SalesSummaryReader(f);
			group.add(ssr.getStore());
		}
		ArrayList<String> totalTips = new ArrayList<String>();
		Scanner keyboard = new Scanner(System.in);
		for (Store s : group) {
			//System.out.println("Total Tips For " + s.getStoreNum());
			//s.setTotalTips(keyboard.nextDouble());
			s.setTotalTips(240);
		}
		for (Store s : group) {
			System.out.println(s.getStoreNum());
			for (Employee e : s.getEmployees()) {
				System.out.println(String.format("%25s total Inshop Hours: %5.2f Tip Portion: %1.6f Actual Amount: %4.2f", e.getName(),
						e.getTotalInshopHours(), s.getTipPortion(e.getName()), s.getDollarTipPortion(e.getName())));
			}
			System.out.println("Total Hours: " + s.getTotalInshopHours());
			System.out.println();
			s.writeToNote();
		}
		keyboard.close();
	}
}
