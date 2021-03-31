package Project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		DummyPredictor predictor = new DummyPredictor();
		String token = "";
		ArrayList<DataPoint> data = new ArrayList<DataPoint>();
		try {
			Scanner scanner = new Scanner(new File("text.txt"));
			while (scanner.hasNextLine()) {
				token = (scanner.nextLine());
				System.out.println(token);
			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found");
		}
		
		//DataPoint testData = new DataPoint(9.0);
		
		//predictor.readData(data);
		//System.out.println(predictor.test(testData)); // Should print "Blue"

	}
	}



