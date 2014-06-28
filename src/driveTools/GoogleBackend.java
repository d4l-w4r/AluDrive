package driveTools;

import java.awt.Dialog.ModalityType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

import utils.ConfigOptions;
import utils.ErrorDialog;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import cryptoTools.ProgressDialog;

public class GoogleBackend {
	
	private final Drive _service;
	
	//utils
	private HttpTransport httpTransport = new NetHttpTransport();
    private JsonFactory jsonFactory = new JacksonFactory();
	
    public GoogleBackend(GoogleCredential cred) {
		_service = new Drive.Builder(httpTransport, jsonFactory, cred).setApplicationName("AluDrive 0.1-alpha").build();
    }
    
    protected void upload(java.io.File sourceFile, MetaData meta) {
    	File body = new File();
    	body.setTitle(meta.get("title"));
    	body.setDescription(meta.get("description"));
    	body.setMimeType(meta.get("mime"));
    	
    	FileContent fcontent = new FileContent(meta.get("mime"), sourceFile);
    	try {
			File file = _service.files().insert(body, fcontent).execute();
			System.out.println("DEBUG (driveTools.GoogleBackend): Upload completed");
			System.out.println("DEBUG (driveTools.GoogleBackend): Google FileID: " + file.getId());
		} catch (IOException e) {
			new ErrorDialog(e);
			e.printStackTrace();
		}
    }
    
    protected List<File> list() {
    	List<File> result = new ArrayList<File>();
        Files.List request;
		try {
			request = _service.files().list();
	        do {
	         
	            FileList files = request.execute();
	
	            result.addAll(files.getItems());
	            request.setPageToken(files.getNextPageToken());
	         
	        } while (request.getPageToken() != null &&
	                 request.getPageToken().length() > 0);
	
	        return result;
      } catch (IOException e) {
          System.err.println("ERRROR (driveTools.GoogleBackend): " + e);
          return null;
        }

    }
    
    protected void fetchContents() {
    	final ProgressDialog pd = new ProgressDialog("Syncing Google Drive", "<html>Pulling header files from your Google Drive.<br>Depending on your connection, this can take a while</html>");
		
    	SwingWorker<Void, Void> wk = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				
					List<File> fileList = list();
					for (File f: fileList) {
						try {
							if(!new java.io.File(ConfigOptions.FILE_SYNC_PATH + f.getTitle()).exists()) {
								DriveFileModel fileRep = new DriveFileModel(ConfigOptions.FILE_SYNC_PATH + f.getTitle(), f.getDownloadUrl(), f.getFileSize().toString());
								System.out.println("DEBUG (driveTools.GoogleBackend): Successfully created header file at: " + fileRep.getPath());
							}
						} catch(IOException e) {
							System.err.println("ERROR (driveTools.GoogleBackend): Failed to create header file at: " + ConfigOptions.FILE_SYNC_PATH + f.getTitle() + "\n" + e.getMessage());
						}
					}
				
				return null;
			}
			
		};
		wk.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if(arg0.getNewValue() == StateValue.STARTED) {
					pd.setModalityType(ModalityType.APPLICATION_MODAL);
					pd.setVisible(true);
				} else {
					pd.dispose();
				}
			}
		});
		wk.execute();
    }
}
    //TODO: Implement all the other file operations

