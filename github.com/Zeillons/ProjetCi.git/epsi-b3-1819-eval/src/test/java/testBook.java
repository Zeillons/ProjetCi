package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.epsi.book.domain.Book;

class testBook {

	@Test	
	void creationBook() {
		Book book = new Book();
		assertNotEquals(null, book);
	}
	@Test	
	void creationBookUniqueId() {
		Book book0 = new Book();
		Book book = new Book();
		assertNotEquals(book0.getId(), book.getId());
	}
	
	/*	App.restoreFromSer();
		ContactDAO contactDao = new ContactDAO();
		Contact contact = contactDao.findById("9d93f731-adc2-4abf-ab25-614f849c3143");
		assertNotEquals(null, contactDao);*/

}