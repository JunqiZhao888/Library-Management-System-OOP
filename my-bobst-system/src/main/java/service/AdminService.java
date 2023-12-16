package service;

import java.util.ArrayList;
import java.util.List;

import dao.AdminDao;
import model.Book;

public class AdminService extends UserService {
	AdminDao adminDao = new AdminDao();

	/**
	 * add a new book
	 * @param book
	 * @return
	 */
	public String addBook(Book book) {
		List<Book> books = new ArrayList<Book>();
		String msg = "";
		// data cannot be empty
		if(book.getAuthor().trim().equals("") || book.getBookname().trim().equals("") ||
				book.getBorrow().trim().equals("") || book.getLocation().trim().equals("") ||
				book.getNum()<0 || book.getNum().toString().trim().equals("")) {
			msg = "Please enter correct info";
		}else {// check if book exists
			books = adminDao.findBookByNum(book.getNum().toString());
			if (books.isEmpty()) {// not exists
				msg = adminDao.addBook(book);
			}else {// exists
				msg = "Book existed";
			}
		}
		return msg;
	}

	/**
	 * find book by id
	 * @param number
	 * @return
	 */
	public Book findByNumber(String number) {
		List<Book> books = new ArrayList<Book>();
		if (number.trim().equals("")) {
			return null;
		}else {
			books = adminDao.findBookByNum(number);
			if (books.isEmpty()) {
				return null;
			}
		}
		return books.get(0);
	}

	/**
	 * delete book by id
	 * @param num
	 * @return
	 */
	public String deleteByNum(String num) {
		return adminDao.deleteByNum(num);
	}

	/**
	 * update book
	 * @param book
	 */
	public String update(Book book) {
		String msg = "";
		if(book.getAuthor().trim().equals("") || book.getBookname().trim().equals("") ||
				book.getBorrow().trim().equals("") || book.getLocation().trim().equals("") ||
				book.getNum()<0 || book.getNum().toString().trim().equals("")) {
			msg = "Please enter correct info";
		}else {
			msg = adminDao.updateBook(book);
		}
		return msg;
	}

}
