package utils;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class ErrorDialog extends JDialog{
	private static final long serialVersionUID = -1587458309413529301L;

	public ErrorDialog(Throwable cause) {
		super();
		this.setTitle("An Error occured");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setMinimumSize(new Dimension(190, 70));
		this.getContentPane().add(Box.createHorizontalStrut(40));
		this.getContentPane().add(Box.createVerticalStrut(25));
		this.getContentPane().add(new JLabel(cause.getMessage(), UIManager.getIcon("OptionPane.errorIcon"), 0));
		this.getContentPane().add(Box.createVerticalStrut(25));
		JButton dismiss = new JButton("Ok");
		dismiss.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				kill();
			}
		});
		this.getContentPane().add(Box.createHorizontalStrut(40));
		this.getContentPane().add(dismiss);
		this.getContentPane().add(Box.createVerticalStrut(10));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.pack();
		this.setVisible(true);
	}
	
	public void kill() {
		this.setVisible(false);
		this.dispose();
	}
	
	public static void main(String[] args) {
		new ErrorDialog(new Throwable("<html>This is a test to see,<br>how the Dialog I employed handles multiline text.</html>"));
	}
}
