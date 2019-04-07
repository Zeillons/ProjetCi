package resources;

import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

public class testget {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Book book = new Book();
		Contact contact = new Contact(book);
		contact.setEmail("teddy@sheh.fr");
		contact.setId("123");
		contact.setName("Teddy");
		contact.setPhone("1234");
		contact.setType(Contact.Type.PERSO);
		String tostring = contact.toString();
		System.out.println(tostring);
	}

}
