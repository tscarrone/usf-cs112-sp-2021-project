package Project1;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

public class DummyPredictor extends Predictor{
	
	private Double trainAvg;
	private Double testAvg;

	@Override
	ArrayList<DataPoint> readData(String filename) {
		ArrayList<DataPoint> data = new ArrayList<DataPoint>();
		Double trainSum = 0.0;
		Double testSum = 0.0;
		int numTrain = 0;
		int numTest = 0;
		for (int i = 0; i < data.size(); i++) {
			DataPoint d = data.get(i);
			Double f = d.getF1();
			Double g = d.getF2();
			String label = d.getLabel();
			if(label.equals("Good")) {
				trainSum += f+g;
				numTrain += 1;
			} else if (label.equals("Bad")) {
				testSum += f+g;
				numTest += 1;
			}
		}
		this.trainAvg = trainSum/numTrain;
		this.testAvg = testSum/numTest;
		return null;
	}

	@Override
	String test(DataPoint data) {
		double trainDist = Math.abs(data.getF1() - this.trainAvg);
		double testDist = Math.abs(data.getF1() - this.testAvg);
		double trainDist2 = Math.abs(data.getF2() - this.trainAvg);
		double testDist2 = Math.abs(data.getF2() - this.testAvg);
		
		if((trainDist < testDist) || ( trainDist2 < testDist2)) {
			return "Good";
		} else {
			return "Bad";
		}
	}

	@Override
	Double getAccuracy(ArrayList<DataPoint> data) {
		
		return null;
	}

	@Override
	Double getPrecision(ArrayList<DataPoint> data) {
		
		return null;
	}

}
