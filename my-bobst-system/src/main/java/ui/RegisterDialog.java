package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Register;
import service.RegistService;
import utils.Tools;

/**
 *  name.len > 2
 *  password.len > 5
 */
public class RegisterDialog {
	private RegistService registService = new RegistService();
	private Register register = null;
	private JDialog registerView;
	private Container c;
	private JFrame jf;


	private JLabel registerName = new JLabel("Name:");
	private JLabel registerCode = new JLabel("Username:");
	private JLabel registerPassword = new JLabel("Password:");
	private JLabel registerPassword2 = new JLabel("Password*2:");
	private JLabel registerAdminCode = new JLabel("Admin key:");
	private JLabel adminCodeNotice = new JLabel("Admin key is for admin register...");
	private JLabel registMold = new JLabel("Register type:");


	private JTextField nameField = new JTextField();
	private JTextField codeField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JPasswordField passwordField2 = new JPasswordField();
	private JTextField adminCodeField = new JTextField();
	private JComboBox<String> registMoldField = new JComboBox<String>();
	private String[] registMoldList = {"User","Admin"};
	private ComboBoxModel<String> model = new DefaultComboBoxModel<String>(registMoldList);
	

	private JButton registBtn = new JButton("Register");
	private JButton resetBtn = new JButton("Reset");
	
	public RegisterDialog(JFrame jf, String title) {
		this.jf = jf;
		init(title);
		registerView.setVisible(true);
	}
	

	private void init(String title) {

		registerView = new JDialog(jf, title, true);
		c = registerView.getContentPane();
		c.setLayout(null);
		registerView.setSize(500, 300);
		registerView.setLocationRelativeTo(null);
		

		registerName.setBounds(120, 20, 100, 30);
		registerCode.setBounds(120, 50, 100, 30);
		registerPassword.setBounds(120,80, 100, 30);
		registerPassword2.setBounds(120, 110, 100, 30);
		registerAdminCode.setBounds(120, 140, 100, 30);
		adminCodeNotice.setBounds(120, 160, 400, 30);
		adminCodeNotice.setForeground(Color.RED);;
		registMold.setBounds(120, 190, 100, 30);
		
		nameField.setBounds(200, 22, 200, 25);
		codeField.setBounds(200, 52, 200, 25);
		passwordField.setBounds(200, 82, 200, 25);
		passwordField2.setBounds(200, 112, 200, 25);
		adminCodeField.setBounds(200, 142, 200, 25);
		registMoldField.setBounds(200, 192, 80, 25);
		
		registBtn.setBounds(180, 230, 100, 25);
		resetBtn.setBounds(260, 230, 100, 25);
		
		registMoldField.setModel(model);
		
		c.add(registerName);
		c.add(registerCode);
		c.add(registerPassword);
		c.add(registerPassword2);
		c.add(registerAdminCode);
		c.add(registMold);
		c.add(adminCodeNotice);
		c.add(nameField);
		c.add(codeField);
		c.add(passwordField);
		c.add(passwordField2);
		c.add(adminCodeField);
		c.add(registMoldField);
		c.add(registBtn);
		c.add(resetBtn);


		createListener();
	}

	private void createListener() {
		registBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Register...");
				String msg = "";
				// check two password
				if (passwordField.getText().equals(passwordField2.getText())) {
					register = new Register(nameField.getText(),codeField.getText(),passwordField.getText(),
							adminCodeField.getText(),registMoldField.getSelectedItem().toString());
					msg = registService.regist(register);
				}else {
					msg = "Password does not match";
					passwordField.setText(""); // clear password
					passwordField2.setText("");
				}
				Tools.createMsgDialog(jf, msg);
			}
		});
		

		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				codeField.setText("");
				passwordField.setText("");
				passwordField2.setText("");
				adminCodeField.setText("");
				registMoldField.setSelectedIndex(0);
			}
		});
	}
}
