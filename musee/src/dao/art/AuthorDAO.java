package dao.art;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import museum.art.Author;

public class AuthorDAO extends DAO<Author> {
	
	private static final String TABLE = "author";
	private static final String PK = "id_author";
	private static final String NOM = "last_name";
	private static final String PRENOM = "first_name";
	private static final String SURNOM = "additional_name";
	private static final String DATES = "dates";
	
	private static AuthorDAO instance=null;

	public static AuthorDAO getInstance(){
		if (instance==null){
			instance = new AuthorDAO();
		}
		return instance;
	}

	private AuthorDAO() {
		super();
	}

	@Override
	public boolean create(Author obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Author obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Author obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Author read(int id) {
		Author author = null;
		if (data.containsKey(id)) {
			author=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String nom = rs.getString(NOM);
				String prenom = rs.getString(PRENOM);
				String surnom = rs.getString(SURNOM);
				String dates = rs.getString(DATES);
				author = new Author(id, nom, prenom, surnom, dates);
				data.put(id, author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return author;
	}
	
	public List<Author> readAll() {
		List<Author> authors = new ArrayList<Author>();
		Author author = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_author = rs.getInt(1);
				author = AuthorDAO.getInstance().read(id_author);
				authors.add(author);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return authors;
	}
}
