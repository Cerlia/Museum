package dao;

import museum.Author;

public class AuthorDAO extends DAO<Author> {
	
	/*
	 * Ci-dessous les noms des champs dans la BD
	private static final String CAPACITE = "capacite";
	private static final String LOCALISATION = "loc";
	private static final String NOM_AV = "nom_av";
	private static final String TABLE = "Avion";
	private static final String CLE_PRIMAIRE = "num_av";
	 */
	

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
		// TODO Auto-generated method stub
		return null;
	}

}
