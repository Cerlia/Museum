package dao.display;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import museum.display.Display;
import museum.display.DisplayModel;
import museum.display.DisplayType;

public class DisplayModelDAO extends DAO<DisplayModel> {
	
	private static final String TABLE = "display_model";
	private static final String PK = "id_display_model";
	private static final String NAME = "name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String DIMZ = "dim_z";
	private static final String MULTIPLE = "display_multiple";
	private static final String TYPE = "ref_display_type";
	
	private static DisplayModelDAO instance=null;

	public static DisplayModelDAO getInstance(){
		if (instance==null){
			instance = new DisplayModelDAO();
		}
		return instance;
	}

	private DisplayModelDAO() {
		super();
	}

	@Override
	public boolean create(DisplayModel obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(DisplayModel obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DisplayModel obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DisplayModel read(int id) {
		DisplayModel display_model = null;
		if (data.containsKey(id)) {
			display_model=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String name = rs.getString(NAME);
				int ref_display_type = rs.getInt(TYPE);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				boolean display_multiple = rs.getBoolean(MULTIPLE);
				DisplayType display_type = DisplayTypeDAO.getInstance().read(ref_display_type);
				display_model = new DisplayModel(id, name, dim_x, dim_y, dim_z, display_multiple, display_type);
				data.put(id, display_model);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return display_model;
	}
	
	public List<DisplayModel> readAll() {
		List<DisplayModel> display_models = new ArrayList<DisplayModel>();
		DisplayModel display_model = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_display_model = rs.getInt(1);
				display_model = DisplayModelDAO.getInstance().read(id_display_model);
				display_models.add(display_model);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return display_models;
	}
	
	/**
	 * retourne la liste des modèles de présentoir compatibles avec un type de surface et un type d'œuvre
	 * @param id_surface_type
	 * @param id_art_type
	 * @return
	 */
	public List<DisplayModel> readAllCompatibleDisplayModels(int id_surface_type, int id_art_type) {
		List<DisplayModel> displayModels = new ArrayList<DisplayModel>();
		DisplayModel displayModel = null;
		try {
			String requete = "select * from display_model dm"
					+ " join display_type dt on (dm.ref_display_type = dt.id_display_type)\r\n"
					+ " join display_art_type dat on (dt.id_display_type = dat.ref_display_type)\r\n"
					+ " join display_surface_type dst on (dat.ref_display_type = dst.id_display_surface_type)\r\n"
					+ " where dat.ref_art_type = " + id_art_type
					+ " and dst.ref_surface_type = " + id_surface_type;	
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_display_model = rs.getInt(1);
				displayModel = DisplayModelDAO.getInstance().read(id_display_model);
				displayModels.add(displayModel);
			}
		} catch (SQLException e) {
		// e.printStackTrace();
		System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return displayModels;
	}
}