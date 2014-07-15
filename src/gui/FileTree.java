package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import startup.MainController;
import utils.ConfigOptions;

/**
 * Display a file system in a JTree view
 * 
 * @version $Id: FileTree.java,v 1.9 2004/02/23 03:39:22 ian Exp $
 * @author Ian Darwin
 */
public class FileTree extends JPanel {

	static final long serialVersionUID = 1L;
<<<<<<< HEAD
	
  /** Construct a FileTree */
	JTree tree;
  public FileTree(File dir) {
	  
    setLayout(new BorderLayout());

    // Make a tree list with all the nodes, and make it a JTree
    tree = new JTree(addNodes(null, dir));

    // Add a listener
    /*tree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
            .getPath().getLastPathComponent();
        System.out.println("DEBUG (gui.FileTree): You selected " + node);
        controll.pushToDrive(new File(e.getPath().toString()));
      }
    });*/
  
=======
	private JTree tree;

	/** Construct a FileTree */

	public FileTree(File dir) {
		super();
		// this.setLayout(new BorderLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// Make a tree list with all the nodes, and make it a JTree
		tree = new JTree(addNodes(null, dir));

		// Lastly, put the JTree into a JScrollPane.
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.getViewport().add(tree);
		// add(BorderLayout.CENTER, scrollpane);
		this.add(scrollpane);
	}

	/** Add nodes from under "dir" into curTop. Highly recursive. */
	@SuppressWarnings("unchecked")
	DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
		String curPath = dir.getPath();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
		if (curTop != null) { // should only be null at root
			curTop.add(curDir);
		}
		@SuppressWarnings("rawtypes")
		Vector ol = new Vector();
		String[] tmp = dir.list();
		for (int i = 0; i < tmp.length; i++)
			ol.addElement(tmp[i]);
		Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
		File f;
		@SuppressWarnings("rawtypes")
		Vector files = new Vector();
		// Make two passes, one for Dirs and one for Files. This is #1.
		for (int i = 0; i < ol.size(); i++) {
			String thisObject = (String) ol.elementAt(i);
			String newPath;
			if (curPath.equals(".")) {
				newPath = thisObject;
			}
			else {
				newPath = curPath + File.separator + thisObject;
			
			} 
			if ((f = new File(newPath)).isDirectory())
				try {
					addNodes(curDir, f);
				} catch (Exception e) {
					System.err.println(f);
				}
			else
				files.addElement(thisObject);
		}
		// Pass two: for files.
		for (int fnum = 0; fnum < files.size(); fnum++)
			curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
		return curDir;
	}
>>>>>>> 9d54729123a10fac1106286540c9ca0ffaa47f4c

	public Dimension getPreferredSize() {
		return new Dimension(20, 100);
	}

<<<<<<< HEAD
  /** Add nodes from under "dir" into curTop. Highly recursive. */
  @SuppressWarnings("unchecked")
public DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
    String curPath = dir.getPath();
    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
    if (curTop != null) { // should only be null at root
      curTop.add(curDir);
    }
    @SuppressWarnings("rawtypes")
	Vector ol = new Vector();
    String[] tmp = dir.list();
    for (int i = 0; i < tmp.length; i++)
      ol.addElement(tmp[i]);
    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
    File f;
    @SuppressWarnings("rawtypes")
	Vector files = new Vector();
    // Make two passes, one for Dirs and one for Files. This is #1.
    for (int i = 0; i < ol.size(); i++) {
      String thisObject = (String) ol.elementAt(i);
      String newPath;
      if (curPath.equals("."))
        newPath = thisObject;
      else
        newPath = curPath + File.separator + thisObject;
      if ((f = new File(newPath)).isDirectory())
    	  try{
        addNodes(curDir, f);
    	  }
      catch (Exception e)
      {System.err.println(f);}
      else
      files.addElement(thisObject);
    }
    // Pass two: for files.
    for (int fnum = 0; fnum < files.size(); fnum++)
      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
    return curDir;
  }
  
	  public Dimension getPreferredSize() {
	    return new Dimension(20, 100);
	  }
	  
	  public JTree getTreeModel() {
		  return tree;
	  }
	  
	  public void updateTreeView(String view) {
		  if(view.equals("local")) {
			  tree = null;
			  tree = new JTree(addNodes(null, new File(ConfigOptions.FILETREE_ROOT_PATH)));
		  } else {
			  tree = null;
			  tree = new JTree(addNodes(null, new File(ConfigOptions.FILE_SYNC_PATH)));
		  }
	  }
=======
	public JTree getTreeModel() {
		return tree;
	}
>>>>>>> 9d54729123a10fac1106286540c9ca0ffaa47f4c

	public void resetTreeModel(File dir) {
			JTree newTree = new JTree(addNodes(null, dir));
			tree.setModel(newTree.getModel());
			DefaultTreeModel m = (DefaultTreeModel) tree.getModel();
			m.reload();
			repaint();
	}

}
