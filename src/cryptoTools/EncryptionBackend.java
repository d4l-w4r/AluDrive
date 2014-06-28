package cryptoTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import utils.ConfigOptions;

public class EncryptionBackend {
	
	private final byte[] _aesKey;
	private final SecretKeySpec _aesKeySpec;
	private Cipher pkCipher, aesCipher;
	private final char[] _passphrase;
	
	public EncryptionBackend(String passphrase) throws NoSuchAlgorithmException, NoSuchPaddingException{

		pkCipher = Cipher.getInstance("RSA");
		aesCipher = Cipher.getInstance("AES");
		_passphrase = passphrase.toCharArray();

		if(!new File(ConfigOptions.AES_KEY_PATH).exists()) {
			HashMap<String, Object> keySpecPair = new HashMap<>();
			try{
				keySpecPair = makeKey();
			} catch(NoSuchAlgorithmException e) {
				new utils.ErrorDialog(e);
				e.printStackTrace();
				System.exit(1);
			}
			_aesKey = (byte[]) keySpecPair.get("key");
			_aesKeySpec = (SecretKeySpec) keySpecPair.get("spec");
			
			try{
				saveKey(new File(ConfigOptions.AES_KEY_PATH));
			} catch(GeneralSecurityException | IOException e) {
				new utils.ErrorDialog(e);
				e.printStackTrace();
			}
		}
		else {
			HashMap<String, Object> keySpecPair = new HashMap<>();
			try{
				keySpecPair = this.loadKey(new File(ConfigOptions.AES_KEY_PATH));
			} catch(GeneralSecurityException | IOException e) {
				new utils.ErrorDialog(e);
				e.printStackTrace();
			}
			_aesKey = (byte[]) keySpecPair.get("key");
			_aesKeySpec = (SecretKeySpec) keySpecPair.get("spec");
		}
		
		/*byte[] tmp = {};
		try{
			tmp = this.encrypt(passphrase);
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		}
		_passphrase = tmp;*/
	}
	
	/**
	 * Creates a new AES key
	 */
	private HashMap<String, Object> makeKey() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(new Integer(ConfigOptions.AES_KEY_SIZE));
		SecretKey key = kgen.generateKey();
		byte[] aesKey = key.getEncoded();
		SecretKeySpec aesKeySpec = new SecretKeySpec(aesKey, "AES");
		
		HashMap<String, Object> keyStore = new HashMap<>();
		keyStore.put("key", (byte[]) aesKey);
		keyStore.put("spec", (SecretKeySpec) aesKeySpec);
		return keyStore;
	}

	/**
	 * Decrypts an AES key from a file using an RSA private key
	 */
	private HashMap<String, Object> loadKey(File in)
			throws GeneralSecurityException, IOException {
		PrivateKey pk = this.decryptPrivKey(new File(ConfigOptions.PRIVATE_KEY_PATH));

		// read AES key
		pkCipher.init(Cipher.DECRYPT_MODE, pk);
		byte[] aesKey = new byte[new Integer(ConfigOptions.AES_KEY_SIZE) / 8];
		CipherInputStream is = new CipherInputStream(new FileInputStream(in),
				pkCipher);
		is.read(aesKey);
		is.close();
		SecretKeySpec aesKeySpec = new SecretKeySpec(aesKey, "AES");
		
		HashMap<String, Object> keyStore = new HashMap<>();
		keyStore.put("key", (byte[]) aesKey);
		keyStore.put("spec", (SecretKeySpec) aesKeySpec);
		return keyStore;
	}

	/**
	 * Encrypts the AES key to a file using an RSA public key
	 */
	private void saveKey(File out) throws IOException,
			GeneralSecurityException {
		File pubKey = new File(ConfigOptions.PUBLIC_KEY_PATH);
		// read public key to be used to encrypt the AES key
		byte[] encodedKey = new byte[(int) pubKey.length()];
		new FileInputStream(pubKey).read(encodedKey);

		// create public key
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pk = kf.generatePublic(publicKeySpec);

		// write AES key
		pkCipher.init(Cipher.ENCRYPT_MODE, pk);
		CipherOutputStream os = new CipherOutputStream(
				new FileOutputStream(out), pkCipher);
		os.write(_aesKey);
		os.close();
	}
	
	private PrivateKey decryptPrivKey(File key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			InvalidKeySpecException, IOException {
		// load the saved key
		int fileContents = (int) key.length();
		byte[] data = new byte[fileContents];
		FileInputStream reader = new FileInputStream(key);
		reader.read(data, 0, fileContents);
		reader.close();
		EncryptedPrivateKeyInfo encInfo = new EncryptedPrivateKeyInfo(data);
		// begin decryption of the key
		PBEKeySpec pbeKeySpec = new PBEKeySpec(_passphrase);
		Cipher cipher = Cipher.getInstance(encInfo.getAlgName());

		SecretKeyFactory secretKeyFac = SecretKeyFactory.getInstance(ConfigOptions.PBE_MODE);
		cipher.init(Cipher.DECRYPT_MODE,
				secretKeyFac.generateSecret(pbeKeySpec),
				encInfo.getAlgParameters());

		PKCS8EncodedKeySpec pkcs8KeySpec = encInfo.getKeySpec(cipher);
		KeyFactory keyFact = KeyFactory.getInstance("RSA");
		PrivateKey retrievedKey = keyFact.generatePrivate(pkcs8KeySpec);

		return retrievedKey;
	}
	
	/**
	 * Encrypts and then copies the contents of a given file.
	 */
	protected void encrypt(File in, File out) throws IOException,
			InvalidKeyException {
		aesCipher.init(Cipher.ENCRYPT_MODE, _aesKeySpec);

		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(
				new FileOutputStream(out), aesCipher);
		copy(is, os);
		os.close();
	}
	
	/**
	 * Decrypts and then copies the contents of a given file.
	 */
	protected void decrypt(File in, File out) throws IOException,
			InvalidKeyException {
		aesCipher.init(Cipher.DECRYPT_MODE, _aesKeySpec);

		CipherInputStream is = new CipherInputStream(new FileInputStream(in),
				aesCipher);
		FileOutputStream os = new FileOutputStream(out);

		copy(is, os);
		copy(is, System.out);
		is.close();
		os.close();
	}
	
	/**
	 * Copies a stream.
	 */
	private void copy(InputStream is, OutputStream os) throws IOException {
		int i;
		byte[] b = new byte[1024];
		while ((i = is.read(b)) != -1) {
			os.write(b, 0, i);
		}
	}
}
