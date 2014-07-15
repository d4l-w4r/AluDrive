package driveTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DriveFolderModel extends java.io.File {
	private final String _folderID;
	private final String _url;
	private final java.io.File _info;
	
	public DriveFolderModel(String filename, String folderID, String downloadUrl) throws IOException {
		super(filename);
		
		_url = downloadUrl;
		_folderID = folderID;
		byte[] url = downloadUrl.getBytes();
		byte[] id = folderID.getBytes();
		
		this.mkdir();
		//add a dot to _info file name to make it hidden on Linux
		_info = new java.io.File(this.getAbsolutePath() + System.getProperty("file.separator") + "." + this.getName());
		//if the system is windows execute command to set file to hidden
		if(System.getProperty("os.name").startsWith("Windows")) {
			Runtime.getRuntime().exec("attrib +H " + _info.getAbsolutePath());
		}
		
		FileOutputStream fos = new FileOutputStream(_info);
		fos.write(url);
		fos.write("\n".getBytes());
		fos.write(id);
		fos.close();
		
		this.setWritable(false);
	}
	
	/**
	 * Overloaded constructor for initializing DriveFolderModel when Model
	 * is already present in FILE_SYNC_PATH
	 * @param filename
	 * @throws IOException
	 */
	public DriveFolderModel(String filename) throws IOException
	{	
		super(filename);
		assert(new File(filename).exists());
		
		_info = new File(this.getAbsolutePath() + System.getProperty("file.separator") + "." +  this.getName());
		FileReader fReader = new FileReader(_info);
		
		BufferedReader reader = new BufferedReader(fReader);
		_url = reader.readLine();
		_folderID = reader.readLine();
		reader.close();
		this.setWritable(false);
	}
	
	public String getFolderID() {
		return _folderID;
	}
	
	public String getDownloadUrl() {
		return _url;
	}
}
