package fr.epsi.book.dal;

import fr.epsi.book.App;
import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.scenario.effect.light.Light.Type;

public class ContactDAO implements IDAO<Contact, String> {
	
	private static final String INSERT_QUERY = "INSERT INTO contact (id, name, email, phone, type_num, book_id) values (?,?,?,?,?,?)";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM contact WHERE id = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM contact";
	private static final String UPDATE_QUERY = "UPDATE contact SET id = ?, name = ?, email = ?, phone = ?, type_num = ?, book_id=? WHERE id =?";
	private static final String REMOVE_QUERY = "DELETE FROM contact WHERE id = ?";
		
	@Override
	public void create( Contact c ) throws SQLException {
		BookDAO bookDao = new BookDAO();
		
		try {
			if((bookDao.findById(c.getBook())).equals(null)) {
				for (Book book : App.bookList) {
					if(book.getId().equals(c.getBook()))
						bookDao.create(book);
				}
				
			}
		}catch(Exception e){
			System.out.println("Erreur de l'ajout du book");
		}
		
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement(INSERT_QUERY);
		st.setString( 1, c.getId() );
		st.setString( 2, c.getName() );
		st.setString( 3, c.getEmail() );
		st.setString( 4, c.getPhone() );
		st.setInt( 5, c.getType().ordinal() );
		st.setString( 6, c.getBook());
		try {	
			st.executeUpdate();
		}catch(Exception e){
			System.out.println("BadaboumCaPlante(comme les fleur) requete :"+st.toString());
		}
	}
	
	@Override
	public Contact findById( String aString ) {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( FIND_BY_ID_QUERY);
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
			PreparedStatement st = connection.prepareStatement( FIND_ALL_QUERY);
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
				for (Book unBook: App.bookList) {
					if(rs.getString(6).equals(unBook.getId())) {
						c.setBook(unBook.getId());
						unBook.addContact(c);
					}			
				}
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
			st = connection.prepareStatement( UPDATE_QUERY);
			st.setString( 1, c.getId() );
			st.setString( 2, c.getName() );
			st.setString( 3, c.getEmail() );
			st.setString( 4, c.getPhone() );
			st.setInt( 5, c.getType().ordinal() );
			st.setString( 6, c.getBook());
			st.setString( 7, c.getId() );
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
			PreparedStatement st = connection.prepareStatement( REMOVE_QUERY);
			st.setString( 1, o.getId());
			st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


