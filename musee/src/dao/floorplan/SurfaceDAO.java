package dao.floorplan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import museum.floorplan.Room;
import museum.floorplan.Surface;

public class SurfaceDAO extends DAO<Surface> {
	
	private static final String TABLE = "zone";
	private static final String PK = "id_zone";
	private static final String ROOM = "ref_room";
	private static final String NAME = "name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	
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
	public boolean create(Surface obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Surface obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Surface obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Surface read(int id) {
		Surface surface = null;
		if (data.containsKey(id)) {
			surface=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_room = rs.getInt(ROOM);				
				String nom = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				Room room = RoomDAO.getInstance().read(ref_room);				
				surface = new Surface(id, nom, dim_x, dim_y, room);				
				data.put(id, surface);
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
}
