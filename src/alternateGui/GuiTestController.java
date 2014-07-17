package alternateGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.SwingUtilities;

import cryptoTools.EncryptionAPI;

import driveTools.GoogleAPI;

public class GuiTestController {

	GoogleAPI driveAPI;
	public GuiTestController() {
		final UserInfoPanelController userInfCtrl = new UserInfoPanelController();
		StatusPanelController statusCtrl = new StatusPanelController();
		ActionPanel actionPanel = new ActionPanel();
		actionPanel.refreshBtn.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				driveAPI.refreshDriveInfo();
			}
		});
		new GuiTest(statusCtrl.getView(), userInfCtrl.getView(), actionPanel);
		final EncryptionAPI cryptAPI = new EncryptionAPI();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				driveAPI = new GoogleAPI(cryptAPI, userInfCtrl);
			}
		});
	}

	public static void main(String[] args) {
		new GuiTestController();
	}
}
