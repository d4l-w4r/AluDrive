package driveTools;

import java.awt.Dialog.ModalityType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

import utils.ConfigOptions;
import utils.ErrorDialog;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentList;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.User.Picture;

import cryptoTools.ProgressDialog;

public class GoogleBackend extends Observable{

	private final Drive _service;

	// utils
	private HttpTransport httpTransport = new NetHttpTransport();
	private JsonFactory jsonFactory = new JacksonFactory();
	//private PythonDriveHub pythonHub = new PythonDriveHub();

	public GoogleBackend(GoogleCredential cred) {
		_service = new Drive.Builder(httpTransport, jsonFactory, cred)
				.setApplicationName("AluDrive 0.1-alpha").build();
	}

	protected InputStream getUserImage() {
		try {
			About about = _service.about().get().execute();
			String url = about.getUser().getPicture().getUrl();
			url = url.replaceAll("s64", "s128");
			System.out.println(url);
			
			HttpResponse resp =
		            _service.getRequestFactory().buildGetRequest(new GenericUrl(url))
		                .execute();
		        return resp.getContent();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String getUserName() {
		try {
			About about = _service.about().get().execute();
			return about.getUser().getDisplayName();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	protected HashMap<String, Long> getDriveCapacity() {
		HashMap<String, Long> out = new HashMap<>();
		try {
			About about = _service.about().get().execute();
			out.put("max", about.getQuotaBytesTotal());
			out.put("used", about.getQuotaBytesUsed());
			return out;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	protected void upload(java.io.File sourceFile, MetaData meta) {
		final File body = new File();
		body.setTitle(meta.get("title"));
		body.setDescription(meta.get("description"));
		body.setMimeType(meta.get("mime"));

		final FileContent fcontent = new FileContent(meta.get("mime"),
				sourceFile);
		final FiniteProgressDialog pd = new FiniteProgressDialog(
				"Uploading Data", "<html>Uploading " + meta.get("title")
						+ " to Google Drive<br>Please wait</html>");

		SwingWorker wk = new SwingWorker() {

			@Override
			protected File doInBackground() throws Exception {
				Drive.Files.Insert req = _service.files()
						.insert(body, fcontent);
				req.getMediaHttpUploader()
						.setChunkSize(MediaHttpUploader.MINIMUM_CHUNK_SIZE * 3)
						.setDirectUploadEnabled(false)
						.setProgressListener(new MyProgressListener(pd));
				File f = req.execute();
				return f;
			}

		};
		wk.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getNewValue() == StateValue.STARTED) {
					pd.setModalityType(ModalityType.MODELESS);
					pd.setVisible(true);
				} else {
					pd.dispose();
				}
			}
		});
		long start = System.nanoTime();
		wk.execute();
		try {
			File f = (File) wk.get();
			long end = System.nanoTime();
			long secs = (end - start) / 1000000000;
			System.out.println("Upload took: " + secs
					+ " seconds. Average upload speed: "
					+ (f.getFileSize() / 128 / 8) / secs + "kb/s");
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void upload(java.io.File sourceFile, MetaData meta,
			String folderID) {
		final File body = new File();
		body.setTitle(meta.get("title"));
		body.setDescription(meta.get("description"));
		body.setMimeType(meta.get("mime"));

		List<ParentReference> pList = new ArrayList<ParentReference>();

		ParentReference parent = new ParentReference();
		parent.setId(folderID);
		parent.setKind("drive#fileLink");

		pList.add(parent);
		body.setParents(pList);

		final FileContent fcontent = new FileContent(meta.get("mime"),
				sourceFile);
		final FiniteProgressDialog pd = new FiniteProgressDialog(
				"Uploading Data", "<html>Uploading " + meta.get("title")
						+ " to Google Drive<br>Please wait</html>");

		SwingWorker wk = new SwingWorker() {

			@Override
			protected File doInBackground() throws Exception {
				Drive.Files.Insert req = _service.files()
						.insert(body, fcontent);
				req.getMediaHttpUploader()
						.setChunkSize(MediaHttpUploader.MINIMUM_CHUNK_SIZE * 3)
						.setDirectUploadEnabled(false)
						.setProgressListener(new MyProgressListener(pd));
				File f = req.execute();
				return f;
			}

		};
		wk.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getNewValue() == StateValue.STARTED) {
					pd.setModalityType(ModalityType.MODELESS);
					pd.setVisible(true);
				} else {
					pd.dispose();
				}
			}
		});
		long start = System.nanoTime();
		wk.execute();
		try {
			File f = (File) wk.get();
			long end = System.nanoTime();
			long secs = (end - start) / 1000000000;
			System.out.println("Upload took: " + secs
					+ " seconds. Average upload speed: "
					+ (f.getFileSize() / 128 / 8) / secs + "kb/s");
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected InputStream download(DriveFileModel model) {
		File file;
		try {
			file = _service.files().get(model.getID()).execute();
		
			String downloadUrl = file.getDownloadUrl();
			
			if (downloadUrl.length() > 0) {
		     
		        HttpResponse resp =
		            _service.getRequestFactory().buildGetRequest(new GenericUrl(downloadUrl))
		                .execute();
		        return resp.getContent();
		        
			} else {
		      	System.out.println("DEBUG (driveTools.GoogleBackend: 194): File doesn't have any content on the store");
		      	return null;
		    } 
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	

	protected List<File> list() {
		List<File> result = new ArrayList<File>();
		Files.List request;
		try {
			request = _service.files().list().setQ("trashed=false");
		
			do {

				FileList files = request.execute();

				result.addAll(files.getItems());
				request.setPageToken(files.getNextPageToken());

			} while (request.getPageToken() != null
					&& request.getPageToken().length() > 0);
			
			return result;
		} catch (IOException e) {
			System.err.println("ERRROR (driveTools.GoogleBackend): " + e);
			return null;
		}

	}

	protected void fetchContents() {
		final ProgressDialog pd = new ProgressDialog(
				"Syncing Google Drive",
				"<html>Pulling header files from your Google Drive.<br>Depending on your connection, this can take a while</html>");

		SwingWorker wk = new SwingWorker() {
			@Override
			protected Void doInBackground() throws Exception {
				// TODO: handle drive folders
				getRemoteData();
				return null;
			}
		};

		wk.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getNewValue().equals(StateValue.STARTED)) {
					System.out.println("Process Started");
					pd.setModalityType(ModalityType.MODELESS);
					pd.setVisible(true);
				} else if (arg0.getNewValue().equals(StateValue.DONE)) {
					pd.dispose();
				}
			}
		});
		wk.execute();

	}

	protected String mkdir(MetaData meta) {
		File f = new File();
		f.setTitle(meta.get("title"));
		f.setMimeType(meta.get("mime"));
		f.setKind("drive#fileLink");

		try {
			File remote = _service.files().insert(f).execute();
			return remote.getId();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void getRemoteData() {
		//HashMap<String, ArrayList<Object>> driveContent = new HashMap<String, ArrayList<Object>>();
		List<File> fileDump = this.list();
		String dumpString = fileDump.toString();
		dumpString.replaceAll("=", ": ");
		String root = "";
		try {
			About about = _service.about().get().execute();
			root = about.getRootFolderId();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String sep = System.getProperty("line.seperator");
		String sep = "\n";
		try {
			FileOutputStream fos = new FileOutputStream(new java.io.File(ConfigOptions.PYTHON_FILE_PATH + "data.py"));
			fos.write(new String("PATH = \"" + ConfigOptions.FILE_HEADER_PATH + "\"").getBytes());
			fos.write(sep.getBytes());
			fos.write(new String("driveRoot = \"" + root + "\"").getBytes());
			fos.write(sep.getBytes());
			fos.write("false = 'false'".getBytes());
			fos.write(sep.getBytes());
			fos.write("true = 'true'".getBytes());
			fos.write(sep.getBytes());
			fos.write(new String("data = " + dumpString).getBytes());
			fos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		//pythonHub.buildLocalHeaders();
	}
	
}
// TODO: Implement all the other file operations