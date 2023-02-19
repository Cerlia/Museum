package dao.display;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import dao.art.ArtTypeDAO;
import museum.art.ArtType;
import museum.display.DisplayArtType;
import museum.display.DisplayType;

public class DisplayArtTypeDAO extends DAO<DisplayArtType> {
	
	private static final String TABLE = "display_art_type";
	private static final String PK = "id_display_art_type";
	private static final String DISPLAY = "ref_display_type";
	private static final String ART = "ref_art_type";
	
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
		DisplayArtType display_art_type = null;
		if (data.containsKey(id)) {
			display_art_type = data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_display_type = rs.getInt(DISPLAY);
				int ref_art_type = rs.getInt(ART);
				DisplayType display_type = DisplayTypeDAO.getInstance().read(ref_display_type);
				ArtType art_type = ArtTypeDAO.getInstance().read(ref_art_type);
				display_art_type = new DisplayArtType(id, display_type, art_type);
				data.put(id, display_art_type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return display_art_type;
	}
	
	public List<DisplayArtType> readAll() {
		List<DisplayArtType> display_art_types = new ArrayList<DisplayArtType>();
		DisplayArtType display_art_type = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_display_art_type = rs.getInt(1);
				display_art_type = DisplayArtTypeDAO.getInstance().read(id_display_art_type);
				display_art_types.add(display_art_type);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return display_art_types;
	}
}
