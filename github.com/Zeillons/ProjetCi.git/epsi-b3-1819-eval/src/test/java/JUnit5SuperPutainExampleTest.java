package test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

class JUnit5SuperPutainExampleTest {

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
	@Test	
	void creationContact() {
		Book book = new Book();
		Contact contact = new Contact(book);
		assertNotEquals(null, contact);
	}
	@Test	
	void toStringContact() {
		Book book = new Book();
		Contact contact = new Contact(book);
		contact.setEmail("teddy@sheh.fr");
		contact.setId("123");
		contact.setName("Teddy");
		contact.setPhone("1234");
		contact.setType(Contact.Type.PERSO);
		String tostring = contact.toString();
		assertEquals("Contact{id='123', name='Teddy', email='teddy@sheh.fr', phone='1234', type=PERSO}", tostring);
	}
	
	/*	App.restoreFromSer();
		ContactDAO contactDao = new ContactDAO();
		Contact contact = contactDao.findById("9d93f731-adc2-4abf-ab25-614f849c3143");
		assertNotEquals(null, contactDao);*/

}
