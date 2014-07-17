package alternateGui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;




public class UserInfoPanelView extends JPanel implements Observer{
	private static final long serialVersionUID = -3624573267743882048L;
	
	private JLabel userPic;
	private JLabel userName;
	private JProgressBar progBar;
	
	public UserInfoPanelView() {
		super();
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
		userName = new JLabel();
		userName.setFont(new Font("Ubuntu", Font.BOLD, 16));
		p2.add(userName);
		//infoPanel.add(new JLabel(user));
		infoPanel.add(p2);
		
		infoPanel.add(initCapacityIndicator());
		JPanel imagePanel = new JPanel();
		imagePanel.setPreferredSize(new Dimension(128, 128));
		imagePanel.setBackground(Color.white);
		imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		//Image img = new ImageIcon(GUIConstants.iconPack + "user.png").getImage();
		//userPic = new JLabel(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
		userPic = new JLabel();
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
	
	private JPanel initCapacityIndicator() {
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		//float capacityInGB = new Float(capacity / Math.pow(1024, 3));
		//float capacityUsed = new Float (used / Math.pow(1024, 3));
		
		progBar = new  JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progBar.setBorder(null);
		progBar.setStringPainted(true);
		progBar.setForeground(new Color(25, 182, 238));
		progBar.setFont(new Font("Ubuntu", Font.BOLD, 14));
		progBar.setUI(new BasicProgressBarUI() {
		      protected Color getSelectionBackground() { return Color.black; }
		      protected Color getSelectionForeground() { return Color.white; }
		    });
		//progBar.setValue((int) ((capacityUsed / capacityInGB) * 100));
		//String s = capacityUsed + "GB, of " + capacityInGB + "GB";
		progBar.setString(""); //to prevent the standard 0% showing
		progBar.setBackground(GUIConstants.elementBG);
		progBar.setBorderPainted(false);
		progBar.setPreferredSize(new Dimension(200, 25));
		content.add(new JLabel("using: "));
		content.add(progBar);
		return content;
	}
	
	
	
	public static void main(String[] args) {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(new UserInfoPanelView());
		test.setSize(400, 128);
		test.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof UserInfoPanelModel) {
			if(arg instanceof String) {
				userName.setText(arg.toString());
			} else if(arg instanceof String[]) {
				String[] array = (String[]) arg;
				String s = array[0] + array[1] + array[2] + array[3];
				progBar.setString(s);
			} else if(arg instanceof ImageIcon) {
				ImageIcon img = (ImageIcon) arg;
				userPic.setIcon(new ImageIcon(img.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
			} else {
				System.out.println(arg);
				progBar.setValue((int) arg);
			}
			this.invalidate();
			this.validate();
			this.repaint();
		}
	}
}
