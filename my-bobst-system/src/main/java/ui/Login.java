package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.Admin;
import model.User;
import service.UserService;


public class Login {
	private JFrame jf = new JFrame();
	private Container c = jf.getContentPane();
	private JPanel topPanel = new JPanel(); //top
	private JLabel userLabel = new JLabel("Username: ");
	private JLabel passwordLabel = new JLabel("Password: ");
	private JLabel titleLabel = new JLabel("Welcome");
	
	private ImageIcon image = new ImageIcon("src/main/java/images/R.png");
	private JLabel logo = new JLabel();//logo
	
	private JTextField userText = new JTextField();// username input
	private JPasswordField passwordText = new JPasswordField();// password input
	private JButton loginBtn = new JButton("Login");// login
	private JButton registerBtn = new JButton("Register");// register
	
	private ButtonGroup btnGroup = new ButtonGroup();
	private JRadioButton jr1 = new JRadioButton("User",true);
	private JRadioButton jr2 = new JRadioButton("Admin",false);
	
	private Font userAndPasswdFont = new Font("TimesRoman",Font.BOLD,15);
	
	private String userName = "";
	private String userPassword = "";
	
	
	public Login() {
		init();
		
		jf.setVisible(true);
	}
	
	private void init(){
		c.setLayout(null);
		jf.setSize(400,300);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		
		// top
		topPanel.setLayout(null);
		topPanel.setBackground(Color.GRAY);
		topPanel.setSize(400,100);
		
		// title
		titleLabel.setFont(new Font("TimesRoman",Font.BOLD,20));
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setBounds(160,0,400,100);
		topPanel.add(titleLabel);
		
		// add picture
		image.setImage(image.getImage().getScaledInstance(55, 55, Image.SCALE_DEFAULT));
		logo.setIcon(image);
		logo.setBounds(50,20,55,55);
		topPanel.add(logo);
		

		userLabel.setBounds(60,20,200,200);
		userLabel.setFont(userAndPasswdFont);
		passwordLabel.setBounds(60,60,200,200);
		passwordLabel.setFont(userAndPasswdFont);
		userText.setColumns(9);
		userText.setBounds(140,110,150,20);
		passwordText.setColumns(11);
		passwordText.setBounds(140, 150, 150, 20);

		loginBtn.setBounds(190,220,100,30);
		registerBtn.setBounds(70,220,100,30);
		
		
		
		/*
		 * user and admin
		 */
		jr1.setBounds(100,180,100,15);
		jr2.setBounds(200,180,100,15);
		btnGroup.add(jr1);
		btnGroup.add(jr2);
		
		
		c.add(topPanel);
		c.add(userLabel);
		c.add(passwordLabel);
		c.add(userText);
		c.add(passwordText);
		c.add(registerBtn);
		c.add(loginBtn);
		c.add(jr1);
		c.add(jr2);
		
		createBtnListener();
	}
	
	private void createBtnListener() {

		/**
		 * register listener
		 */

		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterDialog(jf, "Welcome to register");
			}
		});
		
		/**
		 * for login botton
		 */
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userName = userText.getText();
				userPassword = String.valueOf(passwordText.getPassword());
				
				UserService userService = new UserService();
				
				if(jr1.isSelected()) {
					// user login
					User user = userService.login(userName,userPassword);
					if (user!=null) {
						System.out.println("Success");
						jf.dispose();
						new UserView(user);
					}else {
						createDialog(jf);
					}
				}else {
					// admin login
					Admin admin = userService.adminLogin(userName,userPassword);
					if(admin!=null) {
						System.out.println("Success");
						jf.dispose();
						new AdminView(admin);
					}else {
						createDialog(jf);
					}
				}
			}
		});
		
	}
	
	
	private void  createDialog(JFrame frame) {
		JDialog msgDialog = new JDialog(frame, "Fail", true);
		JLabel msgLabel = new JLabel();
		Container con = msgDialog.getContentPane();
		
		msgDialog.setSize(250,150);
		msgDialog.setLocationRelativeTo(null);
		
		msgLabel.setText("Username or Password error");
		msgLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		
		con.add(msgLabel,BorderLayout.WEST);
		
		msgDialog.setVisible(true);
	}
}
