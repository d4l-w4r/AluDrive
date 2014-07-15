package alternateGui;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class MyScrollbarUI extends BasicScrollBarUI {

	public MyScrollbarUI() {
		super();
	}
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton deadBtn = new JButton();
		deadBtn.setBorder(null);
		deadBtn.setContentAreaFilled(false);
		return deadBtn;
	}
	
	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton deadBtn = new JButton();
		deadBtn.setBorder(null);
		deadBtn.setContentAreaFilled(false);
		return deadBtn;
	}
	
	public void destroyDefaultLandL() {
		createDecreaseButton(0);
		createIncreaseButton(0);
		trackColor = Color.red;
	}
}
