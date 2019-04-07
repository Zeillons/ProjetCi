package resources;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.epsi.book.App;
import fr.epsi.book.dal.*;
import fr.epsi.book.domain.*;

class JUnit5SuperExampleTest {

	@Test
	void test() {
		App.restoreFromSer();
		ContactDAO contactDao = new ContactDAO();
		Contact contact = contactDao.findById("9d93f731-adc2-4abf-ab25-614f849c3143");
		assertNotEquals(null, contactDao);
	}

}
