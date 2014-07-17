package alternateGui;

import java.util.Observable;
import java.util.Observer;

import driveTools.GoogleBackend;

public class StatusPanelController implements Observer {

	private StatusPanelModel _model;
	private StatusPanelView _view;
	public StatusPanelController() {
		_view = new StatusPanelView();
		_model = new StatusPanelModel(_view, 0);
	}
	
	public StatusPanelView getView() {
		return _view;
	}
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
		_model.updateStatus((int) arg);
	}

}
