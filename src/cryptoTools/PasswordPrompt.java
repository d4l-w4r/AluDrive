package cryptoTools;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class PasswordPrompt extends JDialog {
	private static final long serialVersionUID = -809642856155789798L;
	
	private String _pw;
	JPasswordField pwField;
	public PasswordPrompt() {
		super();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Unlock RSA keys");
		
		this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 8, 15));
		pwField = new JPasswordField();
		pwField.setColumns(15);
		this.getContentPane().add(new JLabel("Password:"));
		this.getContentPane().add(pwField);
		
		pwField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_pw = new String(pwField.getPassword());
				dispose();
			}
		});
		//this.setMinimumSize(new Dimension(150, 100));
		this.pack();
	}
	
	public String reveal() {
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setVisible(true);
		return _pw;
	}
}
