package dao.display;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import dao.floorplan.SurfaceDAO;
import museum.display.Display;
import museum.display.DisplayModel;
import museum.floorplan.Surface;

public class DisplayDAO extends DAO<Display> {
	
	private static final String TABLE = "display";
	private static final String PK = "id_display";
	private static final String SURFACE = "ref_surface";
	private static final String MODEL = "ref_display_model";
	private static final String NAME = "name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String DIMZ = "dim_z";	
	
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
		Display display = null;
		if (data.containsKey(id)) {
			display=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_surface = rs.getInt(SURFACE);
				int ref_model = rs.getInt(MODEL);
				String name = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				Surface surface = SurfaceDAO.getInstance().read(ref_surface);
				DisplayModel display_model = DisplayModelDAO.getInstance().read(ref_model);
				display = new Display(id, name, dim_x, dim_y, dim_z, surface, display_model);
				data.put(id, display);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return display;
	}
	
	public List<Display> readAll() {
		List<Display> displays = new ArrayList<Display>();
		Display display = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_display = rs.getInt(1);
				display = DisplayDAO.getInstance().read(id_display);
				displays.add(display);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return displays;
	}
}
