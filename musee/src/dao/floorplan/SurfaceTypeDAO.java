package dao.floorplan;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.Connect;
import dao.DAO;
import museum.floorplan.SurfaceType;

public class SurfaceTypeDAO extends DAO<SurfaceType> {
	
	private static final String TABLE = "surface_type";
	private static final String PK = "id_surface_type";
	private static final String NAME = "name";
	private static final String HAVE_DOOR = "can_have_door";
	
private static SurfaceTypeDAO instance=null;
	
	public static SurfaceTypeDAO getInstance(){
		if (instance==null){
			instance = new SurfaceTypeDAO();
		}
		return instance;
	}
	
	private SurfaceTypeDAO() {
		super();
	}

	@Override
	public boolean create(SurfaceType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(SurfaceType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(SurfaceType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SurfaceType read(int id) {
		SurfaceType surface_type = null;
		if (data.containsKey(id)) {
			surface_type=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String name = rs.getString(NAME);
				boolean can_have_door = rs.getBoolean(HAVE_DOOR);
				surface_type = new SurfaceType(id, name, can_have_door);
				data.put(id, surface_type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return surface_type;
	}
}
