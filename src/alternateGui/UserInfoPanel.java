package alternateGui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.text.IconView;


public class UserInfoPanel extends JPanel {
	private static final long serialVersionUID = -3624573267743882048L;
	
	private JLabel userPic;
	private JProgressBar progBar;
	private static long _maxDriveCapacity;
	
	public UserInfoPanel(String user, long maxDriveCapacity, long currentlyUsed) {
		super();
		_maxDriveCapacity = maxDriveCapacity;
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setLayout(new FlowLayout(FlowLayout.TRAILING, 0, 0));
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.setLayout(new GridBagLayout());
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.white);
		infoPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel p1 = new JPanel();
		p1.setSize(new Dimension(200, 15));
		p1.setBackground(Color.white);
		p1.setLayout(new FlowLayout(FlowLayout.LEADING));
		JLabel welcome = new JLabel("Hi, ");
		welcome.setFont(new Font("Ubuntu", Font.BOLD, 22));
		p1.add(welcome);
		//infoPanel.add(new JLabel("Hi, "));
		infoPanel.add(p1);
		
		JPanel p2 = new JPanel();
		p2.setSize(new Dimension(200, 15));
		p2.setBackground(Color.WHITE);
		p2.setLayout(new FlowLayout(FlowLayout.LEADING));
		JLabel username = new JLabel(user);
		username.setFont(new Font("Ubuntu", Font.BOLD, 16));
		p2.add(username);
		//infoPanel.add(new JLabel(user));
		infoPanel.add(p2);
		
		infoPanel.add(initCapacityIndicator(maxDriveCapacity, currentlyUsed));
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(Color.white);
		imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		userPic = new JLabel(new ImageIcon(GUIConstants.iconPack + "stock_person-panel.png"));
		imagePanel.add(userPic);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 2;
		content.add(infoPanel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		content.add(imagePanel, c);
		this.setBackground(Color.white);
		this.add(content);
	}
	
	private JPanel initCapacityIndicator(long capacity, long used) {
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		float capacityInGB = new Float(capacity / Math.pow(1024, 3));
		float capacityUsed = new Float (used / Math.pow(1024, 3));
		
		progBar = new  JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progBar.setBorder(null);
		progBar.setStringPainted(true);
		progBar.setForeground(new Color(25, 182, 238));
		progBar.setFont(new Font("Ubuntu", Font.BOLD, 14));
		progBar.setUI(new BasicProgressBarUI() {
		      protected Color getSelectionBackground() { return Color.black; }
		      protected Color getSelectionForeground() { return Color.white; }
		    });
		progBar.setValue((int) ((capacityUsed / capacityInGB) * 100));
		String s = capacityUsed + "GB, of " + capacityInGB + "GB";
		progBar.setString(s);
		progBar.setBackground(GUIConstants.elementBG);
		progBar.setBorderPainted(false);
		progBar.setPreferredSize(new Dimension(200, 25));
		content.add(new JLabel("using: "));
		content.add(progBar);
		return content;
	}
	
	public void updateDriveCapacity(long usedCapacity) {
		float capacityInGB = new Float(_maxDriveCapacity / Math.pow(1024, 3));
		float capacityUsed = new Float(usedCapacity / Math.pow(1024, 3));
		progBar.setValue((int) ((capacityUsed / capacityInGB) * 100));
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		String used = formatter.format(capacityUsed);
		String s =  used + "GB, of " + capacityInGB + "GB";
		progBar.setString(s);
	}
	
	public void updateUserImage(String image) {
		userPic.setIcon(new ImageIcon(image));
	}
	
	public static void main(String[] args) {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(new UserInfoPanel("Daniel", 16106127360l, 1468006400l));
		test.setSize(400, 128);
		test.setVisible(true);
	}
}
