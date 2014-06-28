package driveTools;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import utils.ConfigOptions;
import utils.ErrorDialog;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
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

	// utils
	private HttpTransport httpTransport = new NetHttpTransport();
	private JsonFactory jsonFactory = new JacksonFactory();

	public GoogleAuth(EncryptionAPI crypt) {
		_crypt = crypt;
		if (!new java.io.File(ConfigOptions.CRED_STORE_PATH).exists()) {
			_cred = makeCredential();
		} else {
			_cred = readCredential();
		}
	}

	private GoogleCredential makeCredential() {

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
				Arrays.asList(DriveScopes.DRIVE)).setAccessType("offline")
				.setApprovalPrompt("force").build();
		
		String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI)
				.build();
		
		System.out.println("Please type in your authorization code");
		WebViewTrial browser = new WebViewTrial();
		browser.setVisible(true);
		browser.loadURL(url);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String code = br.readLine();
			// TODO: Implement WebView to handle token authorization
			// String code = null;
			GoogleTokenResponse response;
			
			response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
			System.out.println("DEBUG (driveTools.GoogleAuth): Refresh Token is: " + response.getRefreshToken());
			
			//make temporary Credential to store access and refresh token
			Credential credential = flow.createAndStoreCredential(response, CLIENT_ID);
			//create google credential with access and refresh token from temporary credential
			//GoogleCredential cred = new GoogleCredential().setAccessToken(credential.getAccessToken()).setRefreshToken(credential.getRefreshToken()).setFromTokenResponse(response);
			GoogleCredential cred = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
			cred.setAccessToken(credential.getAccessToken());
			cred.setRefreshToken(credential.getRefreshToken());
			
			storeCredential(cred);

			return cred;
		} catch (IOException e) {
			new ErrorDialog(e);
			e.printStackTrace();
		}

		return null;
	}

	private void storeCredential(GoogleCredential cred) {
		try {
			java.io.File f = new java.io.File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "tmp.cred");
			byte[] access= cred.getAccessToken().getBytes();
			byte[] refresh = cred.getRefreshToken().toString()
					.getBytes();
			
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(access);
			fos.write("\n".getBytes());
			fos.write(refresh);
			fos.close();

			_crypt.encryptFile(
					f,
					new java.io.File(ConfigOptions.CRED_STORE_PATH));
			f.delete();
		} catch (IOException e) {
			new ErrorDialog(e);
			e.printStackTrace();
		}
	}

	private GoogleCredential readCredential() {
		GoogleCredential cred = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
		try {
			java.io.File tmp = new java.io.File(ConfigOptions.TMP_FOLDER + "tmp.cred");
			_crypt.decryptFile(new java.io.File(ConfigOptions.CRED_STORE_PATH), tmp);
			
			FileReader fReader = new FileReader(tmp);
			BufferedReader reader = new BufferedReader(fReader);
			String access = reader.readLine();
			String refresh = reader.readLine();
			
			cred.setAccessToken(access);
			cred.setRefreshToken(refresh);
			reader.close();
			tmp.delete();
		} catch (Exception e) {
			new ErrorDialog(e);
			e.printStackTrace();
		}

		return cred;
	}
	
	public boolean refreshAccessToken() throws IOException {
		GoogleCredential tmp = readCredential();
		String refresh = tmp.getRefreshToken();
		GoogleCredential cred = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
		cred.setRefreshToken(refresh);
		boolean response = cred.refreshToken();
		storeCredential(cred);
		return response;
	}
	
	public GoogleCredential getCredential() {
		return _cred;
	}
}
