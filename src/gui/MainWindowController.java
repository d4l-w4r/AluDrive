package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import utils.ConfigOptions;
import utils.MimeTypes;

import cryptoTools.EncryptionAPI;
import driveTools.GoogleAPI;
import driveTools.MetaData;

public class MainWindowController {
	MainWinGUI _gui;
	EncryptionAPI _cryptAPI;
	GoogleAPI _driveAPI;
	
	java.io.File currentLocalSelect;
	java.io.File currentRemoteSelect;
	int lastSelect = -1;
	
	public MainWindowController() {
		_gui = new MainWinGUI(new File(ConfigOptions.FILETREE_ROOT_PATH), new File(ConfigOptions.FILE_HEADER_PATH));
		_cryptAPI = new EncryptionAPI();
		_driveAPI = new GoogleAPI(_cryptAPI);
		
		registerListeners();
	}
	
	private void registerListeners() {
		_gui.upload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker worker = new SwingWorker() {
					@Override
					protected Void doInBackground() {
						java.io.File crypt = new java.io.File(ConfigOptions.TMP_FOLDER + currentLocalSelect.getName());
						_cryptAPI.encryptFile(currentLocalSelect, crypt);
						_driveAPI.upload(crypt, new MetaData(currentLocalSelect.getName(), MimeTypes.getType(currentLocalSelect.getAbsolutePath())));
						crypt.delete();
						return null;
					}
				};
				worker.execute();
			}
		});
		
		_gui.download.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(currentRemoteSelect == null)) {
					ArrayList<java.io.File> fileList = _driveAPI.download(currentRemoteSelect);
					
					for (File f : fileList)
					{
						_cryptAPI.decryptFile(f, new File(ConfigOptions.FILE_DOWNLOAD_ROOT + f.getName()));
						f.delete();
					}
				} else {
					System.err.println("DEBUG (gui.MainWindowController: 68): No file selection has been made on the remote tree");
				}
			}
		});
		
		_gui.delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_driveAPI.delete(currentRemoteSelect);
			}
		});
		
		_gui.refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker worker = new SwingWorker() {
					@Override
					protected Void doInBackground() {
						_driveAPI.fetchDriveContents();
						return null;
					}
				};
				
				worker.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if(evt.getNewValue().equals(StateValue.DONE)) {
							_gui.remoteTree.resetTreeModel(new java.io.File(ConfigOptions.FILE_HEADER_PATH));
						}
					}
				});
				worker.execute();
			}
		});
		
		_gui.localTree.getTreeModel().addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				Object[] path = e.getPath().getPath();
				  if(new java.io.File(path[path.length - 1].toString()).isDirectory()) {
					  currentLocalSelect = new java.io.File(path[path.length - 1].toString());
					  System.out.println("DEBUG (gui.MainWindowController: 111): Selected: " + currentLocalSelect.getAbsolutePath());
				  } else {
			    	  String parent = path[path.length - 2] + System.getProperty("file.separator");
			    	  String name = path[path.length -1].toString();
			    	  currentLocalSelect = new java.io.File(parent + name);
			    	  System.out.println("DEBUG (gui.MainWindowController: 116): Selected: " + currentLocalSelect.getAbsolutePath());
				  }
			}
		});
		
		_gui.remoteTree.getTreeModel().addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				  Object[] path = e.getPath().getPath();
				  if(new java.io.File(path[path.length - 1].toString()).isDirectory()) {
					  currentRemoteSelect = new java.io.File(path[path.length - 1].toString());
					  System.out.println("DEBUG (gui.MainWindowController: 111): Selected: " + currentRemoteSelect.getAbsolutePath());
				  } else {
			    	  String parent = path[path.length - 2] + System.getProperty("file.separator");
			    	  String name = path[path.length -1].toString();
			    	  currentRemoteSelect = new java.io.File(parent + name);
			    	  System.out.println("DEBUG (gui.MainWindowController: 116): Selected: " + currentRemoteSelect.getAbsolutePath());
				  }
			}
		});
	}
	
	public static void main(String[] args) {
		new MainWindowController();
	}
}
