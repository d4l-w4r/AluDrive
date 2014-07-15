package driveTools;

import java.io.IOException;

import javax.swing.JProgressBar;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpHeaders;


public class MyProgressListener implements MediaHttpUploaderProgressListener {

	private final FiniteProgressDialog progress;
	
	public MyProgressListener(FiniteProgressDialog prog) {
		progress = prog;
	}
	
	
	@Override
	public void progressChanged(MediaHttpUploader arg0) throws IOException {
		// TODO Auto-generated method stub
		switch (arg0.getUploadState()) {
		case INITIATION_STARTED:
			System.out.println("DEBUG (driveTools.MyProgressListener): Initiation started");
			break;
		case INITIATION_COMPLETE:
			System.out.println("DEBUG (driveTools.MyProgressListener): Initiation complete");
			break;
		case MEDIA_IN_PROGRESS:
			progress.updateProgress(arg0.getProgress());
			//System.out.println("DEBUG (driveTools.MyProgressListener): " + arg0.getProgress());
			
			break;
		case MEDIA_COMPLETE:
			progress.updateProgress(1.0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("DEBUG (driveTools.MyProgressListener): Upload complete");
			progress.dispose();
			break;
		case NOT_STARTED:
			System.out.println("DEBUG (driveTools.MyProgressListener): Not Started");
		}
	}

}
