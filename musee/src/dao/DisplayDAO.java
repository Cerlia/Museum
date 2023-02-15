package dao;

import museum.Display;

public class DisplayDAO extends DAO<Display> {

	/*
	 * Ci-dessous les noms des champs dans la BD
	private static final String CAPACITE = "capacite";
	private static final String LOCALISATION = "loc";
	private static final String NOM_AV = "nom_av";
	private static final String TABLE = "Avion";
	private static final String CLE_PRIMAIRE = "num_av";
	 */
	
	private static DisplayDAO instance=null;

	public static DisplayDAO getInstance(){
		if (instance==null){
			instance = new DisplayDAO();
		}
		return instance;
	}

	private DisplayDAO() {
		super();
	}

	@Override
	public boolean create(Display obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Display obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Display obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Display read(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
