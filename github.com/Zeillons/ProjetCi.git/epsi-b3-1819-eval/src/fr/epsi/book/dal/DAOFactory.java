package fr.epsi.book.dal;

import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

public class DAOFactory {
	
	private DAOFactory() {}
	
	public static IDAO<Contact, String> getContactDAO() {
		return new ContactDAO();
	}
	
	public static IDAO<Book, String> getBookDAO() {
		return new BookDAO();
	}
}
