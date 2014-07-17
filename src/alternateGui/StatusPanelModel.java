package alternateGui;

import java.util.Observable;
import java.util.Observer;

public class StatusPanelModel extends Observable{

	private String statusIconPath;
	private int status; //for easy debugging
	
	public StatusPanelModel(Observer o, int initStatus) {
		super();
		this.addObserver(o);
		status = initStatus;
		updateStatus(status);
	}
	
	public void updateStatus(int statusCode) {
		status = statusCode;
		statusIconPath = selectStatusIcon(statusCode);
		this.setChanged();
		this.notifyObservers(statusIconPath);
	}
	
	private String selectStatusIcon(int statusCode) {
		/*
		 * statusCode		status
		 * ==========		======
		 * 0				connection established
		 * 1				operation pending (upload/download/sync in progress
		 * 2				drive error occurred
		 * 3				encryption running //TODO: implement + design icons
		 * 4				encryption error //TODO: implement + design icons
		 */
		
		switch (statusCode) {
		case 0:
			return GUIConstants.iconPack + "status/22/connection.png";
		case 1:
			return GUIConstants.iconPack + "status/22/operation-pending.png";
		case 2:
			return GUIConstants.iconPack + "status/22/sync_error.png";
		default:
			System.err.println("Unregocnized statusCode: " + statusCode + ".");
			return null;
		}
	}
}
