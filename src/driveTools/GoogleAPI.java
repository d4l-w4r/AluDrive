package driveTools;

import java.awt.Dialog.ModalityType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

import utils.ConfigOptions;

import com.google.api.client.util.DateTime;
import com.google.api.services.drive.model.File;

import cryptoTools.EncryptionAPI;
import cryptoTools.ProgressDialog;

public class GoogleAPI {

	private final GoogleAuth _auth;
	private final GoogleBackend _backend;
	
	public GoogleAPI(EncryptionAPI crypt) {
		_auth = new GoogleAuth(crypt);
		boolean success = false;
		try {
			success = _auth.refreshAccessToken();
		} catch(IOException e) {
			System.err.println("ERROR (driveTools.GoogleAPI): IOException while trying to refresh token.\n" + e.getMessage());
		}
		System.out.println("DEBUG (driveTools.GoogleAPI): Token refreshed: " + success);
		_backend = new GoogleBackend(_auth.getCredential());
	}
	
	public void upload(java.io.File src, MetaData meta) {
		if(!src.exists()) {
			//TODO: Handle properly
			System.err.println("ERROR (driveTools.GoogleAPI): The selected file (" + src.getPath() + ") doesn't exist.");
			return;
		}
		_backend.upload(src, meta);
		fetchDriveContents();
	}
	
	public void fetchDriveContents() {
		_backend.fetchContents();
	}
	
	public void list() {
		List<File> list = _backend.list();
		File f = list.get(0);
		System.out.println("DEBUG (driveTools.GoogleAPI): Filename: " + f.getTitle());
		System.out.println("DEBUG (driveTools.GoogleAPI): Url: " + f.getDownloadUrl());
		System.out.println("DEBUG (driveTools.GoogleAPI): Size: " + f.getFileSize());
	}
	
	public static void main(String[] args) {
		GoogleAPI api = new GoogleAPI(new EncryptionAPI());
		api.fetchDriveContents();
		api.list();
	} 
	//TODO: implement all the other file operations
}
