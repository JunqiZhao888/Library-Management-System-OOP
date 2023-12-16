package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Book;
import model.User;
import service.UserService;
import utils.Tools;


public class UserView{
	User user = null;
	UserService userService = new UserService();
	private JFrame userView = new JFrame("Bobst Library");
	private Container c = userView.getContentPane();
	private JPanel topPanel = new JPanel();
	private JLabel userMsg = new JLabel();
	private JLabel time = new JLabel();
	private JLabel title = new JLabel("Information");
	private JScrollPane mainPanel = new JScrollPane();
	private JTable table = new JTable();
	private JComboBox<String> findBy = new JComboBox<>();
	private String items[] = {"By name", "By author", "By Id","Get all books"};
	private JTextField input = new JTextField();
	private JButton submit = new JButton("Search");
	
	private List<Book> books = null;
	String[] tableTitle = new String[]{"Name","Author","Id","Borrow info","Location"};
	private String[][] tableData = new String[1][5];
	
	
	public UserView(User loginUser) {
		this.user = loginUser;
		init();
		userView.setVisible(true);
	}
	

	private void init() {
		c.setLayout(null);
		userView.setSize(800,500);
		userView.setLocationRelativeTo(null);
		userView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userView.setResizable(false);
		
		topPanel();
		mainPanel();
		
		createListener();
	}
	

	private void topPanel() {
		topPanel.setLayout(null);
		topPanel.setBounds(0,0,800,100);
		topPanel.setBackground(Color.GRAY);
		
		

		userMsg.setBounds(10,5,200,20);
		userMsg.setFont(new Font("TimesRoman",Font.PLAIN,15));
		userMsg.setText("Welcome:"+user.getName());
		

		ComboBoxModel<String> model = new DefaultComboBoxModel<>(items);
		findBy.setModel(model);
		findBy.setFont(new Font("TimesRoman", Font.PLAIN, 9));
		findBy.setBounds(120,40,80,25);
		

		input.setBounds(220, 40, 300, 25);

		submit.setBounds(550, 40, 80, 25);
		

		time.setBounds(600, 70, 200, 25);
		time.setFont(new Font("TimesRoman", Font.ITALIC, 11));
		time.setText("Login time:"+Tools.getTime());
		
		topPanel.add(userMsg);
		topPanel.add(findBy);
		topPanel.add(input);
		topPanel.add(submit);
		topPanel.add(time);
		c.add(topPanel);
		topPanel.setVisible(true);
	}
	

	private void mainPanel() {

		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setEnabled(false);
		table.setRowHeight(20);
		mainPanel.setBounds(45, 140, 700, 300);
		mainPanel.setViewportView(table);
		

		title.setBounds(350, 110, 100, 25);
		
		c.add(mainPanel);
		c.add(title);
		mainPanel.setVisible(true);
	}
	
	
	

	private void createListener() {

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String findMsg = "";
				findMsg = input.getText();
				

				if(findBy.getSelectedItem().equals(items[0])) {

					books = userService.findBooks(findMsg, 0);
					System.out.println("Search by name");
				}
				if(findBy.getSelectedItem().equals(items[1])) {
					books = userService.findBooks(findMsg, 1);
					System.out.println("Search by author");
				}
				if(findBy.getSelectedItem().equals(items[2])) {
					books = userService.findBooks(findMsg, 2);
					System.out.println("Search by Id");
				}
				if (findBy.getSelectedItem().equals(items[3])) {
					books = userService.findBooks(findMsg, 3);
					System.out.println("Get all books");
				}
				
				if(books != null) {
					tableData = new String[books.size()][5];
					for(int i=0; i<books.size(); i++) {
						Book book = books.get(i);
						if(book != null) {
							tableData[i][0] = book.getBookname();
							tableData[i][1] = book.getAuthor();
							tableData[i][2] = book.getNum().toString();
							tableData[i][3] = book.getBorrow();
							tableData[i][4] = book.getLocation();
						}
					}
				}
				TableModel data = new DefaultTableModel(tableData,tableTitle);
				table.setModel(data);
				input.setText("");
			}
		});
	}
	
}
