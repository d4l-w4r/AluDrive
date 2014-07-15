package driveTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DriveFileModel{

//	private String _url;
//	private String _parent;
//	private String _size;
	private String _mime;
	private String _id;
	//private String _title;
	
	/**
	 * Constructor for initializing DriveFileModel when Model
	 * is already present in 
	 * @param filename
	 * @throws IOException
	 */
	public DriveFileModel(String filename) {
		File src = new File(filename);
		FileReader fReader;
		try {
			fReader = new FileReader(src);
		
			BufferedReader reader = new BufferedReader(fReader);
//			_parent = reader.readLine();
//			_url = reader.readLine();
//			_size = reader.readLine();
			_id = reader.readLine();
			_mime = reader.readLine();
			//_title = reader.readLine();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DEBUG (driveTools.DriveFileModel: 43): FileModel intialized. ID: " + _id + " MIME: " + _mime);
	}
	
	/*public String getDownloadUrl() {
		return _url;
	}
	
	public String getSize() {
		return _size;
	}
	
	public String getParent() {
		return _parent;
	}
	*/
	public String getMime() {
		return _mime;
	}
	
	public String getID() {
		return _id;
	}
	
	/*public String getTitle() {
		return _title;
	}*/
}
