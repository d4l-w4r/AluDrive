package driveTools;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class FiniteProgressDialog extends JDialog {

	private final JProgressBar _progBar;
	
	public FiniteProgressDialog(String title, String body) {
		super();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(title);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.getContentPane().add(Box.createHorizontalStrut(20));
		this.getContentPane().add(new JLabel(body));
		this.getContentPane().add(Box.createHorizontalStrut(20));
		_progBar = new JProgressBar(SwingConstants.HORIZONTAL);
		_progBar.setStringPainted(true);
		this.getContentPane().add(Box.createVerticalStrut(15));
		this.getContentPane().add(Box.createHorizontalStrut(20));
		this.getContentPane().add(_progBar);
		this.getContentPane().add(Box.createHorizontalStrut(20));
		this.pack();
	}
	
	public void updateProgress(double val) {
		double value = val*100;
		_progBar.setString(String.valueOf((int)value) + "%");
		_progBar.setValue((int) value);
	}
}
