package driveTools;

import cryptoTools.EncryptionAPI;

public class GoogleAPI {

	private final GoogleAuth _auth;
	private final GoogleBackend _backend;
	
	public GoogleAPI(EncryptionAPI crypt) {
		_auth = new GoogleAuth(crypt);
		_backend = new GoogleBackend(_auth.getCredential());
	}
	
	public void upload(java.io.File src, MetaData meta) {
		_backend.upload(src, meta);
	}
	
	//TODO: implement all the other file operations
}
