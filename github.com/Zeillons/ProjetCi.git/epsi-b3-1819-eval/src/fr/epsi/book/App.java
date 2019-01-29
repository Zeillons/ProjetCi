package fr.epsi.book;

import fr.epsi.book.dal.BookDAO;
import fr.epsi.book.dal.ContactDAO;
import fr.epsi.book.dal.IDAO;
import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {
	
	private static final String BOOK_BKP_DIR = "./resources/backup/";
	
	private static final Scanner sc = new Scanner( System.in );
	private static Book book = new Book();
	public static List<Book> bookList = new ArrayList<Book>();
	
	public static void main( String... args ) {
		bookList.add(book);
		dspMainMenu();
	}
	
	public static Contact.Type getTypeFromKeyboard() {
		int response;
		boolean first = true;
		do {
			if ( !first ) {
				System.out.println( "***********************************************" );
				System.out.println( "*    Mauvais choix, merci de recommencer !    *" );
				System.out.println( "***********************************************" );
			}
			System.out.println( "*******Choix type de contact *******" );
			System.out.println( "* 1 - Perso                        *" );
			System.out.println( "* 2 - Pro                          *" );
			System.out.println( "************************************" );
			System.out.print( "*Votre choix : " );
			try {
				response = sc.nextInt() - 1;
			} catch ( InputMismatchException e ) {
				response = -1;
			} finally {
				sc.nextLine();
			}
			first = false;
		} while ( 0 != response && 1 != response );
		return Contact.Type.values()[response];
	}
	
	public static void addContact() {
		System.out.println( "**************************************" );
		System.out.println( "**********Ajout d'un contact**********" );
		Contact contact = new Contact(book.getId());
		System.out.print( "Entrer le nom :" );
		contact.setName( sc.nextLine() );
		System.out.print( "Entrer l'email :" );
		contact.setEmail( sc.nextLine() );
		System.out.print( "Entrer le téléphone :" );
		contact.setPhone( sc.nextLine() );
		contact.setType( getTypeFromKeyboard() );
		book.addContact( contact );
		ContactDAO dao = new ContactDAO();
		try {
		dao.create(contact);
		}catch(Exception e) {
			System.out.println( "Attention, Contact non ajout� dans la bdd a cause d'une erreur (padbol)" );
		}
		System.out.println( "Nouveau contact ajouté ..." );
		
	}
	
	public static void editContact() {
		System.out.println( "*********************************************" );
		System.out.println( "**********Modification d'un contact**********" );
		dspContacts( false );
		System.out.print( "Entrer l'identifiant du contact : " );
		String id = sc.nextLine();
		Contact contact = book.getContacts().get( id );
		if ( null == contact ) {
			System.out.println( "Aucun contact trouvé avec cet identifiant ..." );
		} else {
			System.out
					.print( "Entrer le nom ('" + contact.getName() + "'; laisser vide pour ne pas mettre à jour) : " );
			String name = sc.nextLine();
			if ( !name.isEmpty() ) {
				contact.setName( name );
			}
			System.out.print( "Entrer l'email ('" + contact
					.getEmail() + "'; laisser vide pour ne pas mettre à jour) : " );
			String email = sc.nextLine();
			if ( !email.isEmpty() ) {
				contact.setEmail( email );
			}
			System.out.print( "Entrer le téléphone ('" + contact
					.getPhone() + "'; laisser vide pour ne pas mettre à jour) : " );
			String phone = sc.nextLine();
			if ( !phone.isEmpty() ) {
				contact.setPhone( phone );
			}
			ContactDAO dao = new ContactDAO();
			try {
			dao.update(contact);
			}catch(Exception e) {
				System.out.println( "Attention, Contact non modifi� dans la bdd a cause d'une erreur" );
			}
			System.out.println( "Le contact a bien été modifié ..." );
		}
	}
	
	public static void deleteContact() {
		System.out.println( "*********************************************" );
		System.out.println( "***********Suppression d'un contact**********" );
		dspContacts( false );
		System.out.print( "Entrer l'identifiant du contact : " );
		String id = sc.nextLine();
		Contact contact = book.getContacts().remove( id );
		if ( null == contact ) {
			System.out.println( "Aucun contact trouvé avec cet identifiant ..." );
		} else {
			ContactDAO dao = new ContactDAO();
			try {
			dao.remove(contact);
			}catch(Exception e) {
				System.out.println( "Attention, Contact non supprim� dans la bdd" );
			}
			System.out.println( "Le contact a bien été supprimé ..." );
		}
	}
	
	public static void sort() {
		int response;
		boolean first = true;
		do {
			if ( !first ) {
				System.out.println( "***********************************************" );
				System.out.println( "* Mauvais choix, merci de recommencer !       *" );
				System.out.println( "***********************************************" );
			}
			System.out.println( "*******Choix du critère*******" );
			System.out.println( "* 1 - Nom     **              *" );
			System.out.println( "* 2 - Email **                *" );
			System.out.println( "*******************************" );
			System.out.print( "*Votre choix : " );
			try {
				response = sc.nextInt();
			} catch ( InputMismatchException e ) {
				response = -1;
			} finally {
				sc.nextLine();
			}
			first = false;
		} while ( 0 >= response || response > 2 );
		Map<String, Contact> contacts = book.getContacts();
		switch ( response ) {
			case 1:
				contacts.entrySet().stream()
						.sorted( ( e1, e2 ) -> e1.getValue().getName().compareToIgnoreCase( e2.getValue().getName() ) )
						.forEach( e -> dspContact( e.getValue() ) );
				break;
			case 2:
				
				contacts.entrySet().stream().sorted( ( e1, e2 ) -> e1.getValue().getEmail()
																	 .compareToIgnoreCase( e2.getValue().getEmail() ) )
						.forEach( e -> dspContact( e.getValue() ) );
				break;
		}
	}
	
	public static void searchContactsByName() {
		
		System.out.println( "*******************************************************************" );
		System.out.println( "************Recherche de contacts sur le nom ou l'email************" );
		System.out.println( "*******************************************************************" );
		System.out.print( "*Mot cl� (1 seul) : " );
		String word = sc.nextLine();
		Map<String, Contact> subSet = new HashMap<>();
		
		for (Book b : bookList) {
			Map<String, Contact> miniSubSet = b.getContacts().entrySet().stream()
										  .filter( entry -> entry.getValue().getName().contains( word ) || entry
												  .getValue().getEmail().contains( word ) )
										  .collect( HashMap::new, ( newMap, entry ) -> newMap
												  .put( entry.getKey(), entry.getValue() ), Map::putAll );
			for (Contact unContact : miniSubSet.values()) {
				subSet.put(unContact.getId(), unContact);
			}
		}
		
		if ( subSet.size() > 0 ) {
			System.out.println( subSet.size() + " contact(s) trouvé(s) : " );
			subSet.entrySet().forEach( entry -> dspContact( entry.getValue() ) );
		} else {
			System.out.println( "Aucun contact trouvé avec cet identifiant ..." );
		}
	}
	
	public static void dspContact( Contact contact ) {
		System.out.println( contact.getId() + "\t\t\t\t" + contact.getName() + "\t\t\t\t" + contact
				.getEmail() + "\t\t\t\t" + contact.getPhone() + "\t\t\t\t" + contact.getType() );
	}
	
	public static void dspContacts( boolean dspHeader ) {
		if ( dspHeader ) {
			System.out.println( "**************************************" );
			System.out.println( "********Liste de vos contacts*********" );
		}
		for ( Map.Entry<String, Contact> entry : book.getContacts().entrySet() ) {
			dspContact( entry.getValue() );
		}
		System.out.println( "**************************************" );
	}
	
	public static void dspMainMenu() {
		int response = -1;
		boolean first = true;
		do {
			if ( !first ) {
				System.out.println( "***********************************************" );
				System.out.println( "* Mauvais choix, merci de recommencer !       *" );
				System.out.println( "***********************************************" );
			}
			System.out.println( "**************************************" );
			System.out.println( "*****************Menu*****************" );
			System.out.println( "* 1 - Ajouter un contact             *" );
			System.out.println( "* 2 - Modifier un contact            *" );
			System.out.println( "* 3 - Supprimer un contact           *" );
			System.out.println( "* 4 - Lister les contacts            *" );
			System.out.println( "* 5 - Rechercher un contact          *" );
			System.out.println( "* 6 - Trier les contacts             *" );
			System.out.println( "* 7 - Sauvegarder                    *" );
			System.out.println( "* 8 - Restaurer                      *" );
			System.out.println( "* 9 - Export des contacts            *" );
			System.out.println( "* 10 - Changement de book            *" );
			System.out.println( "* 11 - Quitter                       *" );
			System.out.println( "**************************************" );
			System.out.print( "*Votre choix : " );
			try {
				response = sc.nextInt();
			} catch ( InputMismatchException e ) {
				response = -1;
			} finally {
				sc.nextLine();
			}
			first = false;
		} while ( 1 > response || 11 < response );
		switch ( response ) {
			case 1:
				addContact();
				dspMainMenu();
				break;
			case 2:
				editContact();
				dspMainMenu();
				break;
			case 3:
				deleteContact();
				dspMainMenu();
				break;
			case 4:
				dspContacts( true );
				dspMainMenu();
				break;
			case 5:
				searchContactsByName();
				dspMainMenu();
				break;
			case 6:
				sort();
				dspMainMenu();
				break;
			case 7:
				storeContacts();
				dspMainMenu();
				break;
			case 8:
				restoreContacts();
				dspMainMenu();
				break;
			case 9:
				exportContacts();
				dspMainMenu();
				break;
			case 10:
				changeBook();
				dspMainMenu();
				break;
		}
	}
	
	private static void changeBook() {
		System.out.println( "**************************************" );
		System.out.println( "************Choisir un book***********" );
		int nbBooks = bookList.size();
		for (int i = 0; i < nbBooks; i++) {
			
			System.out.println( "* "+i+" - "+bookList.get(i).getId()+"             *" );
		}
		System.out.println( "* "+nbBooks+" - Nouveau Book             *" );
		System.out.print( "*Votre choix : " );
		int response;
		try {
			response = sc.nextInt();
		} catch ( InputMismatchException e ) {
			response = -1;
		} finally {
			sc.nextLine();
		}
		
		if(response<nbBooks && response>=0) {
			book = bookList.get(response);
		}else {
			book = new Book();
			bookList.add(book);
		}
	}
	
	private static void storeContacts() {
		
		Path path = Paths.get( BOOK_BKP_DIR );
		if ( !Files.isDirectory( path ) ) {
			try {
				Files.createDirectory( path );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		String backupFileName = new SimpleDateFormat( "yyyy-MM-dd-hh-mm-ss" ).format( new Date() ) + ".ser";
		try ( ObjectOutputStream oos = new ObjectOutputStream( Files
				.newOutputStream( Paths.get( BOOK_BKP_DIR + backupFileName ) ) ) ) {
			oos.writeObject( bookList );
			System.out.println( "Sauvegarde terminée : fichier " + backupFileName );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private static void restoreContacts() {
		System.out.println( "*****************Menu*****************" );
		System.out.println( "* 1 - Restore avec SER               *" );
		System.out.println( "* 2 - Restore avec BDD               *" );
		System.out.print(   "*Votre choix : "                        );
		int response;
		try {
			response = sc.nextInt();
		} catch ( InputMismatchException e ) {
			response = -1;
		} finally {
			sc.nextLine();
		}
		while ( 1 > response || 3 < response );
			switch ( response ) {
				case 1:
					restoreFromSer();
					break;
				case 2:
					restoreFromBDD();
					break;
			}
		
	}
	private static void restoreFromBDD() {
		BookDAO bookDao = new BookDAO();
		Boolean exists = false;
		for (Book b : bookDao.findAll()) {
			exists = false;
			for (Book b2 : bookList) {
				if(b2.getId().equals(b.getId()))
					exists = true;
			}
			if(!exists)
				bookList.add(b);
		}
		ContactDAO dao = new ContactDAO(); 
		System.out.println(dao.findAll());
		System.out.println( "***Contacts restaur�s dans tous vos books***" );
		
	}
	private static void restoreFromSer() {
		try ( DirectoryStream<Path> ds = Files.newDirectoryStream( Paths.get( BOOK_BKP_DIR ), "*.ser" ) ) {
			Path path;
			int nbPath = 0;
			List<Path> listPath = new ArrayList<Path>();
			System.out.println( "**************************************" );
			System.out.println( "***********Choisir un Fichier*********" );
			for ( Path unPath : ds ) {
				nbPath++;
				listPath.add(unPath);
				System.out.println( "* "+nbPath+" - "+unPath.getFileName()+"             *" );	
			}
			System.out.print( "*Votre choix : " );
			int response;
			try {
				response = sc.nextInt();
			} catch ( InputMismatchException e ) {
				response = -1;
			} finally {
				sc.nextLine();
			}
			
			if(response-1<nbPath && response>0) {
				path = listPath.get(response-1);
				System.out.println( "Restauration du fichier : " + path.getFileName() );
				try ( ObjectInputStream ois = new ObjectInputStream( Files.newInputStream( path ) ) ) {
					bookList = ( List<Book> ) ois.readObject();
					System.out.println( "Restauration terminée : fichier " + path.getFileName() );
				} catch ( ClassNotFoundException e ) {
					e.printStackTrace();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Ce fichier n'existe pas");
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private static void exportContacts() {
		try {
			String csvFile =  new SimpleDateFormat( "yyyy-MM-dd-hh-mm-ss" ).format( new Date() ) + ".csv";
	        FileWriter writer = new FileWriter( new File(BOOK_BKP_DIR + csvFile ));
	        writer.append("Id" + ',' + "Name" + ',' + "Email" + ',' + "Phone" + ',' + "Type"+','+"Book Id" );
	        
	        for (Book book : bookList) {
		        for ( Contact entry : book.getContacts().values() ) {
		            writer.append( "\n" + entry.getId() + "," + entry.getName() + "," + entry.getEmail() + "," + entry.getPhone() + "," + entry.getType() +","+entry.getBook() );
		        }
	        }
	        writer.flush();
	        writer.close();
	        System.out.println( "Contact export�s sous le fichier : " + csvFile);
	
		} catch ( Exception e ) {
			System.out.println("Erreur lors de l'export des contacts");
		}
	}
	
}
