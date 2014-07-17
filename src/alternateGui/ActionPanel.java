package alternateGui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ActionPanel extends JPanel {
	private static final long serialVersionUID = 6847028417991009408L;
	
	public JButton addBtn;
	public JButton removeBtn;
	public JButton refreshBtn;
	
	public ActionPanel() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 2));
		addBtn = initAddButton();
		removeBtn = initRemoveButton();
		refreshBtn = initRefreshButton();
		
		this.add(refreshBtn);
		this.add(addBtn);
		this.add(removeBtn);
		this.setBackground(GUIConstants.elementBG);
	}
	
	private JButton initAddButton() {
		JButton btn = new JButton();
		btn.setRolloverEnabled(true);
		btn.setContentAreaFilled(false);
		btn.setBorder(null);
		
		Icon btn_idle = new ImageIcon(GUIConstants.iconPack + "actions/22/add.png");
		Icon btn_hover = new ImageIcon(GUIConstants.iconPack + "actions/22/add-hover.png");
		Icon btn_active = new ImageIcon(GUIConstants.iconPack + "actions/22/add-active.png");
		
		btn.setIcon(btn_idle);
		btn.setRolloverIcon(btn_hover);
		btn.setPressedIcon(btn_active);
		return btn;
	}
	
	private JButton initRemoveButton() {
		JButton btn = new JButton();
		btn.setRolloverEnabled(true);
		btn.setContentAreaFilled(false);
		btn.setBorder(null);
		
		Icon btn_idle = new ImageIcon(GUIConstants.iconPack + "actions/22/remove.png");
		Icon btn_hover = new ImageIcon(GUIConstants.iconPack + "actions/22/remove-hover.png");
		Icon btn_active = new ImageIcon(GUIConstants.iconPack + "actions/22/remove-active.png");
		
		btn.setIcon(btn_idle);
		btn.setRolloverIcon(btn_hover);
		btn.setPressedIcon(btn_active);
		return btn;
	}
	
	private JButton initRefreshButton() {
		JButton btn = new JButton();
		btn.setRolloverEnabled(true);
		btn.setContentAreaFilled(false);
		btn.setBorder(null);
		
		Icon btn_idle = new ImageIcon(GUIConstants.iconPack + "actions/22/refresh.png");
		Icon btn_hover = new ImageIcon(GUIConstants.iconPack + "actions/22/refresh-hover.png");
		Icon btn_active = new ImageIcon(GUIConstants.iconPack + "actions/22/refresh-active.png");
		
		btn.setIcon(btn_idle);
		btn.setRolloverIcon(btn_hover);
		btn.setPressedIcon(btn_active);
		return btn;
	}
	
	public static void main(String[] args) {
		JFrame test = new JFrame("Test");
		test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//test.setPreferredSize(new Dimension(80, 50));
		test.add(new ActionPanel());
		test.pack();
		test.setVisible(true);
	}
}
