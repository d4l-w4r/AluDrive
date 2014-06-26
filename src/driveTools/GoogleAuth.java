package driveTools;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import utils.ConfigOptions;
import utils.ErrorDialog;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

import cryptoTools.EncryptionAPI;

public class GoogleAuth {

	private static String CLIENT_ID = "448650047220-m72n2idc18dcv7p2hpdevkjpnkhfhnf3.apps.googleusercontent.com";
	private static String CLIENT_SECRET = "WkUlMoiFz5_foJYxzes-3--s";
	private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	private final GoogleCredential _cred;
	private final EncryptionAPI _crypt;
	
	//utils
	private HttpTransport httpTransport = new NetHttpTransport();
    private JsonFactory jsonFactory = new JacksonFactory();
	
	public GoogleAuth(EncryptionAPI crypt) {
		_crypt = crypt;
		if(!new java.io.File(ConfigOptions.CRED_STORE_PATH).exists()) {
			_cred = makeCredential();
		} else {
			_cred = readCredential();
		}
	}
	
	private GoogleCredential makeCredential() {
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
		        .setAccessType("online")
		        .setApprovalPrompt("auto").build();
		    
		    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
		    
		    System.out.println("Please type in your authorization code");
		    WebViewTrial browser = new WebViewTrial();
		    browser.setVisible(true);
		    browser.loadURL(url);
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    try {
			    String code = br.readLine();
			    //TODO: Implement WebView to handle token authorization
			    //String code = null;
			    GoogleTokenResponse response;
			
				response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
				GoogleCredential cred = new GoogleCredential().setFromTokenResponse(response);
			    storeCredential(cred);
			    
			    return cred;
			} catch (IOException e) {
				new ErrorDialog(e);
				e.printStackTrace();
			}
		    
		return null;
	}
	
	private void storeCredential(GoogleCredential cred) {
			//TODO: Enable encryption for the credential store
		  java.io.File f = new java.io.File(ConfigOptions.CRED_STORE_PATH);
		  
		  byte[] token = cred.getAccessToken().getBytes();
		  byte[] expiry = cred.getExpirationTimeMilliseconds().toString().getBytes();
		  
		  try {
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(token);
			fos.write("\n".getBytes());
			fos.write(expiry);
			fos.close();
		} catch (IOException e) {
			new ErrorDialog(e);
			e.printStackTrace();
		}
	}
	
	private GoogleCredential readCredential() {
		GoogleCredential cred = new GoogleCredential();
		//TODO: Enable decryption from credStore
		  try {
			  java.io.File inf = new java.io.File(ConfigOptions.CRED_STORE_PATH);
			  FileReader fReader = new FileReader(inf);
			  BufferedReader reader = new BufferedReader(fReader);
			  String token = reader.readLine();
			  long expiry = new Long(reader.readLine());
			  cred.setAccessToken(token);
			  cred.setExpirationTimeMilliseconds(expiry);
			  reader.close();
		  } catch(Exception e) {
			  new ErrorDialog(e);
			  e.printStackTrace();
		  }
		  
		  return cred;
	}
	
	public GoogleCredential getCredential() {
		return _cred;
	}
}
