package dao.display;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import dao.art.ArtDAO;
import dao.floorplan.SurfaceDAO;
import museum.art.Art;
import museum.display.Display;
import museum.display.DisplayModel;
import museum.floorplan.Surface;

public class DisplayDAO extends DAO<Display> {
	
	private static final String TABLE = "display";
	private static final String PK = "id_display";
	private static final String SURFACE = "ref_surface";
	private static final String MODEL = "ref_display_model";
	private static final String NAME = "display_name";
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
	public boolean create(Display display) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+SURFACE+", "+MODEL+", "+NAME+
					", "+DIMX+", "+DIMY+", "+DIMZ+") VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, display.getSurface().getId_surface());
			pst.setInt(2, display.getDisplay_model().getId_display_model());
			pst.setString(3, display.getName());
			pst.setInt(4, display.getDim_x());
			pst.setInt(5, display.getDim_y());
			pst.setInt(6, display.getDim_z());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				display.setId_display(rs.getInt(1));
			}
			data.put(display.getId_display(), display);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;	
	}

	@Override
	public boolean delete(Display display) {
		boolean success = true;
		try {
			int id_display = display.getId_display();
			String requete = "DELETE FROM "+TABLE+" WHERE "+PK+" = ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete);
			pst.setInt(1, id_display);
			pst.executeUpdate();
			data.remove(id_display);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean update(Display obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Display read(int id_display) {
		Display display = null;
		if (data.containsKey(id_display)) {
			display=data.get(id_display);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id_display;
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
				List<Art> arts = new ArrayList<Art>();
				display = new Display(id_display, name, dim_x, dim_y, dim_z, surface, display_model, arts);
				data.put(id_display, display);
				arts.addAll(ArtDAO.getInstance().readAllArtsOfDisplay(id_display));
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
	
	/**
	 * retourne la liste des présentoirs d'une surface donnée
	 * @param id_surface
	 * @return
	 */
	public List<Display> readAllDisplaysOfSurface(int id_surface) {
		List<Display> displays = new ArrayList<Display>();
		Display display = null;
		try {
			String requete = "SELECT * FROM " + TABLE + " WHERE " + SURFACE + "= " + id_surface;
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
	
	/**
	 * retourne la liste des présentoirs d'une salle donnée
	 * @param id_surface
	 * @return
	 */
	public List<Display> readAllDisplaysOfRoom(int id_room) {
		List<Display> displays = new ArrayList<Display>();
		Display display = null;
		try {
			String requete = "SELECT * FROM DISPLAY d JOIN surface s ON (d.ref_surface = s.id_surface)"
					+ "	JOIN room r ON (s.ref_room = r.id_room) WHERE id_room=" + id_room;
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
	
	/**
	 * retourne la liste des présentoirs existant sur une surface et compatibles avec un type d'œuvre
	 * @param id_surface
	 * @param id_art_type
	 * @return
	 */
	public List<Display> readAllExistingCompatibleDisplaysOfSurface(int id_surface, int id_art_type) {
		List<Display> displays = new ArrayList<Display>();
		Display display = null;
		try {
			String requete = "SELECT * FROM display d"
					+ " JOIN display_model dm ON (d.ref_display_model = dm.id_display_model)"
					+ " JOIN display_type dt ON (dm.ref_display_type = dt.id_display_type)"
					+ " JOIN display_art_type dat ON (dt.id_display_type = dat.ref_display_type)"
					+ " WHERE ref_surface =" + id_surface
					+ " AND ref_art_type =" + id_art_type;	
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
