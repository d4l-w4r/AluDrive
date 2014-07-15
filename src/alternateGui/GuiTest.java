package alternateGui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class GuiTest{

	public GuiTest() {
		final JFrame f = new JFrame("test");
		f.setMinimumSize(new Dimension(692, 338));
		f.setPreferredSize(new Dimension(879, 528));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();
		northPanel.setBorder(LineBorder.createGrayLineBorder());
		northPanel.setBackground(Color.white);
		//northPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		northPanel.setLayout(new BorderLayout(0, 0));
		northPanel.add(new ProgressPanel(0, "/home/daniel/test", 2), BorderLayout.WEST);
		northPanel.add(Box.createHorizontalStrut(320), BorderLayout.CENTER);
		northPanel.add(new UserInfoPanel("Daniel", 10737418240l, 1288490188l), BorderLayout.EAST);
		f.getContentPane().add(northPanel, BorderLayout.NORTH);
		
		f.getContentPane().add(new FileSelectionPanel(), BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(GUIConstants.elementBG);
		//southPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 2));
		southPanel.setLayout(new BorderLayout(2,2));
		southPanel.add(new ActionPanel(), BorderLayout.WEST);
		southPanel.add(Box.createHorizontalStrut(770), BorderLayout.CENTER);
		southPanel.add(new StatusPanel(), BorderLayout.EAST);
		f.getContentPane().add(southPanel, BorderLayout.SOUTH);
		

		
		
		f.pack();
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GuiTest();
	}
}
