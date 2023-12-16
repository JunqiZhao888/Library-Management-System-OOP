package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Admin;
import model.Book;
import service.AdminService;
import utils.Tools;


public class AdminView {
	//service和admin
	AdminService adminService = new AdminService();
	private Admin admin = new Admin();
	// main gui
	private JFrame adminView = new JFrame("Bobst Library (Admin)");
	private Container c = adminView.getContentPane();
	// top components
	private JPanel topPanel = new JPanel();
	private JLabel topAdminMsg = new JLabel(); // admin info
	private JComboBox<String> topFindBy = new JComboBox<String>();
	private String items[] = {"By name", "By author", "By id","get all books"};
	private JTextField topInput = new JTextField();
	private JLabel topTime = new JLabel();
	//侧边面板和组件
	private JPanel sidePanel = new JPanel();
	private JButton sideSubmit = new JButton("Search");
	private JButton sideAddBtn = new JButton("Add");
	private JButton sideDeleteBtn = new JButton("Delete");
	private JButton sideUpdateBtn = new JButton("Update");
	private JPanel addPanel = new JPanel();
	private JPanel deletePanel = new JPanel();
	
	private JScrollPane findPanel = new JScrollPane();
	private JPanel updataPanel = new JPanel();
	private JLabel functionMsg = new JLabel();
	//添加功能组件
	private JLabel addName = new JLabel("Name");
	private JLabel addAuthor = new JLabel("Author");
	private JLabel addNumber = new JLabel("Id");
	private JLabel addLocation = new JLabel("Location");
	private JLabel addBorrow = new JLabel("Borrow Info");
	private JComboBox<String> addBorrowText = new JComboBox<String>();
	private String[] addBorrowList = {"Ready","Borrowed"};
	private JTextField addNameText = new JTextField(30);
	private JTextField addAuthorText = new JTextField(30);
	private JTextField addNumberText = new JTextField(30);
	private JTextField addLocationText = new JTextField(30);
	private JButton addCommitBtn = new JButton("Add");
	private JButton addCleanBtn = new JButton("Clean");
	private JLabel findTitle = new JLabel("Information");
	private JTable findTable = new JTable();
	String[] findTableTitle = new String[]{"Name","Author","Id","Information","Location"};

	private String[][] findTableData;;

	private JLabel deletenum = new JLabel("Enter id to delete:");
	private JTextField deletenumText = new JTextField(30); //获得图书编号
	private JButton deletenumBtn = new JButton("Delete");
	//修改功能组件
	private JLabel updateNum = new JLabel("Enter id to update:");
	private JTextField updateNumText0 = new JTextField(30);
	private JButton updateFindBtn = new JButton("Search");
	private JButton updateBtn = new JButton("Update");
	private JTextField updateNameText = new JTextField(30);
	private JTextField updateAuthorText = new JTextField(30);
	private JTextField updateNumText = new JTextField(30);
	private JTextField updateLocationText = new JTextField(30);
	private JComboBox<String> updateBorrowText = new JComboBox<String>();
	private int id;
	//实例
	private Book book = null;
	private List<Book> books = new ArrayList<Book>();
	//删除的dialog
	private JDialog bookDialog = new JDialog(adminView, "Book info", true); //展示删除图书信息的窗口
	private JButton sure = new JButton("Delete?");
	private JButton nosure = new JButton("Cancel");
	//错误信息
	private String msg = "";
	
	public AdminView() {
		init();
		adminView.setVisible(true);
	}
	
	public AdminView(Admin admin) {
		this.admin = admin;
		init();
		adminView.setVisible(true);
	}
	
	private void init() {
		c.setLayout(null);
		adminView.setSize(800, 500);
		adminView.setLocationRelativeTo(null);
		adminView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminView.setResizable(false);
		
		createTopPanel(); // top panel
		createSidePanel(); // side panel
		selectFunctionListener();
		createListener();
	}

	private void setFunctionMsg(String msg) {
		
		functionMsg.setBounds(200, 10, 700, 30);
		functionMsg.setText(msg);
		functionMsg.setFont(new Font("TimesRoman", Font.BOLD, 25));
		functionMsg.setForeground(Color.orange);
	}
	

	private void createTopPanel() {
		topPanel.setLayout(null);
		topPanel.setBounds(0,0,800,100);
		topPanel.setBackground(Color.GRAY);
		
		//显示登录用户的名字
		topAdminMsg.setBounds(10,5,200,20);
		topAdminMsg.setFont(new Font("TimesRoman",Font.PLAIN,15));
		topAdminMsg.setText("Welcome admin:"+admin.getName());
		
		//检索方式按钮
		ComboBoxModel<String> model = new DefaultComboBoxModel<>(items);
		topFindBy.setModel(model);
		topFindBy.setFont(new Font("TimesRoman", Font.PLAIN, 9));
		topFindBy.setBounds(120,40,80,25);
		
		//检索输入框
		topInput.setBounds(220, 40, 300, 25);
		
		//检索按钮
		sideSubmit.setBounds(550, 40, 80, 25);
		
		//当前时间
		topTime.setBounds(600, 70, 200, 25);
		topTime.setFont(new Font("TimesRoman", Font.ITALIC, 11));
		topTime.setText("Login Time:"+Tools.getTime());
		
		topPanel.add(topAdminMsg);
		topPanel.add(topFindBy);
		topPanel.add(topInput);
		topPanel.add(sideSubmit);
		topPanel.add(topTime);
		c.add(topPanel);
		topPanel.setVisible(true);
	}

	private void createSidePanel() {
		sidePanel.setLayout(new FlowLayout());
		sidePanel.setBounds(0, 100, 100, 400);
		sidePanel.setBackground(Color.lightGray);
		
		sidePanel.add(sideAddBtn);
		sidePanel.add(sideDeleteBtn);
		sidePanel.add(sideUpdateBtn);
		c.add(sidePanel);
		sidePanel.setVisible(true);
	}
	

	private JPanel createAddPanel() {
		addPanel.setLayout(null);
		addPanel.setBounds(100, 100, 700, 700);

		setFunctionMsg("You are adding...");

		addName.setBounds(150, 60, 100, 30);
		addAuthor.setBounds(150, 90, 100, 30);
		addNumber.setBounds(150, 120, 100, 30);
		addLocation.setBounds(150, 150, 100, 30);
		addBorrow.setBounds(150, 180, 100, 30);
		addNameText.setBounds(220, 62, 300, 25);
		addAuthorText.setBounds(220, 92, 300, 25);
		addNumberText.setBounds(220, 122, 300, 25);
		addLocationText.setBounds(220, 152, 300, 25);
		ComboBoxModel<String> model = new DefaultComboBoxModel<String>(addBorrowList);
		addBorrowText.setModel(model);
		addBorrowText.setBounds(220, 182, 80, 25);
		
		//按钮
		addCommitBtn.setBounds(280, 240, 60, 25);
		addCleanBtn.setBounds(350, 240, 80, 25);
		
		addPanel.add(functionMsg);
		addPanel.add(addName);
		addPanel.add(addAuthor);
		addPanel.add(addNumber);
		addPanel.add(addLocation);
		addPanel.add(addBorrow);
		addPanel.add(addNameText);
		addPanel.add(addAuthorText);
		addPanel.add(addNumberText);
		addPanel.add(addLocationText);
		addPanel.add(addBorrowText);
		addPanel.add(addCommitBtn);
		addPanel.add(addCleanBtn);
		return addPanel;
	}

	private JScrollPane createFindPanel() {

		setFunctionMsg("You are searching...");
		functionMsg.setBounds(300, 110, 700, 30);

		findTitle.setBounds(400, 150, 100, 25);

		findTable.getTableHeader().setReorderingAllowed(false);
		findTable.getTableHeader().setResizingAllowed(false);
		findTable.setEnabled(false);
		findTable.setRowHeight(20);
		findPanel.setBounds(150, 190, 600, 250);
		findPanel.setViewportView(findTable);
		
		c.add(functionMsg);
		c.add(findTitle);
		
		return findPanel;
	}

	private JPanel createDeletePanel() {
		deletePanel.setLayout(null);
		deletePanel.setBounds(100, 100, 700, 700);

		setFunctionMsg("You are deleting...");

		deletenum.setBounds(50, 70, 200, 30);
		deletenumText.setBounds(200, 70, 300, 25);
		deletenumBtn.setBounds(520, 70, 60, 25);
		
		deletePanel.add(functionMsg);
		deletePanel.add(deletenum);
		deletePanel.add(deletenumText);
		deletePanel.add(deletenumBtn);
		return deletePanel;
	}

	private JPanel createUpdataPanel() {
		
		updataPanel.setLayout(null);
		updataPanel.setBounds(100, 100, 700, 700);

		setFunctionMsg("You are updating...");

		updateNum.setBounds(50, 70, 200, 30);
		updateNumText0.setBounds(200, 70, 300, 25);
		updateFindBtn.setBounds(520, 70, 60, 25);
		
		updataPanel.add(functionMsg);
		updataPanel.add(updateNum);
		updataPanel.add(updateNumText0);
		updataPanel.add(updateFindBtn);
		return updataPanel;
	}
	

	private void createMainPanel(JPanel jp) {
		c.remove(findTitle);
		c.add(jp);
		jp.setVisible(true);
	}
	private void createMainPanel(JScrollPane jp) {
		c.add(jp);
		jp.setVisible(true);
	}

	private void selectFunctionListener() {

		sideAddBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				setVisibleFalse();
				createMainPanel(createAddPanel());
			}
		});
		

		sideDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				setVisibleFalse();

				createMainPanel(createDeletePanel());
			}
		});
		
		//修改功能
		sideUpdateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisibleFalse();

				createMainPanel(createUpdataPanel());
			}
		});
	}

	private void createListener() {

		sideSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisibleFalse();

				createMainPanel(createFindPanel());
				

				String findMsg = "";
				findMsg = topInput.getText();
				

				if(topFindBy.getSelectedItem().equals(items[0])) {
					books = adminService.findBooks(findMsg, 0);
					System.out.println("Search by name");
				}
				if(topFindBy.getSelectedItem().equals(items[1])) {
					books = adminService.findBooks(findMsg, 1);
					System.out.println("Search by author");
				}
				if(topFindBy.getSelectedItem().equals(items[2])) {
					books = adminService.findBooks(findMsg, 2);
					System.out.println("Search by id");
				}
				if (topFindBy.getSelectedItem().equals(items[3])) {
					books = adminService.findBooks(findMsg, 3);
					System.out.println("Get all books");
				}
				
				if(books != null) {
					findTableData = new String[books.size()][5];
					for(int i=0; i<books.size(); i++) {
						Book book = books.get(i);
						if(book != null) {
							findTableData[i][0] = book.getBookname();
							findTableData[i][1] = book.getAuthor();
							findTableData[i][2] = book.getNum().toString();
							findTableData[i][3] = book.getBorrow();
							findTableData[i][4] = book.getLocation();
						}
					}
				}

				TableModel data = new DefaultTableModel(findTableData,findTableTitle);
				findTable.setModel(data);
				topInput.setText("");
			}
		});

		addCommitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String bookname = "";
				String author = "";
				Long number = -1l;
				String location = "";
				String borrow = "";

				if (addNumberText.getText().contains(" ") || addNumberText.getText().equals("")) {
					msg = "ID cannot be empty";
				}else if(!Tools.isNumeric(addNumberText.getText())) {
					msg = "Id has to be number";
				}else {
					bookname = addNameText.getText();
					author = addAuthorText.getText();
					number = Long.parseLong(addNumberText.getText());
					location = addLocationText.getText();
					borrow = addBorrowText.getSelectedItem().toString();
					book = new Book(bookname, author, number, borrow, location);
					msg = adminService.addBook(book);
				}

				Tools.createMsgDialog(adminView, msg);
			}
		});

		addCleanBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNameText.setText("");
				addAuthorText.setText("");
				addNumberText.setText("");
				addLocationText.setText("");
				addBorrowText.setSelectedIndex(0);
			}
		});
	

		deletenumBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				book = adminService.findByNumber(deletenumText.getText());
				if (book!=null) {
					showBookMsgDialog(book);
				}else {
					Tools.createMsgDialog(adminView, "Book does not exist!");
					deletenumText.setText("");
				}
			}
		});
		

		sure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = adminService.deleteByNum(book.getNum().toString());
				Tools.createMsgDialog(adminView, msg);
				bookDialog.dispose();
			}
		});
		

		nosure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookDialog.dispose();
			}
		});
		

		updateFindBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				book = adminService.findByNumber(updateNumText0.getText());
				if (book != null) {
					showBookMsgOnUpdataPanel(book);
				}else {
					Tools.createMsgDialog(adminView, "Book not exists");
				}
			}
		});
		

		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				if (updateNumText.getText().equals("") || updateNumText.getText().contains(" ")) {
					Tools.createMsgDialog(adminView, "Id format error");
				}else {

					System.out.println(updateBorrowText.getSelectedItem().toString());
					book = new Book(id ,updateNameText.getText(), updateAuthorText.getText(),
							Long.parseLong(updateNumText.getText()), updateBorrowText.getSelectedItem().toString(),
							updateLocationText.getText());
					msg = adminService.update(book);
					Tools.createMsgDialog(adminView, msg);
					cleanBookMsgOnUppdataPanel();
				}
			}
		});
	}
	

	private void setVisibleFalse() {
		addPanel.setVisible(false);
		deletePanel.setVisible(false);
		findPanel.setVisible(false);
		updataPanel.setVisible(false);
		
		c.remove(addPanel);
		c.remove(deletePanel);
		c.remove(findPanel);
		c.remove(updataPanel);
	}
	

	// 这里我改了
	private void showBookMsgDialog(Book book) {
		
		Container bookContainer = bookDialog.getContentPane();
		bookContainer.setLayout(null);
		
		bookDialog.setSize(500, 300);
		bookDialog.setLocationRelativeTo(null);
		

		JLabel name = new JLabel("Name:"+book.getBookname());
		JLabel author = new JLabel("Author:"+book.getAuthor());
		JLabel num = new JLabel("Id:"+book.getNum().toString());
		JLabel location = new JLabel("Location:"+book.getLocation());
		JLabel borrow = new JLabel("Borrow Info:"+book.getBorrow());
		

		name.setBounds(100, 50, 300, 30);
		author.setBounds(100, 80, 300, 30);
		num.setBounds(100, 110, 300, 30);
		location.setBounds(100, 140, 300, 30);
		borrow.setBounds(100, 170, 300, 30);
		sure.setBounds(130, 220, 100, 25);
		nosure.setBounds(250, 220, 100, 25);

		bookContainer.add(name);
		bookContainer.add(author);
		bookContainer.add(num);
		bookContainer.add(location);
		bookContainer.add(borrow);
		bookContainer.add(sure);
		bookContainer.add(nosure);
		bookDialog.setVisible(true);
	}


	private void showBookMsgOnUpdataPanel(Book book) {

//		JLabel name = new JLabel("Name:"+book.getBookname());
//		JLabel author = new JLabel("Author:"+book.getAuthor());
//		JLabel num = new JLabel("Id:"+book.getNum().toString());
//		JLabel location = new JLabel("Location:"+book.getLocation());
//		JLabel borrow = new JLabel("Borrow Info:"+book.getBorrow());
		JLabel name = new JLabel("Name:");
		JLabel author = new JLabel("Author:");
		JLabel num = new JLabel("Id:");
		JLabel location = new JLabel("Location:");
		JLabel borrow = new JLabel("Borrow Info:");
		ComboBoxModel<String> model = new DefaultComboBoxModel<String>(addBorrowList);
		updateBorrowText.setModel(model);

		id = book.getId();
		updateNameText.setText(book.getBookname());
		updateAuthorText.setText(book.getAuthor());
		updateNumText.setText(book.getNum().toString());
		updateLocationText.setText(book.getLocation());
		if (book.getBorrow().equals("Borrowed")) {
			updateBorrowText.setSelectedIndex(1);
		}
		
		name.setBounds(200, 130, 60, 30);
		author.setBounds(200, 160, 60, 30);
		num.setBounds(200, 190, 60, 30);
		location.setBounds(200, 220, 60, 30);
		borrow.setBounds(200, 250, 60, 30);
		
		updateNameText.setBounds(300, 132, 200, 25);
		updateAuthorText.setBounds(300, 162, 200, 25);
		updateNumText.setBounds(300, 192, 200, 25);
		updateLocationText.setBounds(300, 222, 200, 25);
		updateBorrowText.setBounds(300, 252, 80, 25);
		updateBtn.setBounds(200, 290, 300, 25);
		
		updataPanel.add(name);
		updataPanel.add(author);
		updataPanel.add(num);
		updataPanel.add(location);
		updataPanel.add(borrow);
		updataPanel.add(updateNameText);
		updataPanel.add(updateAuthorText);
		updataPanel.add(updateNumText);
		updataPanel.add(updateLocationText);
		updataPanel.add(updateBorrowText);
		updataPanel.add(updateBtn);
		updataPanel.setVisible(false);
		updataPanel.setVisible(true);
	}


	private void cleanBookMsgOnUppdataPanel() {
		updateNameText.setText("");
		updateAuthorText.setText("");
		updateNumText.setText("");
		updateLocationText.setText("");
		updateBorrowText.setSelectedIndex(0);
	}
	
}
