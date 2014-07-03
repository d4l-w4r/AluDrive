package cryptoTools;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import driveTools.MetaData;

import utils.ConfigOptions;
import utils.MimeTypes;

public class EncryptionAPI {
	private EncryptionBackend _backend;

	public EncryptionAPI() {
		String pw;
		if (!new File(ConfigOptions.PRIVATE_KEY_PATH)
				.exists()
				|| !new File(ConfigOptions.PUBLIC_KEY_PATH)
						.exists()) {
			KeyGen keygen = new KeyGen();
			//TODO: save a hashed version of the give password in a file so we can compare it 
			//with the PasswordPrompt input (jBCrypt)
			pw = keygen.getPassphrase(); 
			while(!new File(ConfigOptions.PUBLIC_KEY_PATH).exists()) {
				//waiting for keypair creation
			}
		} else {
			PasswordPrompt prompt = new PasswordPrompt();
			pw = prompt.reveal();
		}

		try{
			_backend = new EncryptionBackend(pw);
		} catch(NoSuchAlgorithmException | NoSuchPaddingException e) {
			new utils.ErrorDialog(e);
			e.printStackTrace();
			System.exit(1);
		}
		//ready pw variable for garbage collection to have the plain text 
		//password cleared from RAM as soon as possible
		pw = null; 
	}
	
	public void encryptFile(File in, File out) {
		if(in.isDirectory()) {
			//TODO: Handle properly
			//System.err.println("ERROR (cryptoTools.EncryptionAPI): The file " + in.getPath() + " is a directory.");
			//return;
			if(in.isDirectory()) {
				java.io.File tmp = new java.io.File(ConfigOptions.TMP_FOLDER + in.getName() + System.getProperty("file.separator"));
				tmp.mkdir();
				java.io.File[] files = in.listFiles();
				for(java.io.File f: files) {
					if(!f.isDirectory()) {
						encryptFile(f, new java.io.File(out.getAbsolutePath() + System.getProperty("file.separator") + f.getName()));
					}
				}
			}
		} else {
			try{
				_backend.encrypt(in, out);
			} catch(IOException | InvalidKeyException e) {
				new utils.ErrorDialog(e);
				e.printStackTrace();
			}
		}
	}
	
	public void decryptFile(File in, File out) {
		try{
			_backend.decrypt(in, out);
		} catch(IOException | InvalidKeyException e) {
			new utils.ErrorDialog(e);
			e.printStackTrace();
		}
	}
}
