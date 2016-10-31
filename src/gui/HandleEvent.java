package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * 监听器
 * @author tianlong
 *
 */
public class HandleEvent extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HandleEvent() {
		// TODO Auto-generated constructor stub
		FlowLayout layout = new FlowLayout();
		setLayout(layout);

		JButton jbtOK = new JButton("OK");
		JButton jbtCancel = new JButton("Cancel");
		add(jbtOK);
		add(jbtCancel);

		OKlistenerClass listener1 = new OKlistenerClass();
		CancelListenerClass listener2 = new CancelListenerClass();
		jbtCancel.addActionListener(listener2);
		jbtOK.addActionListener(listener1);
	}

	public static void main(String[] args) {
		JFrame frame = new HandleEvent();
		frame.setTitle("Handle Event");
		frame.setSize(200, 150);
		frame.setLocation(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class OKlistenerClass implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("OK");
	}

}

class CancelListenerClass implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("cancel");
	}
}
