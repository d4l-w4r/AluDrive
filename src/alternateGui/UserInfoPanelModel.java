package alternateGui;

import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

public class UserInfoPanelModel extends Observable{
	private String _userName;
	private ImageIcon _userImage;
	private long _maxDriveCapacity;
	private long _usedDriveCapacity;
	
	public UserInfoPanelModel(Observer o) {
		super();
		this.addObserver(o);
		_userName = "";
		_userImage = new ImageIcon();
		_maxDriveCapacity = 0l;
		_usedDriveCapacity = 0l;
	}
	
	public void updateDriveCapacity(long maxCapacity, long usedCapacity) {
		_maxDriveCapacity = maxCapacity;
		_usedDriveCapacity = usedCapacity;
		float capacityInGB = new Float(_maxDriveCapacity / Math.pow(1024, 3));
		float capacityUsed = new Float(_usedDriveCapacity / Math.pow(1024, 3));
		float diff = (capacityUsed / capacityInGB) * 100;
		setChanged();
		notifyObservers((int) Math.round(diff));
		
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		String used = formatter.format(capacityUsed);
		String max = formatter.format(capacityInGB);
		String[] out = {used, "GB, of ", max, "GB"};
		setChanged();
		notifyObservers(out);
	}
	
	public void updateUserImage(String image) {
		_userImage = new ImageIcon(image);
		setChanged();
		notifyObservers(_userImage);
	}
	
	public void updateUserName(String user) {
		_userName = user;
		setChanged();
		notifyObservers(_userName);
	}
}
