package driveTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DriveFileModel extends File {
	private static final long serialVersionUID = -1893670515297466089L;

	private final String _url;
	private final String _size;
	
	public DriveFileModel(String filename, String downloadUrl, String filesize) throws IOException {
		super(filename);
		_url = downloadUrl;
		_size = filesize;
		byte[] url = downloadUrl.getBytes();
		byte[] size = filesize.getBytes();
		
		FileOutputStream fos = new FileOutputStream(this);
		fos.write(url);
		fos.write("\n".getBytes());
		fos.write(size);
		fos.close();
		
		this.setWritable(false);
	}
	
	public DriveFileModel(String filename) throws IOException {
		super(filename);
		FileReader fReader = new FileReader(this);
		BufferedReader reader = new BufferedReader(fReader);
		_url = reader.readLine();
		_size = reader.readLine();
		reader.close();
		this.setWritable(false);
	}
	
	public String getDownloadUrl() {
		return _url;
	}
	
	public String getSize() {
		return _size;
	}
}
