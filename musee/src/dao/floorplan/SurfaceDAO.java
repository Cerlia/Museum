package dao.floorplan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import dao.display.DisplayDAO;
import museum.display.Display;
import museum.floorplan.Room;
import museum.floorplan.Surface;
import museum.floorplan.SurfaceType;

public class SurfaceDAO extends DAO<Surface> {
	
	private static final String TABLE = "surface";
	private static final String PK = "id_surface";
	private static final String ROOM = "ref_room";
	private static final String NAME = "name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String DIMZ = "dim_z";
	private static final String TYPE = "ref_surface_type";
	private static final String NB = "number";
	
	private static SurfaceDAO instance=null;

	public static SurfaceDAO getInstance(){
		if (instance==null){
			instance = new SurfaceDAO();
		}
		return instance;
	}

	private SurfaceDAO() {
		super();
	}	

	@Override
	public boolean create(Surface surface) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+ROOM+", "+NAME+", "+DIMX+", "+DIMY+", "+DIMZ+", "+TYPE +", " + NB+
					") VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, surface.getRoom().getId_room());
			pst.setString(2, surface.getName());
			pst.setInt(3, surface.getDim_x());
			pst.setInt(4, surface.getDim_y());
			pst.setInt(5, surface.getDim_z());
			pst.setInt(6, surface.getSurface_type().getId_surface_type());
			pst.setInt(7, surface.getNumber());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				surface.setId_surface(rs.getInt(1));
			}
			data.put(surface.getId_surface(), surface);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean delete(Surface surface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Surface surface) {
		boolean success = true;
		try {
			String requete = "UPDATE "+TABLE+" SET "+NB+"= ?,"+ROOM+"= ?,"+NAME+"= ?,"+DIMX+"= ?,"+DIMY+"= ?,"+DIMZ
					+"= ?,"+TYPE+"= ? WHERE "+PK+"= ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, surface.getNumber());
			pst.setInt(2, surface.getRoom().getId_room());
			pst.setString(3, surface.getName());
			pst.setInt(4, surface.getDim_x());
			pst.setInt(5, surface.getDim_y());
			pst.setInt(6, surface.getDim_z());
			pst.setInt(7, surface.getSurface_type().getId_surface_type());
			pst.setInt(8, surface.getId_surface());
			pst.executeUpdate();
			data.put(surface.getId_surface(), surface);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Surface read(int id_surface) {
		Surface surface = null;
		if (data.containsKey(id_surface)) {
			surface=data.get(id_surface);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id_surface;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_room = rs.getInt(ROOM);				
				int ref_surface_type = rs.getInt(TYPE);
				String name = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				int number = rs.getInt(NB);
				Room room = RoomDAO.getInstance().read(ref_room);
				SurfaceType surface_type = SurfaceTypeDAO.getInstance().read(ref_surface_type);
				List<Display> displays = new ArrayList<Display>();
				surface = new Surface(id_surface, room, name, dim_x, dim_y, dim_z, surface_type, number, displays);
				data.put(id_surface, surface);				
				displays.addAll(DisplayDAO.getInstance().readAllDisplaysOfSurface(id_surface));				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return surface;
	}
	
	public List<Surface> readAll() {
		List<Surface> surfaces = new ArrayList<Surface>();
		Surface surface = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_surface = rs.getInt(1);
				surface = SurfaceDAO.getInstance().read(id_surface);
				surfaces.add(surface);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return surfaces;
	}
	
	/**
	 * retourne la liste de surfaces d'une salle donnée
	 * @param id_room
	 * @return
	 */
	public List<Surface> readAllSurfacesOfRoom(int id_room) {
		List<Surface> surfaces = new ArrayList<Surface>();
		Surface surface = null;
		try {
			String requete = "SELECT * FROM " + TABLE + " WHERE " + ROOM + "= " + id_room;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_surface = rs.getInt(1);
				surface = SurfaceDAO.getInstance().read(id_surface);
				surfaces.add(surface);
			}
		} catch (SQLException e) {
		// e.printStackTrace();
		System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return surfaces;
	}
}
