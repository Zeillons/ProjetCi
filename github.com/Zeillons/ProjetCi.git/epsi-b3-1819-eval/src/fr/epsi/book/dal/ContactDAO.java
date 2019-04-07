package fr.epsi.book.dal;

import fr.epsi.book.App;
import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sun.scenario.effect.light.Light.Type;

public class ContactDAO implements IDAO<Contact, String> {
	
	private static final String INSERT_QUERY = "INSERT INTO contact (id, book, name, email, phone, type_num) values (?,?,?,?,?,?)";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM contact WHERE id = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM contact";
	private static final String UPDATE_QUERY = "UPDATE contact SET id = ?, name = ?, email = ?, phone = ?, type_num = ? WHERE id =?";
	private static final String REMOVE_QUERY = "DELETE FROM contact WHERE id = ?";
		
	@Override
	public void create( Contact c ) throws SQLException {
		
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS );
		st.setString( 1, c.getId() );
		st.setString( 2, c.getBook().getId());
		st.setString( 3, c.getName() );
		st.setString( 4, c.getEmail() );
		st.setString( 5, c.getPhone() );
		st.setInt( 6, c.getType().ordinal() );
		st.executeUpdate();
	}
	
	@Override
	public Contact findById( String aString ) {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( FIND_BY_ID_QUERY, Statement.RETURN_GENERATED_KEYS );
			st.setString( 1, aString);
			st.executeQuery();
			ResultSet rs;
			rs = st.getResultSet();
			
			Contact c = new Contact(null);
			if ( rs.next() ) {
				c.setId( rs.getString(1) );
				c.setName(rs.getString(2) );
				c.setEmail(rs.getString(3) );
				c.setPhone(rs.getString(4) );
				c.setType(Contact.Type.values()[rs.getInt(5)]);
			}
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Contact> findAll() {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( FIND_ALL_QUERY, Statement.RETURN_GENERATED_KEYS );
			st.executeQuery();
			ResultSet rs;
			rs = st.getResultSet();
			
			List<Contact> listC = new ArrayList<Contact>();
			Contact c = new Contact(null);
			while ( rs.next() ) {
				c = new Contact(null);
				c.setId( rs.getString( 1 ) );
				c.setName(rs.getString(2) );
				c.setEmail(rs.getString(3) );
				c.setPhone(rs.getString(4) );
				c.setType(Contact.Type.values()[rs.getInt(5)]);
				listC.add(c);
			}
			return listC;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Contact update( Contact c ) {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st;
			st = connection.prepareStatement( UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS );
			st.setString( 1, c.getId() );
			st.setString( 2, c.getName() );
			st.setString( 3, c.getEmail() );
			st.setString( 4, c.getPhone() );
			st.setInt( 5, c.getType().ordinal() );
			st.executeUpdate();
			
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void remove( Contact o ) {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( REMOVE_QUERY, Statement.RETURN_GENERATED_KEYS );
			st.setString( 1, o.getId());
			st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


