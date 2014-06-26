import java.io.File;

import javax.swing.JButton;

import org.mindrot.jbcrypt.BCrypt;

import cryptoTools.EncryptionAPI;


public class CLI {
	
	public static final String homeDir = "/home/daniel/Desktop/encryptionTest/";

	public static void main(String[] args) {
		EncryptionAPI api = new EncryptionAPI();
		
		if(!(args.length < 2)) {
			switch (args[0]) {
			case "encrypt":
				if(args.length < 3) {
					api.encryptFile(new File(args[1]), new File(args[1]));
				} else {
					api.encryptFile(new File(args[1]), new File(args[2]));
				}
				break;
			case "decrypt":
				if(args.length < 3) {
					//api.decryptFile(new File(args[1]), new File(args[1]));
					api.decryptFile(new File(args[1]), new File("/dev/stdout"));
				} else {
					api.decryptFile(new File(args[1]), new File(args[2]));
				}
			default:
				break;
			}
		} else {
			System.err.println("Invalid usage.\n Use:\tcryptTest {encrypt/decrypt} /path/to/infile /path/to/outfile\n\noutfile is optional. If no target given, input file will be overwritten.");
		}
	}
}