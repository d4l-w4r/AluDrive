package alternateGui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StatusPanel extends JPanel{

	private static final long serialVersionUID = -8353479383875379010L;
	public JLabel statusIcon;
	public StatusPanel() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 2));
		this.add(initStatusIcon());
		this.setBackground(GUIConstants.elementBG);
	}
	
	private JLabel initStatusIcon() {
		statusIcon = new JLabel(new ImageIcon(GUIConstants.iconPack + "status/22/connection.png"));
		return statusIcon;
	}
	
	public void updateStatus(int statusCode) {
		/*
		 * statusCode		status
		 * ==========		======
		 * 0				connection established
		 * 1				operation pending (upload/download/sync in progress
		 * 2				drive error occured		
		 */
		
		switch (statusCode) {
		case 0:
			statusIcon.setIcon(new ImageIcon(GUIConstants.iconPack + "status/22/connected.png"));
			break;
		case 1:
			statusIcon.setIcon(new ImageIcon(GUIConstants.iconPack + "status/22/operation-pending.png"));
			break;
		case 2:
			statusIcon.setIcon(new ImageIcon(GUIConstants.iconPack + "status/22/sync_error.png"));
			break;
		default:
			System.err.println("Unregocnized statusCode: " + statusCode + ".");
			break;
		}
	}
}
