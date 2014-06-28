package startup;

import utils.ConfigOptions;
import utils.MimeTypes;
import cryptoTools.EncryptionAPI;
import driveTools.GoogleAPI;
import driveTools.MetaData;

public class MainController {
	private EncryptionAPI _cryptAPI;
	private GoogleAPI _driveAPI;
	
	public MainController() {
		_cryptAPI = new EncryptionAPI();
		_driveAPI = new GoogleAPI(_cryptAPI);
	}
	
	public void pushToDrive(java.io.File f) {
		java.io.File tmp = new java.io.File(ConfigOptions.TMP_FOLDER + f.getName());
		_cryptAPI.encryptFile(f, tmp);
		_driveAPI.upload(tmp, new MetaData(f.getName(), MimeTypes.getType(f.getAbsolutePath().replaceAll("^.*\\.(.*)$", ".$1"))));
	}
}
