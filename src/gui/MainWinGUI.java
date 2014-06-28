package gui;


import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import utils.ConfigOptions;




public class MainWinGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JPanel _contentPane = new JPanel();
	JPanel _paneLeft = new JPanel();
	JPanel _paneRight = new JPanel();
	
	public JButton upload;
	public JButton download;
	public JButton delete;
	public FileTree treeModel;
	public FileTree remoteTreeModel;
	public JMenuItem loadFile;
	
	public MainWinGUI()
	{
		super("GDrive File Coder");	
		this.setSize(700, 500);
		
		JPanel menuPane = initMenuPanel();
		this.setJMenuBar(initMenuBar());
		_paneLeft = initLeftPanel();
		_paneRight = initRightPanel();
		

			
		this.getContentPane().setLayout(new BorderLayout());
		//this.getContentPane().add(menuPane, BorderLayout.NORTH);
		this.getContentPane().add(_contentPane, BorderLayout.CENTER);
		_contentPane.setBackground(Color.white);
		_contentPane.setLayout(new BorderLayout(10, 10));
		_contentPane.add(menuPane, BorderLayout.CENTER);
		_contentPane.add(_paneLeft, BorderLayout.LINE_START);
		_contentPane.add(_paneRight, BorderLayout.LINE_END);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(700, 504));
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private JPanel initMenuPanel() {
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		upload = new JButton("Upload");
		upload.setPreferredSize(new Dimension(90, 30));
		upPanel.add(upload);
		
		JPanel downPanel = new JPanel();
		downPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		download = new JButton("Download");
		download.setPreferredSize(new Dimension(90, 30));
		downPanel.add(download);
		
		JPanel delPanel = new JPanel();
		delPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		delete = new JButton("Delete");
		delete.setPreferredSize(new Dimension(90, 30));
		delPanel.add(delete);
		
		JPanel menuPane = new JPanel();
		//menuPane.setBackground(Color.white);
		menuPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.Y_AXIS));
		menuPane.setPreferredSize(new Dimension(100,425));
		
		
		menuPane.add(Box.createVerticalStrut(10));
		menuPane.add(delPanel);
		menuPane.add(downPanel);
		menuPane.add(upPanel);
		menuPane.add(Box.createVerticalStrut(250));
		return menuPane;
	}
	

	private JMenuBar initMenuBar() {
		JMenuBar bar = new JMenuBar();
		JMenu fileM = new JMenu("File");
		

		loadFile = new JMenuItem("Load File");
		fileM.add(loadFile);
		bar.add(fileM);

		return bar;
	}
	
	private JPanel initLeftPanel()
	{
		JPanel content = new JPanel();
		treeModel = new FileTree (new File(ConfigOptions.FILETREE_ROOT_PATH));
		//JTree tree = new JTree();
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setPreferredSize(new Dimension(200, 450));
		scrollpane.getViewport().add(treeModel);
		content.add(scrollpane);
		return content;
	}
	
	private JPanel initRightPanel()
	{
		JPanel content = new JPanel();
		//JTree tree = new JTree();
		remoteTreeModel = new FileTree(new File(ConfigOptions.FILE_SYNC_PATH));
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setPreferredSize(new Dimension(200, 450));
		scrollpane.getViewport().add(remoteTreeModel);
		content.add(scrollpane);
		return content;
	}
}
