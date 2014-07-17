package alternateGui;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import driveTools.GoogleAPI;

public class UserInfoPanelController implements Observer{
	private UserInfoPanelView _view;
	private UserInfoPanelModel _model;
	
	public UserInfoPanelController() {
		_view = new UserInfoPanelView();
		_model = new UserInfoPanelModel(_view);
	}

	public UserInfoPanelView getView() {
		return _view;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GoogleAPI) {
			if(arg instanceof HashMap) {
				HashMap<String, Long> capacity = (HashMap<String, Long>) arg;
				_model.updateDriveCapacity(capacity.get("max"), capacity.get("used"));
			} else if(arg instanceof String) {
				String s = arg.toString();
				if(s.startsWith("/") || s.startsWith("C:")) {
					System.out.println(s);
					_model.updateUserImage(s);
				} else {
					_model.updateUserName(s);
				}
			}
		}
	}
}
