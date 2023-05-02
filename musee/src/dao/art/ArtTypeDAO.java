package dao.art;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import museum.art.ArtType;

public class ArtTypeDAO extends DAO<ArtType> {
	
	private static final String TABLE = "art_type";
	private static final String PK = "id_art_type";
	private static final String NAME = "art_type_name";
	
	private static ArtTypeDAO instance=null;
	
	public static ArtTypeDAO getInstance(){
		if (instance==null){
			instance = new ArtTypeDAO();
		}
		return instance;
	}
	
	private ArtTypeDAO() {
		super();
	}

	@Override
	public boolean create(ArtType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArtType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(ArtType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArtType read(int id) {
		ArtType art_type = null;
		if (data.containsKey(id)) {
			art_type=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String nom = rs.getString(NAME);
				art_type = new ArtType(id, nom);
				data.put(id, art_type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return art_type;
	}
	
	public List<ArtType> readAll() {
		List<ArtType> art_types = new ArrayList<ArtType>();
		ArtType art_type = null;
		try {			
			String requete = "SELECT * FROM " + TABLE+ " ORDER BY "+NAME;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art_type = rs.getInt(1);
				art_type = ArtTypeDAO.getInstance().read(id_art_type);
				art_types.add(art_type);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return art_types;	
	}
}
