package driveTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DriveFileModel extends File {
	private static final long serialVersionUID = -1893670515297466089L;

	private final String _url;
	private String _parent;
	private final String _size;
	
	/**
	 * Constructor for initializing DriveFileModel when a new file is created
	 * 
	 * @param filename
	 * @param downloadUrl
	 * @param filesize
	 * @throws IOException
	 */
	public DriveFileModel(String filename, String downloadUrl, String filesize, String parent) {
		super(filename);
		_url = downloadUrl;
		_size = filesize;
		_parent = parent;
		byte[] url = downloadUrl.getBytes();
		byte[] size = filesize.getBytes();
		try {
			FileOutputStream fos = new FileOutputStream(this);
			if(!_parent.equals("")) {
				fos.write(_parent.getBytes());
				fos.write("\n".getBytes());
			} else {
				fos.write("None".getBytes());
				fos.write("\n".getBytes());
			}
			fos.write(url);
			fos.write("\n".getBytes());
			fos.write(size);
			fos.close();
			
			this.setWritable(false);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Overloaded constructor for initializing DriveFileModel when Model
	 * is already present in 
	 * @param filename
	 * @throws IOException
	 */
	public DriveFileModel(String filename) {
		super(filename);
		assert(new File(filename).exists());
		String url = null;
		String size = null;
		try {
			FileReader fReader = new FileReader(this);
			BufferedReader reader = new BufferedReader(fReader);
			_parent = reader.readLine();
			url = reader.readLine();
			size = reader.readLine();
			reader.close();
			this.setWritable(false);
		} catch(IOException e) {
			e.printStackTrace();
		}
		_url = url;
		_size = size;
	}
	
	public String getDownloadUrl() {
		return _url;
	}
	
	public String getSize() {
		return _size;
	}
	
	public String getParent() {
		return _parent;
	}
}
