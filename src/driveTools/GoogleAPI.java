package driveTools;

import java.awt.Dialog.ModalityType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

import org.apache.commons.io.IOUtils;


import utils.ConfigOptions;
import utils.MimeTypes;

import alternateGui.GUIConstants;

import com.google.api.client.googleapis.media.MediaHttpUploader.UploadState;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.model.File;

import cryptoTools.EncryptionAPI;
import cryptoTools.ProgressDialog;

public class GoogleAPI extends Observable{

	private final GoogleAuth _auth;
	private final GoogleBackend _backend;
	
	public GoogleAPI(EncryptionAPI crypt, Observer o) {
		super();
		addObserver(o);
		_auth = new GoogleAuth(crypt);
		_backend = new GoogleBackend(_auth.getCredential());
		String img = getUserImage();
		setChanged();
		notifyObservers(img);
		String name = getUserName();
		setChanged();
		notifyObservers(name);
		HashMap<String, Long> capacity = getDriveCapacity();
		System.out.println(capacity.get("used") + ", of " + capacity.get("max"));
		setChanged();
		notifyObservers(capacity);
	}
	
	/**
	 * Uploads a given file and its MetaData object to Google Drive
	 * and synchronizes the header files in FILE_SYNC_PATH
	 * 
	 * @param java.io.File src 
	 * @param meta
	 */
	public void upload(java.io.File src, MetaData meta) {
		if(!src.exists()) {
			//TODO: Handle properly
			System.err.println("ERROR (driveTools.GoogleAPI): The selected file (" + src.getPath() + ") doesn't exist.");
			return;
		}
		if(src.isDirectory()) {
			//create Drive folder
			String folderID = _backend.mkdir(new MetaData(src.getName(), MimeTypes.getType(".googlDir")));
			java.io.File[] files = src.listFiles();
			for(java.io.File f: files) {
				if(!f.isDirectory()) {
					//upload(f, new MetaData(f.getName(), MimeTypes.getType(f.getAbsolutePath())));
					_backend.upload(f, new MetaData(f.getName(), MimeTypes.getType(f.getAbsolutePath())), folderID);
					src.delete();
				} 
			}
		} else {
			_backend.upload(src, meta);
		}
		src.delete();
	}
	
	public ArrayList<java.io.File> download(java.io.File file) {
		if(file.isDirectory()) {
			java.io.File tmpFolder = new java.io.File(ConfigOptions.TMP_FOLDER + file.getName());
			tmpFolder.mkdirs();
			String tmpFldrPath = tmpFolder.getAbsolutePath() + System.getProperty("file.separator");
			ArrayList<java.io.File> fileList = new ArrayList<java.io.File>(file.list().length);
			for (java.io.File f: file.listFiles()) {
				System.out.println("DEBUG (driveTools.GoogleAPI: 85): Current file: " + f.getAbsolutePath());
				if(!f.isDirectory()) {
					DriveFileModel fileModel = new DriveFileModel(f.getAbsolutePath());
					InputStream contentStream = _backend.download(fileModel);
					java.io.File tmpFile = new java.io.File(tmpFldrPath + f.getName());
					try {
						FileOutputStream writer = new FileOutputStream(tmpFile);
						writer.write(IOUtils.toByteArray(contentStream));
						writer.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
					fileList.add(tmpFile);
				}
			}
			return fileList;
		}
		
		DriveFileModel fileModel = new DriveFileModel(file.getAbsolutePath());
		InputStream contentStream = _backend.download(fileModel);
		java.io.File tmpFile = new java.io.File(ConfigOptions.TMP_FOLDER + file.getName());
		try {
			FileOutputStream writer = new FileOutputStream(tmpFile);
			writer.write(IOUtils.toByteArray(contentStream));
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		ArrayList<java.io.File> outArr = new ArrayList<java.io.File>(1);
		outArr.add(tmpFile);
		return outArr;
	}
	
	public void delete(java.io.File file){
		System.err.println("Not yet implemented. (Delete)");

	}
	/**
	 * Synchronizes the contents on google Drive with the header files on the 
	 * local file system.
	 * After this function returns the files in the FILE_SYNC_PATH and on the
	 * Google Drive should be the same.
	 */
	public void fetchDriveContents() {
		_backend.fetchContents();
		//_backend.getRemoteData();
	}
	
	/**
	 * Prints a list of all files on the Google Drive to stdout.
	 * Format: $Filename at:	$linkToFile
	 */
	public void listRemoteContents() {
		List<File> list = _backend.list();
		System.out.println("Listing remote files: \n");
		for (File f:list) {
			//System.out.println(f.getTitle() + " at:\t " + f.getAlternateLink());
			System.out.println(f);
		}
	}
	
	/**
	 * Prints a list of all header files in FILE_SYNC_PATH to stdout.
	 * Format: $Filename at:	$/ABSOLUTE/PATH/TO/FILE
	 */
	public void listLocalContents() {
		java.io.File[] files = new java.io.File(ConfigOptions.FILE_HEADER_PATH).listFiles();
		System.out.println("Listing locally stored header files: \n");
		for (java.io.File f: files) {
			System.out.println(f.getName() + " at:\t " + f.getAbsolutePath());
		}
	}
	
	public String getUserImage() {
		InputStream is = _backend.getUserImage();
		String path = GUIConstants.iconPack + "userImg";
		try {
			FileOutputStream writer = new FileOutputStream(path);
			writer.write(IOUtils.toByteArray(is));
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public String getUserName() {
		return _backend.getUserName();
	}
	
	public HashMap<String, Long> getDriveCapacity() {
		return _backend.getDriveCapacity();
	}
	
	public void refreshDriveInfo() {
		String img = getUserImage();
		setChanged();
		notifyObservers(img);
		String name = getUserName();
		setChanged();
		notifyObservers(name);
		HashMap<String, Long> map = getDriveCapacity();
		setChanged();
		notifyObservers(map);
	}
	
	public static void main(String[] args) {
		GoogleAPI api = new GoogleAPI(new EncryptionAPI(), null);
		//api.fetchDriveContents();
		//api.listRemoteContents();
		//api.download(new java.io.File(ConfigOptions.FILE_HEADER_PATH + "data_sample_FC5.png"));
		//api.fetchDriveContents();
		System.out.println(api.getUserImage());
	} 
	//TODO: implement all the other file operations
}
