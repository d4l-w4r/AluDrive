package alternateGui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;


public class StatusPanelView extends JPanel implements Observer {

	private static final long serialVersionUID = -8353479383875379010L;
	private JLabel statusIconLabel;
	
	public StatusPanelView() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 2));
		this.add(initStatusIcon());
		this.setBackground(GUIConstants.elementBG);
	}
	
	private JLabel initStatusIcon() {
		statusIconLabel = new JLabel();
		return statusIconLabel;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof StatusPanelModel) {
			String iconPath = arg.toString();
			statusIconLabel.setIcon(new ImageIcon(iconPath));
			System.out.println(statusIconLabel);
			this.invalidate();
			this.validate();
			this.repaint();
		}
	}
}
