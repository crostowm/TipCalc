package docobj;

public class Shift {
	private String shiftType;
	private double payHours;
	private double rate;
	
	public Shift(String shiftType, double payHours, double rate) {
		super();
		this.shiftType = shiftType;
		this.payHours = payHours;
		this.rate = rate;
	}
	
	public String getShiftType() {
		return shiftType;
	}
	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}
	public double getPayHours() {
		return payHours;
	}
	public void setPayHours(double payHours) {
		this.payHours = payHours;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}
