package cryptoTools;

import java.awt.Dialog.ModalityType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Observable;
import java.util.Observer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

import utils.ConfigOptions;

public class KeyGen implements Observer {
	
	private String pw;
	
	public KeyGen() {
		super();
		new KeyGenDialog(this);
	}

	public void makeKeyPair(String passphrase) throws NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidParameterSpecException, IOException {
		// generate key pair

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		// keyPairGenerator.initialize(2048);
		keyPairGenerator.initialize(4096);
		KeyPair keyPair = keyPairGenerator.genKeyPair();

		// extract the encoded private key, this is an unencrypted PKCS#8
		// private key
		byte[] encodedprivkey = keyPair.getPrivate().getEncoded();
		byte[] encodedPubKey = keyPair.getPublic().getEncoded();
		FileOutputStream out = new FileOutputStream(ConfigOptions.PUBLIC_KEY_PATH);
		out.write(encodedPubKey);
		out.close();
		// int count = 20;// hash iteration count TODO: Experiment for optimal
		// value
		int count = 20000;
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[8];
		random.nextBytes(salt);

		// Create PBE parameter set
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
		PBEKeySpec pbeKeySpec = new PBEKeySpec(passphrase.toCharArray());
		SecretKeyFactory keyFac = SecretKeyFactory.getInstance(ConfigOptions.PBE_MODE);
		SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

		Cipher pbeCipher = Cipher.getInstance(ConfigOptions.PBE_MODE);

		// Initialize PBE Cipher with key and parameters
		pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

		// Encrypt the encoded Private Key with the PBE key
		byte[] ciphertext = pbeCipher.doFinal(encodedprivkey);

		// Now construct PKCS #8 EncryptedPrivateKeyInfo object
		AlgorithmParameters algparms = AlgorithmParameters.getInstance(ConfigOptions.PBE_MODE);
		algparms.init(pbeParamSpec);
		EncryptedPrivateKeyInfo encinfo = new EncryptedPrivateKeyInfo(algparms,
				ciphertext);

		// Saving the key info to file
		FileOutputStream fos = new FileOutputStream(ConfigOptions.PRIVATE_KEY_PATH);
		fos.write(encinfo.getEncoded());
		fos.close();
	}

	public String getPassphrase() {
		return pw;
	}
	
	@Override
	public void update(Observable obs, Object obj) {
		((KeyGenDialog) obs).kill();
		pw = new String((char[]) obj);

		final ProgressDialog pd = new ProgressDialog();
		SwingWorker<Void, Void> wk = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try{
					makeKeyPair(pw);
				} catch (InvalidKeyException | NoSuchAlgorithmException
						| InvalidKeySpecException | NoSuchPaddingException
						| InvalidAlgorithmParameterException
						| IllegalBlockSizeException | BadPaddingException
						| InvalidParameterSpecException | IOException  e) {
						e.printStackTrace();
						new utils.ErrorDialog(e);
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
