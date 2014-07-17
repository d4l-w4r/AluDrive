package alternateGui;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.sun.org.apache.bcel.internal.generic.NEW;


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
		UIManager.put("ScrollBar.thumb", new ColorUIResource(130, 129, 129));
		UIManager.put("ScrollBar.thumbDarkShadow", new ColorUIResource(130, 129, 129));
		UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(130, 129, 129));
		UIManager.put("ScrollBar.thumbShadow", new ColorUIResource(130, 129, 129));
		UIManager.put("ScrollBar.width", 14);
	}
}
