package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.Book;
import utils.JDBCUtil;

public class AdminDao extends UserDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;


	/**
	 * search admin by username and password
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public Admin findAllByAdminAndPasswd(String userName, String userPassword) {
		Admin admin = null;
		try {
			String sql = "select * from t_admin where username=? and password=?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, userPassword);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Integer id = rs.getInt("aid");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				
				admin = new Admin(id, username, password, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.release(rs, ps, conn);
		}
		
		return admin;
	}


	/**
	 * add a new book
	 * @param book
	 * @return
	 */
	public String addBook(Book book) {
		String msg = "";
		try {
			String sql = "insert into t_books (bookname,author,number,borrow,location) values (?,?,?,?,?)";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, book.getBookname());
			ps.setString(2, book.getAuthor());
			ps.setLong(3, book.getNum());
			ps.setString(4, book.getBorrow());
			ps.setString(5, book.getLocation());
			
			int flag = ps.executeUpdate();
			if(flag == 1) {
				msg = "Success";
			}else {
				msg = "Fail";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.release(ps, conn);
		}
		return msg;
	}

	/**
	 * delete a book by id
	 * @param num
	 * @return
	 */
	public String deleteByNum(String num) {
		String msg = "";
		try {
			String sql = "delete from t_books where number = ?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			int flag = ps.executeUpdate();
			if (flag == 1) {
				msg = "Success";
			}else {
				msg = "Fail";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.release(ps, conn);
		}
		return msg;
	}

	/**
	 * update book info
	 * @param book
	 * @return
	 */
	public String updateBook(Book book) {
		String msg = "";
		try {
			String sql = "update t_books set bookname=?, author=?, number=?, borrow=?, location=? where id=?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, book.getBookname());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getNum().toString());
			ps.setString(4, book.getBorrow());
			ps.setString(5, book.getLocation());
			ps.setString(6, book.getId().toString());
			
			int flag = ps.executeUpdate();
			if (flag == 1) {
				msg = "Success";
			}else {
				msg = "Fail";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.release(ps, conn);
		}
		return msg;
	}
}
