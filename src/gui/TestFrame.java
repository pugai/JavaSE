package gui;

import javax.swing.JFrame;

/**
 * @author tianlong
 *
 */
public class TestFrame {

	public static void main(String[] args) {
		JFrame frame1 = new JFrame();
		frame1.setTitle("windows 1");
		frame1.setSize(300, 200);
		frame1.setLocation(500, 500);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
	}
	
}
