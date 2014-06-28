package startup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import utils.ConfigOptions;
import utils.ErrorDialog;

import gui.MainWinGUI;

public class StartUP {

	public static File selection;
	
	public static void main(String[] args) {
		checkSetUp();
		
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		final MainWinGUI gui = new MainWinGUI();
		final MainController control = new MainController();
		
		gui.upload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(selection != null) {
					control.pushToDrive(selection);
					gui.remoteTreeModel.updateTreeView("remote");
					gui.remoteTreeModel.repaint();
				}
			}
		});
		
		gui.treeModel.getTreeModel().addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				TreePath p = e.getPath();
				String s1 = (String) (p.getPathComponent(0).toString());
				String s2 = (String) (p.getPathComponent(1).toString());
				
				selection = new File(s1 + System.getProperty("file.separator") + s2);
			}
		});
		
		//debugProperties();
		
	}
	
	private static void checkSetUp() {
		if(!System.getProperty("java.specification.version").equals("1.8")) {
			new ErrorDialog(new Throwable("<html>Fatal Exception!:<br> It appears that you do not have the required Java JRE8 installed.<br>Aborting execution...<br></html>"));
			System.exit(2);
		}
		if(!new File(ConfigOptions.BASEPATH).exists()) {
			try {
				File name = new File(ConfigOptions.KEYPATH);
				name.mkdirs();
			} catch(SecurityException se) {
				new ErrorDialog(new Throwable("<html>Could not create the path<br>"+ConfigOptions.BASEPATH+"<br>Please check that you have sufficient permissions.<br></html>"));
			}
		}
		if(!new File(ConfigOptions.FILE_SYNC_PATH).exists()) {
			try {
				File name = new File(ConfigOptions.FILE_SYNC_PATH);
				name.mkdirs();
			} catch(SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void debugProperties() {
		System.err.println("=== Java System & Environment Properties ===");
		System.out.println("java.runtime.version: " + System.getProperty("java.runtime.version"));
		System.out.println("java.vm.specification.version: " + System.getProperty("java.vm.specification.version"));
		System.out.println("java.version: " + System.getProperty("java.version"));
		System.out.println("java.io.tmpdir: " + System.getProperty("java.io.tmpdir"));
		System.out.println("line.separator: " + System.getProperty("line.separator"));
		System.out.println("file.separator: " + System.getProperty("file.separator"));
		System.out.println("os.name: " + System.getProperty("os.name"));
		System.out.println("user.home: " + System.getProperty("user.home"));
		System.out.println("user.name: " + System.getProperty("user.name"));
		System.out.println("user.language: " + System.getProperty("user.language"));
		
		System.err.println("\n=== Application Preferences ===");
		System.out.println("AES Key Path: " + ConfigOptions.AES_KEY_PATH);
		System.out.println("AES Key Size: " + ConfigOptions.AES_KEY_SIZE);
		System.out.println("Application Root: " + ConfigOptions.BASEPATH);
		System.out.println("Application Credential Store: " + ConfigOptions.CRED_STORE_PATH);
		System.out.println("Application Crypto Path: " + ConfigOptions.CRYPTPATH);
		System.out.println("Userspace Filetree Root: " + ConfigOptions.FILETREE_ROOT_PATH);
		System.out.println("RSA Key Path" + ConfigOptions.KEYPATH);
		System.out.println("Password-Based-Encryption Mode: " + ConfigOptions.PBE_MODE);
		System.out.println("RSA Private Key Path: " + ConfigOptions.PRIVATE_KEY_PATH);
		System.out.println("RSA Public Key Path: " + ConfigOptions.PUBLIC_KEY_PATH);	
	}


}
