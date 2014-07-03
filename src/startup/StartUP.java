package startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.python.core.util.FileUtil;
import org.python.google.common.io.Files;

import utils.ConfigOptions;
import utils.ErrorDialog;

import gui.MainWinGUI;

public class StartUP {

	public static void main(String[] args) {
		checkSetUp();
		//debugProperties();
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

		new MainWinGUI();
		
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
        if(!new File(ConfigOptions.PYTHON_FILE_PATH).exists()) {
        	try {
        		File name = new File(ConfigOptions.PYTHON_FILE_PATH);
        		name.mkdirs();
        		//Files.copy(new File("../../pyTools/fileLoader.py"), new File(ConfigOptions.PYTHON_FILE_PATH + "fileLoader.py"));
        	} catch(SecurityException e) {
        		e.printStackTrace();
        	}
        	
        }
	}
	
	public static void debugProperties() {
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
		
		System.out.println(ConfigOptions.AES_KEY_PATH);
		System.out.println(ConfigOptions.AES_KEY_SIZE);
		System.out.println(ConfigOptions.BASEPATH);
		System.out.println(ConfigOptions.CRED_STORE_PATH);
		System.out.println(ConfigOptions.CRYPTPATH);
		System.out.println(ConfigOptions.FILETREE_ROOT_PATH);
		System.out.println(ConfigOptions.KEYPATH);
		System.out.println(ConfigOptions.PBE_MODE);
		System.out.println(ConfigOptions.PRIVATE_KEY_PATH);
		System.out.println(ConfigOptions.PUBLIC_KEY_PATH);	
	}


}
