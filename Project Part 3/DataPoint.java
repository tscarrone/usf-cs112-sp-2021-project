package Project1;
public class DataPoint {
	private Double f1;
	private Double f2;
	private String label;
	private Boolean isTest;
	
	public DataPoint(Double f, Double g, String label, Boolean test) {
		this.setF1(f);
		this.setF2(g);
		this.setLabel(label);
		this.setTest(test);
	}
	
	public DataPoint(Double f, Double g) {
		this.setF1(f);
		this.setF2(g);
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
		return isTest;
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
		if (label.equals("0") || label.equals("1")) {
			return;
		}
		this.label = label;
	}
	
	public boolean setTest(Boolean test) {
		if((label == ("0"))) {
			return true;
		}
		return this.isTest = test;
	}
	


}
