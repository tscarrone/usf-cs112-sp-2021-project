package Project1;
public class DataPoint {
	private Double f1;
	private Double f2;
	private String label;
	private Boolean isTest;
	
	public DataPoint(Double f, Double g, String label, Boolean test) {
		this.f1 = f;
		this.f1 = g;
		setLabel(label);
		setTest(test);
	}
	
	public DataPoint(Double f, Double g) {
		this.f1 = f;
		this.f2 = g;
		this.label = null;
	}
	
	public Double getF1() {
		return this.f1;
	}
	
	public Double getF2() {
		return this.f2;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public Boolean getTest() {
		return this.isTest;
	}
	
	public void setF1(Double f) {
		
		if (f < 0) {
			return;
		}
		this.f1 = f;
	}
	
	public void setF2(Double g) {
		
		if (g < 0) {
			return;
		}
		this.f2 = g;
	}
	
	public void setLabel(String label) {
		if (!(label.equals("Good")|| label.equals("Bad"))) {
			return;
		}
		this.label = label;
	}
	
	public boolean setTest(Boolean test) {
		if((label.equals("Good"))) {
			this.isTest = test;
			return true;
		}
		return false;
	}

}
