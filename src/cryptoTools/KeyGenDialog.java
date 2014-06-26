package cryptoTools;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class KeyGenDialog extends Observable{
	private KeyGenDialogUI _ui;
	
	public KeyGenDialog(Observer o) {
		super();
		addObserver(o);
		_ui = new KeyGenDialogUI();
		_ui.okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(_ui.pass1.getPassword().length == 0) || !(_ui.pass2.getPassword().length == 0)) {
					setChanged();
					notifyObservers(_ui.pass1.getPassword());
				}
			}
		});
		
		_ui.pass2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!(_ui.pass1.getPassword().length == 0) || !(_ui.pass2.getPassword().length == 0)) {
					setChanged();
					notifyObservers(_ui.pass1.getPassword());
				}
			}
		});
		
		_ui.pass2.getDocument().addDocumentListener(new DocumentListener() {
			private final Document doc1 = _ui.pass1.getDocument();
			private final Document doc2 = _ui.pass2.getDocument();
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				try {
					if(doc1.getText(0, doc1.getLength()).equals(doc2.getText(0, doc2.getLength()))) {
						_ui.okButton.setEnabled(true);
						_ui.pass2.setBackground(Color.white);
					} else {
						_ui.okButton.setEnabled(false);
						_ui.pass2.setBackground(new Color(225, 50, 50));
					}
					
				} catch(BadLocationException e) {
					_ui.okButton.setEnabled(false);
					_ui.pass2.setBackground(new Color(225, 50, 50));
				}
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try {
					if(doc1.getText(0, doc1.getLength()).equals(doc2.getText(0, doc2.getLength()))) {
						_ui.okButton.setEnabled(true);
						_ui.pass2.setBackground(Color.white);
					} else {
						_ui.okButton.setEnabled(false);
						_ui.pass2.setBackground(new Color(225, 50, 50));
					}
					
				} catch(BadLocationException e) {
					_ui.okButton.setEnabled(false);
					_ui.pass2.setBackground(new Color(225, 50, 50));
				}
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				try {
					if(doc1.getText(0, doc1.getLength()).equals(doc2.getText(0, doc2.getLength()))) {
						_ui.okButton.setEnabled(true);
						_ui.pass2.setBackground(Color.white);
					} else {
						_ui.okButton.setEnabled(false);
						_ui.pass2.setBackground(new Color(225, 50, 50));
					}
					
				} catch(BadLocationException e) {
					_ui.okButton.setEnabled(false);
					_ui.pass2.setBackground(new Color(225, 50, 50));
				}
			}
		});
		
		_ui.setModalityType(ModalityType.APPLICATION_MODAL);
		_ui.setVisible(true);
	}
	
	public void kill() {
		_ui.dispose();
	}
}
