package driveTools;

import java.io.IOException;

import utils.ErrorDialog;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class GoogleBackend {
	
	private final Drive _service;
	
	//utils
	private HttpTransport httpTransport = new NetHttpTransport();
    private JsonFactory jsonFactory = new JacksonFactory();
	
    public GoogleBackend(GoogleCredential cred) {
		_service = new Drive.Builder(httpTransport, jsonFactory, cred).build();
	}
    
    protected void upload(java.io.File sourceFile, MetaData meta) {
    	File body = new File();
    	body.setTitle(meta.get("title"));
    	body.setDescription(meta.get("description"));
    	body.setMimeType(meta.get("mime"));
    	
    	FileContent fcontent = new FileContent(meta.get("mime"), sourceFile);
    	try {
			_service.files().insert(body, fcontent).execute();
		} catch (IOException e) {
			new ErrorDialog(e);
			e.printStackTrace();
		}
    }
    
    //TODO: Implement all the other file operations
}
