package test.resources;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.MultipleGradientPaint.ColorSpaceType;

import org.junit.jupiter.api.Test;

import fr.epsi.book.App;
import fr.epsi.book.dal.ContactDAO;
import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

class testContact {

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
	@Test	
	void creationContactUniqueId() {
		Book book = new Book();
		Contact contact = new Contact(book);
		Contact contact0 = new Contact(book);
		assertNotEquals(contact0.getId(), contact.getId());
	}
	/*	App.restoreFromSer();
		ContactDAO contactDao = new ContactDAO();
		Contact contact = contactDao.findById("9d93f731-adc2-4abf-ab25-614f849c3143");
		assertNotEquals(null, contactDao);*/

}
