import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import fr.epsi.book.domain.Book;

class JUnitTest2 {

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
}
