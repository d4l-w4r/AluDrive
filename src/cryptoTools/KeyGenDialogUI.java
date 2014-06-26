package cryptoTools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class KeyGenDialogUI extends JDialog{
	private static final long serialVersionUID = 5765054488801286377L;

	public JButton okButton;
	public JPasswordField pass1;
	public JPasswordField pass2;
	
	public KeyGenDialogUI() {
		super();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Set up RSA key pair");
		JPanel content = new JPanel();
		this.getContentPane().add(content);
		content.setLayout(new BorderLayout(10, 25));
		
		JPanel headerField = new JPanel();
		headerField.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		headerField.add(new JLabel("Please choose a strong password to encrypt your private key"));
		
		JPanel pwField = new JPanel();
		pwField.setLayout(new BoxLayout(pwField, BoxLayout.PAGE_AXIS));
		pass1 = new JPasswordField(15);
		pass2 = new JPasswordField(15);
		pass1.setMargin(new Insets(5, 5, 5, 5));
		pass2.setMargin(new Insets(5, 5, 5, 5));
		pwField.add(Box.createVerticalStrut(10));
		pwField.add(new JLabel("Enter your password:"));
		pwField.add(Box.createVerticalStrut(5));
		pwField.add(pass1);
		pwField.add(Box.createVerticalStrut(20));
		pwField.add(new JLabel("Retype your password:"));
		pwField.add(Box.createVerticalStrut(5));
		pwField.add(pass2);
		pwField.add(Box.createVerticalStrut(10));
		
		JPanel buttonField = new JPanel();
		buttonField.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		okButton = new JButton("OK");
		buttonField.add(okButton);
		
		content.add(headerField, BorderLayout.PAGE_START);
		content.add(pwField, BorderLayout.CENTER);
		content.add(buttonField, BorderLayout.PAGE_END);
		content.add(Box.createRigidArea(new Dimension(30, 0)), BorderLayout.LINE_START);
		content.add(Box.createRigidArea(new Dimension(30, 0)), BorderLayout.LINE_END);

		this.pack();
	}
}
