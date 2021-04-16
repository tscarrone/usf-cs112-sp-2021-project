package Project1;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class KNNPredictor extends Predictor{
	private int K = 23;
	private int survived = 0;
	private int notSurvived = 0;
	protected ArrayList<DataPoint> data = new ArrayList<DataPoint>();
	
	public KNNPredictor(int K) {
		this.K = K;
	}

	@Override
	ArrayList<DataPoint> readData(String filename) {
		
		String fileName = "titanic.csv";
		File file = new File(fileName);
		FileReader fis;
		Random rand = new Random();
		try {
			fis = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fis);
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] fileData = line.trim().split(",");
				DataPoint datapoint = new DataPoint(Double.valueOf(fileData[4]), Double.valueOf(fileData[5]));
				datapoint.setLabel(fileData[1]);
				double randNum = rand.nextDouble();
				if(randNum < 0.9) {
					datapoint.setTest(false);
					datapoint.setLabel(fileData[1]);
				}else {
					datapoint.setTest(true);
					datapoint.setLabel(fileData[1]);
				}
				if(fileData[1].equals("0")) {
					survived++;
				}else {
					notSurvived++;
				}
				
				data.add(datapoint);
			}
			br.close();
		} catch (IOException e) {
			System.out.println(data);
			e.printStackTrace();
		}
		System.out.println(survived);
		System.out.println(notSurvived);
		return data;
	}
	
		

	
	

	private double getDistance(DataPoint p1, DataPoint p2) {
		double x1 = p1.getF1();
		double x2 = p2.getF1();
		double y1 = p1.getF2();
		double y2 = p2.getF2();
		double temp1 = x1 - x2;
		double temp2 = y1 - y2;
		temp1 = temp1*temp1;
		temp2 = temp2*temp2;
		return Math.sqrt(temp1+temp2);
		
	} 
	
	
	@Override
	String test(DataPoint test) {
		double[][] arr = new double[(int) ((this.data.size()*0.9) + 1)][2];
		int index = 0;
		if (test.getTest()) {
			for(DataPoint train: this.data) {
				if(train.getTest() == false) {
					arr[index] = new double[] {
						getDistance(test, train), train.getLabel().equals("survived") ?0:1	
					};
					index += 1;
				}
			}
			java.util.Arrays.sort(arr, (a,b)-> Double.compare(a[0],b[0]));
			
		}
		int zero = 0;
		int one = 0;
		for(int i=K; i<arr.length; i++) {
	        for(int j=0; j<arr[i].length; j++) {
	        	if(test.getLabel().equals("survived")) {
	        		zero += 1;
	        	} else {
	        		one += 1;
	        	} 
	        		if(zero > one) {
	        			return String.valueOf(zero);
	        		} else {
	        			return String.valueOf(one);
	        		}
	        }
	    }
		return null;
	}

	@Override
	Double getAccuracy(ArrayList<DataPoint> data) {
		Double truePositive = 0.0;
		Double falsePositive = 0.0;
		Double trueNegative = 0.0;
		Double falseNegative = 0.0;
		
		for (int i = 0; i < data.size(); i++) {
			 Double testLabel = Double.valueOf(test(data.get(i)));
			 DataPoint trainLabel = data.get(i);
			 if(testLabel.equals("1") || trainLabel.equals("1")) {
				 truePositive++;
			 }else if(testLabel.equals("1") || trainLabel.equals("0")) {
				 falsePositive++;
			 }else if(testLabel.equals("0") || trainLabel.equals("1")) {
				 falseNegative++;
			 }else if(testLabel.equals("0") || trainLabel.equals("0")) {
				 trueNegative++;
			 }
			 
		}
		return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
	}

	@Override
	Double getPrecision(ArrayList<DataPoint> data) {
		Double truePositive = 0.0;
		Double falsePositive = 0.0;
		Double trueNegative = 0.0;
		Double falseNegative = 0.0;
		
		for (int i = 0; i < data.size(); i++) {
			 Double testLabel = Double.valueOf(test(data.get(i)));
			 DataPoint trainLabel = data.get(i);
			 if(testLabel.equals("1") || trainLabel.equals("1")) {
				 truePositive++;
			 }else if(testLabel.equals("1") || trainLabel.equals("0")) {
				 falsePositive++;
			 }else if(testLabel.equals("0") || trainLabel.equals("1")) {
				 falseNegative++;
			 }else if(testLabel.equals("0") || trainLabel.equals("0")) {
				 trueNegative++;
			 }
		
	}
		return truePositive / (truePositive + falseNegative);
}
}