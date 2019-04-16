package fr.epsi.book.dal;

import fr.epsi.book.App;
import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Bullshit {
	
	private static final String INSERT_QUERY = "INSERT INTO sheh (sheh,shehh,shehhh,sheeh,sheeeh,shheh,shhheh,ssheh,sssheh) values ('sheh','sheh','sheh','sheh','sheh','sheh','sheh','sheh','sheh')";
		

	public boolean sheh() {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS );
			st.executeUpdate();
			return true;
		}catch(Exception e) {
			System.out.println("ERROR"+e.toString());
			return false;
		}
	}
}
