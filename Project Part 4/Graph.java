package Project1;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

public class Graph extends JPanel {

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    // TODO: Add point colors for each type of data point
    private Color pointColor = new Color(255, 0, 255);
    private Color red = new Color(255, 0, 0);
    private Color blue = new Color(0, 0, 255);
    private Color cyan = new Color(0, 255, 255);
    private Color yellow = new Color(255,255,0);

    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private ArrayList<DataPoint> data;

    // TODO: Add a private KNNPredictor variable
    private static KNNPredictor k;
    
    //Set up animation parameters.
    static final int FPS_MIN = 2;
    static final int FPS_MAX = 25;
    static final int FPS_INIT = 5;    //initial frames per second
    int frameNumber = 0;
    int delay;
	protected ArrayList<DataPoint> oddValue;
    static JButton button;
    
    	
	/**
	 * Constructor method
	 */
    public Graph(int K, String fileName) {
        // Generate random data point
        List<DataPoint> data = new ArrayList<>();
		
        // TODO: Remove the above logic where random data is generated
        // TODO: instantiate the KNNPredictor variable
        k = new KNNPredictor(K);
        // TODO: Run readData using input filename to split the data to test and training
		
		k.readData(fileName);
        // TODO: Set this.data as the output of readData
		this.data = k.readData(fileName);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) ((data.get(i).getF1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - data.get(i).getF2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;

            // TODO: Depending on the type of data and how it is tested, change color here.
            // You need to test your data here using the model to obtain the test value 
            // and compare against the true label.
            // Note that depending on how you implemented "test" method, you may need to 
            // modify KNNPredictor to store the output from readData.
            // You can also optimize further to compute accuracy and precision in a single
            // iteration.
            
            //If the label from the test method returns “1” and the label of the DataPoint is “1”,
            //it is a truePositive, so color it blue. (Color(0,0,255))
           
            DataPoint train = (data.get(i));
            if (i % 2 == 0) {
            	g2.setColor(pointColor);
            } else {
            	g2.setColor(blue);
            } {
					if (k.test(train).equals("1") && train.getLabel().equals("1")) {
						g2.setColor(blue);
		            } else if (k.test(train).equals("1") && train.getLabel().equals("0")) {
		            	g2.setColor(cyan);
		            } else if (k.test(train).equals("0") && train.getLabel().equals("1")) {
		            	g2.setColor(yellow);
		            } else if (k.test(train).equals("0") && train.getLabel().equals("0")) {
		            	g2.setColor(red);
		            }
					g2.fillOval(x, y, ovalW, ovalH);
				
			} 
            
        }

    }
    public void Slider() {
        setLayout((LayoutManager) new BoxLayout(this, BoxLayout.PAGE_AXIS));
 
        delay = 1000 / FPS_INIT;
 
        //Create the label.
        JLabel sliderLabel = new JLabel("Choose the majority value", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        //Create the slider.
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                                              FPS_MIN, FPS_MAX, FPS_INIT);
         
 
        framesPerSecond.addChangeListener((ChangeListener) this);
 
        //Turn on labels at major tick marks.
 
        framesPerSecond.setMajorTickSpacing(5);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        framesPerSecond.setFont(font);
 
 
 
        //Put everything together.
        add(sliderLabel);
        add(framesPerSecond);
       
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
 
        
    }

    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF2());
        }
        return minData;
    }


    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF2());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(ArrayList<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }

    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGui(int K, String fileName) {


	    /* Main panel */
        Graph mainPanel = new Graph(K, fileName);
        
        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(700, 600));

        /* creating the frame */
        JFrame frame = new JFrame("CS 112 Lab Part 3");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        
        
        System.out.println("Accuracy:" + (k.getAccuracy(mainPanel.data)));
        System.out.println("Precision:" + ((k.getPrecision(mainPanel.data))));
        	
        JLabel accuracy = new JLabel("Accuracy:" + (k.getAccuracy(mainPanel.data)));
        frame.add(accuracy);
        
        JLabel precision = new JLabel("Precision:" + (k.getPrecision(mainPanel.data)));
        frame.add(precision);
        
        
        JLabel sliderLabel = new JLabel("Choose the majority value", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        mainPanel.add(sliderLabel);
        System.out.println(sliderLabel);
        
      //Create the slider.
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                                              FPS_MIN, FPS_MAX, FPS_INIT);
        
        //Turn on labels at major tick marks.
        
        framesPerSecond.setMajorTickSpacing(5);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setSnapToTicks(true);
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        framesPerSecond.setFont(font);
        
        frame.add(framesPerSecond);
 
        
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(3,3));
        
        
        
        button = new JButton("Run Test");
        button.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		int value = framesPerSecond.getValue();
        		int oddValue = (value*2)/1;
        		KNNPredictor sliderValue = new KNNPredictor(oddValue);
        		sliderValue.test(DataPoint.oddValue);
        		sliderValue.getAccuracy(mainPanel.oddValue);
        		sliderValue.getPrecision(mainPanel.oddValue);
        	} 
        });
         
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.setVisible(true);
        
        
    }
    
    
    
      
    /* The main method runs createAndShowGui*/
    public static void main(String[] args) {
        int K = 7; // A value of K selected    
        String fileName = "titanic.csv"; // TODO: Change this to titanic.csv
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(K, fileName);
            }
        });
    }
}
