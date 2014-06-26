package cryptoTools;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressDialog extends JDialog{
	private static final long serialVersionUID = -1333006562248666252L;

	public ProgressDialog() {
		super();
		JPanel content = new JPanel();
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Waiting...");
		this.getContentPane().add(content);
		content.setLayout(new BorderLayout(10, 20));
		
		JProgressBar pb = new JProgressBar();
		pb.setIndeterminate(true);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		headerPanel.add(new JLabel("Creating your key pair..."));
		
		content.add(headerPanel, BorderLayout.PAGE_START);
		content.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
		content.add(pb, BorderLayout.CENTER);
		content.add(Box.createHorizontalStrut(50), BorderLayout.LINE_START);
		content.add(Box.createHorizontalStrut(50), BorderLayout.LINE_END);
		content.add(Box.createVerticalStrut(35), BorderLayout.PAGE_END);
		
		this.pack();
	}
}
