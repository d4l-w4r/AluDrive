package driveTools;

import java.io.IOException;

import org.python.util.PythonInterpreter;

import utils.ConfigOptions;

public class PythonDriveHub {

	private final PythonInterpreter _interpreter;

	public PythonDriveHub() {
		_interpreter = new PythonInterpreter();
		if (!new java.io.File(ConfigOptions.PYTHON_FILE_PATH + "__init__.py")
				.exists()) {
			try {
				new java.io.File(ConfigOptions.PYTHON_FILE_PATH + "__init__.py")
						.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			validateEnvironmentSetup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateEnvironmentSetup() throws Exception {
		_interpreter.exec("import os");
		String line1 = "import sys";
		String line2_1 = "sys.path.append(\"";
		String line2_2 = "\")";
		_interpreter.exec(line1 + "\n" + line2_1
				+ ConfigOptions.PYTHON_FILE_PATH + line2_2);
	}

	public boolean buildLocalHeaders() {
		_interpreter.execfile(ConfigOptions.PYTHON_FILE_PATH + "fileLoader.py");
		return true;
	}
}
