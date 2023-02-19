package dao.floorplan;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.Connect;
import dao.DAO;
import museum.floorplan.Museum;

public class MuseumDAO extends DAO<Museum> {
	
	private static final String TABLE = "museum";
	private static final String PK = "id_museum";
	private static final String NAME = "museum_name";
	
	private static MuseumDAO instance=null;

	public static MuseumDAO getInstance(){
		if (instance==null){
			instance = new MuseumDAO();
		}
		return instance;
	}

	private MuseumDAO() {
		super();
	}

	@Override
	public boolean create(Museum obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Museum obj) {
		// le musée peut seulement être créé et modifié, pas supprimé
		return false;
	}

	@Override
	public boolean update(Museum obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Museum read(int id) {
		Museum museum = null;
		if (data.containsKey(id)) {
			museum=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String museum_name = rs.getString(NAME);				
				museum = new Museum(id, museum_name);
				data.put(id, museum);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return museum;
	}
}