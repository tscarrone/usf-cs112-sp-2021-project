package Project1;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SecondDriver {

	public static void main(String[] args) {
		KNNPredictor predictor = new KNNPredictor(23);
		ArrayList<DataPoint> data = new ArrayList<DataPoint>();
		
		
		String fileName = "titanic.csv";
		
		predictor.readData(fileName);
		System.out.println(predictor.getAccuracy(data));
		System.out.println(predictor.getPrecision(data));
		SwingUtilities.invokeLater(
		          new Runnable() { public void run() { initAndShowGUI(); } }
		        );
	}
		
	private static void initAndShowGUI() {
		JFrame myFrame = new JFrame("Titanic Prediction");
			
			
		GridLayout grid = new GridLayout(2,2);
			
		Container contentPane = myFrame.getContentPane();

		contentPane.setLayout(grid);
		
			
		contentPane.add(new JButton());
		contentPane.add(new JButton());
		contentPane.add(new JButton());


			
		myFrame.pack();
		myFrame.setVisible(true);
		    
	}

}
