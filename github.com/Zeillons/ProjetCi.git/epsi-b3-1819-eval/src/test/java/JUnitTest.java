import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fr.epsi.book.dal.Bullshit;
import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

class JUnitTest {

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
		//
	}
	@Test	
	void creationContactUniqueId() {
		Book book = new Book();
		Contact contact = new Contact(book);
		Contact contact0 = new Contact(book);
		assertNotEquals(contact0.getId(), contact.getId());
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
	@Test	
	void variableEnv() {
		String javaHome = System.getenv("JAVA_HOME");
		assertNotEquals(null, javaHome);
	}
	@Test	
	void sheh() {
		boolean sheh = false;
		//for(int i =0; i<10;i++) {
			Bullshit shehFactory = new Bullshit();
			sheh = shehFactory.sheh();
		//}
		assertEquals(true, sheh);
	}
	
}
