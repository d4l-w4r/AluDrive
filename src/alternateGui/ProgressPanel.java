package alternateGui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;


public class ProgressPanel extends JPanel{
	private static final long serialVersionUID = -8371250957134947336L;
	
	private JLabel operationLabel;
	private JLabel fileLabel;
	private JLabel counterLabel;
	private JProgressBar progBar;
	
	private final int _totalFiles;
	
	/**
	 * operationCode	Operation
	 * =============    =========
	 * 0				Download
	 * 1				Upload
	 * @param operationCode
	 */
	public ProgressPanel(int operationCode, String filename, int totalFiles) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//this.setLayout(new GridLayout(2, 1));
		_totalFiles = totalFiles;
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.white);
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
		JPanel headerContent = new JPanel();
		headerContent.setBackground(Color.WHITE);
		headerContent.setLayout(new BoxLayout(headerContent, BoxLayout.PAGE_AXIS));
		headerContent.add(Box.createVerticalStrut(16));
		headerContent.add(initOperationLabel(operationCode));
		headerContent.add(Box.createVerticalStrut(10));
		headerContent.add(fileLabel = new JLabel(filename));
		headerPanel.add(headerContent);
		
		JPanel progressPanel = new JPanel();
		progressPanel.setBackground(Color.white);
		progressPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 0));
		progressPanel.add(initProgressBar());
		progressPanel.add(initCounterLabel(totalFiles));
		this.setBackground(Color.white);
		this.add(headerPanel);
		this.add(progressPanel);
		
	}
	
	private JLabel initOperationLabel(int operationCode) {
		switch (operationCode) {
		case 0:
			operationLabel = new JLabel("Downloading:");
			break;
		case 1:
			operationLabel = new JLabel("Uploading:");
			break;
		default:
			System.err.println("Unregocnized operationCode: " + operationCode + ".");
			break;
		}
		return operationLabel;
	}
	
	private JProgressBar initProgressBar() {
		progBar = new  JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progBar.setBorder(null);
		progBar.setStringPainted(true);
		progBar.setForeground(new Color(25, 182, 238));
		progBar.setValue(65);
		progBar.setFont(new Font("Ubuntu", Font.BOLD, 14));
		progBar.setUI(new BasicProgressBarUI() {
		      protected Color getSelectionBackground() { return Color.black; }
		      protected Color getSelectionForeground() { return Color.white; }
		    });
		//progBar.setBackground(new Color(212, 217, 209));
		progBar.setBackground(GUIConstants.elementBG);
		progBar.setBorderPainted(false);
		progBar.setPreferredSize(new Dimension(200, 25));
		return progBar;
	}
	
	private JLabel initCounterLabel(int total) {
		counterLabel = new JLabel();
		String content = "1/" + total;
		counterLabel.setText(content);
		return counterLabel;
	}
	
	public void updateFileName(String filename) {
		fileLabel.setText(filename);
	}
	
	public void updateProgressBar(int val) {
		progBar.setValue(val);
	}
	
	public void updateCounter(int currentFileNo) {
		counterLabel.setText(currentFileNo + "/" + _totalFiles);
	}
	
	public static void main(String[] args) {
		JFrame test =  new JFrame("test");
		test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//test.setPreferredSize(new Dimension(320, 240));
		test.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
		test.add(new ProgressPanel(0, "/home/daniel/test.file", 12));
		test.pack();
		test.setVisible(true);
	}
}
