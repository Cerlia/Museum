package dao;

import museum.DisplayArtType;

public class DisplayArtTypeDAO extends DAO<DisplayArtType> {
	
	/*
	 * Ci-dessous les noms des champs dans la BD
	private static final String CAPACITE = "capacite";
	private static final String LOCALISATION = "loc";
	private static final String NOM_AV = "nom_av";
	private static final String TABLE = "Avion";
	private static final String CLE_PRIMAIRE = "num_av";
	 */
	
	private static DisplayArtTypeDAO instance=null;

	public static DisplayArtTypeDAO getInstance(){
		if (instance==null){
			instance = new DisplayArtTypeDAO();
		}
		return instance;
	}

	private DisplayArtTypeDAO() {
		super();
	}

	@Override
	public boolean create(DisplayArtType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(DisplayArtType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DisplayArtType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DisplayArtType read(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
