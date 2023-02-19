package dao.display;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import dao.floorplan.SurfaceTypeDAO;
import museum.display.DisplaySurfaceType;
import museum.display.DisplayType;
import museum.floorplan.SurfaceType;

public class DisplaySurfaceTypeDAO extends DAO<DisplaySurfaceType> {

	private static final String TABLE = "display_surface_type";
	private static final String PK = "id_display_surface_type";
	private static final String DISPLAY = "ref_display_type";
	private static final String SURFACE = "ref_surface_type";
	
	private static DisplaySurfaceTypeDAO instance=null;

	public static DisplaySurfaceTypeDAO getInstance(){
		if (instance==null){
			instance = new DisplaySurfaceTypeDAO();
		}
		return instance;
	}

	private DisplaySurfaceTypeDAO() {
		super();
	}

	@Override
	public boolean create(DisplaySurfaceType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(DisplaySurfaceType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DisplaySurfaceType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DisplaySurfaceType read(int id) {
		DisplaySurfaceType display_surface_type = null;
		if (data.containsKey(id)) {
			display_surface_type = data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_display_type = rs.getInt(DISPLAY);
				int ref_surface_type = rs.getInt(SURFACE);
				DisplayType display_type = DisplayTypeDAO.getInstance().read(ref_display_type);
				SurfaceType surface_type = SurfaceTypeDAO.getInstance().read(ref_surface_type);
				display_surface_type = new DisplaySurfaceType(id, surface_type, display_type);
				data.put(id, display_surface_type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return display_surface_type;
	}
	
	public List<DisplaySurfaceType> readAll() {
		List<DisplaySurfaceType> display_surface_types = new ArrayList<DisplaySurfaceType>();
		DisplaySurfaceType display_surface_type = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_display_surface_type = rs.getInt(1);
				display_surface_type = DisplaySurfaceTypeDAO.getInstance().read(id_display_surface_type);
				display_surface_types.add(display_surface_type);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return display_surface_types;
	}
}
