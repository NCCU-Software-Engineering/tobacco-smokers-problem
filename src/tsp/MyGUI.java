package tsp;

import javax.swing.JFrame;

public class MyGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static void start() {
		JFrame frame = new JFrame("Tobacco Smokers Problem");
	    frame.setSize(1200, 800);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JButton button = new JButton("Click me");
	}
}
