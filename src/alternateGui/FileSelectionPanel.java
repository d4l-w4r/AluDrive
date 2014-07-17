package alternateGui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class FileSelectionPanel extends JPanel{
	private static final long serialVersionUID = -3113285539256532670L;

	private JScrollPane scrollPane;
	public GridLayout g;
	public FileSelectionPanel() {
		super();
		this.setLayout(new BorderLayout());
		final JPanel content = new JPanel();
		content.setBackground(Color.white);
		g = new GridLayout(1, 4, 20, 20);
		content.setLayout(g);
		for (int i = 0; i < 23; ++i) {
			int count = content.getComponentCount() % 4;
			if(count == 0) {
				System.out.println("containing multiple of 4");
				g.setRows(g.getRows() + 1);
				JLabel filled = new JLabel(new ImageIcon(GUIConstants.iconPack + "doc-mime.png"));
				filled.setName("full");
				content.add(filled);
				JLabel placeholder = new JLabel();
				JLabel placeholder2 = new JLabel();
				placeholder.setName("empty");
				placeholder2.setName("empty");
				content.add(placeholder);
				content.add(placeholder2);
			} else {
				switch (count) {
				case 1:
					System.out.println("add three empty elements");
					JLabel inserted = new JLabel(new ImageIcon(GUIConstants.iconPack + "doc-mime.png"));
					inserted.setName("full");
					content.add(inserted);
					break;
				case 2:
					System.out.println("add two empty elements");
					content.add(new JLabel(new ImageIcon(GUIConstants.iconPack + "doc-mime.png")));
					break;
				case 3: 
					System.out.println("add one empty element");
					JLabel filled = new JLabel(new ImageIcon(GUIConstants.iconPack + "doc-mime.png"));
					filled.setName("full");
					content.add(filled, content.getComponentCount() - 2);
					if(content.getComponent(content.getComponentCount() -1) != null) {
						System.out.println(content.getComponent(content.getComponentCount() -1));
						if((content.getComponent(content.getComponentCount() -1).getName()).equals("empty")) {
							content.remove(content.getComponentCount() -1);
						}
					}
					break;
				default:
					break;
				}
			}
		}
		
		
		/*j.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int count = content.getComponentCount() % 4;
				if(count == 0) {
					System.out.println("containing multiple of 4");
					g.setRows(g.getRows() + 1);
					JLabel filled = new JLabel(new ImageIcon("/home/daniel/Documents/icon_pack/doc-mime.png"));
					filled.setName("full");
					content.add(filled);
					JLabel placeholder = new JLabel();
					JLabel placeholder2 = new JLabel();
					placeholder.setName("empty");
					placeholder2.setName("empty");
					content.add(placeholder);
					content.add(placeholder2);
				} else {
					switch (count) {
					case 1:
						System.out.println("add three empty elements");
						JLabel inserted = new JLabel(new ImageIcon("/home/daniel/Documents/icon_pack/doc-mime.png"));
						inserted.setName("full");
						content.add(inserted);
						break;
					case 2:
						System.out.println("add two empty elements");
						content.add(new JLabel(new ImageIcon("/home/daniel/Documents/icon_pack/doc-mime.png")));
						break;
					case 3: 
						System.out.println("add one empty element");
						JLabel filled = new JLabel(new ImageIcon("/home/daniel/Documents/icon_pack/doc-mime.png"));
						filled.setName("full");
						content.add(filled, content.getComponentCount() - 2);
						if(content.getComponent(content.getComponentCount() -1) != null) {
							System.out.println(content.getComponent(content.getComponentCount() -1));
							if((content.getComponent(content.getComponentCount() -1).getName()).equals("empty")) {
								content.remove(content.getComponentCount() -1);
							}
						}
						break;
					default:
						break;
					}
				}
			}
		});
		this.add(j, BorderLayout.NORTH);*/
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getViewport().add(content);
		scrollPane.setBorder(null);
		JScrollBar myBar = new JScrollBar();
		
		MyScrollbarUI myUI = new MyScrollbarUI();
		myUI.destroyDefaultLandL();
		
		myBar.setUI(myUI);
		this.add(scrollPane, BorderLayout.CENTER);
		this.setBackground(Color.white);
		scrollPane.setVerticalScrollBar(myBar);  
		g.preferredLayoutSize(content);
	}
	
	public static void main(String[] args) {
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(new FileSelectionPanel());
		test.pack();
		test.setVisible(true);
	}
	
}
