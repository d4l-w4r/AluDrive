package startup;
import java.io.File;

import utils.ConfigOptions;
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
			case "--encrypt":
				File src = new File(args[1]);
				File crypt = new File(ConfigOptions.TMP_FOLDER + src.getName());
				
				cryptApi.encryptFile(src, crypt);
				driveApi.upload(crypt, new MetaData(src.getName(), MimeTypes.getType(src.getAbsolutePath())));
				crypt.delete();
			
				break;
			case "--decrypt":
				if(args.length < 3) {
					cryptApi.decryptFile(new File(args[1]), new File("/dev/stdout"));
				} else {
					cryptApi.decryptFile(new File(args[1]), new File(args[2]));
				}
				break;
			case "--list":
				if(args[1].equals("local")) {
					driveApi.listLocalContents();
				} else if (args[1].equals("remote")) {
					driveApi.listRemoteContents();
				} else {
					System.out.println("ERROR (gui.CommandLineInterface): Unrecognized input. Please choose either local or remote");
				}
			default:
				break;
			}
		} else if(args.length == 1) {
			switch (args[0]) {
			case "--sync":
				driveApi.fetchDriveContents();
				break;
			case "--help":
				printHelp();
				break;
			default:
				System.out.println("ERROR (gui.CommandLineInterface): Unrecognized option: " + args[0]);
				printHelp();
			} 
		} else {
			printHelp();
		}
	}
	
	private static void printHelp() {
		String header = "\nAluDrive v.0.1-alpha\nauthor: aludrive.dev@gmail.com\n\nUsage: aludrive OPTION args\n";
		String body = "Options (mandatory):\n--help\t\t\t\t\tshow this help text\n\n--sync\t\t\t\t\tpull all header files from remote dir (not actually downloading file content!)\n\n--list [OPTION]\t\t\t\tlist all files at specified location\n\t\t\t\t\t[OPTION]={local, remote}\n\n--encrypt [IN_FILE]\t\t\tencrypts the given file and uploads it to Google Drive\n\t\t\t\t\t[IN_FILE]=/path/to/file/\n\n--decrypt [IN_FILE] ([OUT_FILE])\tdownloads specified file from GoogleDrive and decrypts it\n\t\t\t\t\t[OUT_FILE] is optional. If no file is given\n\t\t\t\t\tcontents will be printed to stdout";
		System.out.println(header + body);
	}
}