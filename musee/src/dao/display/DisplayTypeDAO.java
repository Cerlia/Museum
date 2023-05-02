package dao.display;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import museum.display.DisplayType;

public class DisplayTypeDAO extends DAO<DisplayType> {
	
	private static final String TABLE = "display_type";
	private static final String PK = "id_display_type";
	private static final String NAME = "display_type_name";
	
	private static DisplayTypeDAO instance=null;

	public static DisplayTypeDAO getInstance(){
		if (instance==null){
			instance = new DisplayTypeDAO();
		}
		return instance;
	}

	private DisplayTypeDAO() {
		super();
	}

	@Override
	public boolean create(DisplayType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(DisplayType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DisplayType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DisplayType read(int id) {
		DisplayType display_type = null;
		if (data.containsKey(id)) {
			display_type=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String nom = rs.getString(NAME);
				display_type = new DisplayType(id, nom);
				data.put(id, display_type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return display_type;
	}
	
	public List<DisplayType> readAll() {
		List<DisplayType> display_types = new ArrayList<DisplayType>();
		DisplayType display_type = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_display_type = rs.getInt(1);
				display_type = DisplayTypeDAO.getInstance().read(id_display_type);
				display_types.add(display_type);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return display_types;
	}
}
