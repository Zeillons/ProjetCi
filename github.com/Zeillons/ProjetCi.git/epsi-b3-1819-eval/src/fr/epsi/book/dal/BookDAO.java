package fr.epsi.book.dal;

import fr.epsi.book.App;
import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements IDAO<Book, String> {
	private static final String INSERT_QUERY = "INSERT INTO book (id,code) values (?,?)";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM book WHERE id = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM book";
	private static final String UPDATE_QUERY = "UPDATE book SET id = ?, code =? WHERE id =?";
	private static final String REMOVE_QUERY = "DELETE FROM book WHERE id = ?";
	@Override
	public void create( Book o ) throws SQLException {
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( INSERT_QUERY);
		st.setString( 1, o.getId() );
		st.setString( 2, o.getCode() );
		st.executeUpdate();
	}
	
	@Override
	public Book findById( String id ) {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( FIND_BY_ID_QUERY);
			st.setString( 1, id);
			st.executeQuery();
			ResultSet rs;
			rs = st.getResultSet();
			
			Book b = new Book();
			if ( rs.next() ) {
				b.setId( rs.getString(1) );
				b.setCode(rs.getString(2) );
				
			}
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Book> findAll() {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( FIND_ALL_QUERY );
			st.executeQuery();
			ResultSet rs;
			rs = st.getResultSet();
			
			List<Book> listB = new ArrayList<Book>();
			Book b = new Book();
			while ( rs.next() ) {
				b = new Book();
				b.setId( rs.getString( 1 ) );
				b.setCode(rs.getString(2) );
				
				listB.add(b);
			}
			
			
			
			return listB;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Book update( Book b ) {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st;
			st = connection.prepareStatement( UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS );
			st.setString( 1, b.getId() );
			st.setString( 2, b.getCode() );
			st.setString( 3, b.getId() );
			st.executeUpdate();
			
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void remove( Book b ) {
		try {
			Connection connection = PersistenceManager.getConnection();
			PreparedStatement st = connection.prepareStatement( REMOVE_QUERY, Statement.RETURN_GENERATED_KEYS );
			st.setString( 1, b.getId());
			st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
