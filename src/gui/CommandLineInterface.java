package gui;
import java.io.File;

import utils.MimeTypes;

import cryptoTools.EncryptionAPI;
import driveTools.GoogleAPI;
import driveTools.MetaData;


public class CommandLineInterface {
	
	public static void main(String[] args) {
		EncryptionAPI cryptApi = new EncryptionAPI();
		GoogleAPI driveApi = new GoogleAPI(cryptApi);
		
		if(!(args.length < 2)) {
			switch (args[0]) {
			case "encrypt":
				if(args.length < 3) {
					System.err.println("Please also provide a target for the encrypted output");
				} else {
					File src = new File(args[1]);
					File crypt = new File(args[2]);
					
					cryptApi.encryptFile(src, crypt);
					driveApi.upload(crypt, new MetaData(src.getName(), MimeTypes.getType(src.getAbsolutePath().replaceAll("^.*\\.(.*)$", ".$1"))));
				}
				break;
			case "decrypt":
				if(args.length < 3) {
					cryptApi.decryptFile(new File(args[1]), new File("/dev/stdout"));
				} else {
					cryptApi.decryptFile(new File(args[1]), new File(args[2]));
				}
			default:
				break;
			}
		} else {
			System.err.println("Invalid usage.\n Use:\tcryptTest {encrypt/decrypt} /path/to/infile /path/to/outfile\n\noutfile is optional. If no target given, input file will be overwritten.");
		}
	}
}