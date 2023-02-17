package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import museum.Room;
import museum.Zone;

public class ZoneDAO extends DAO<Zone> {
	
	private static final String TABLE = "zone";
	private static final String PK = "id_zone";
	private static final String ROOM = "ref_room";
	private static final String NAME = "name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	
	private static ZoneDAO instance=null;

	public static ZoneDAO getInstance(){
		if (instance==null){
			instance = new ZoneDAO();
		}
		return instance;
	}

	private ZoneDAO() {
		super();
	}	

	@Override
	public boolean create(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Zone read(int id) {
		Zone zone = null;
		if (data.containsKey(id)) {
			zone=data.get(id);
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
				zone = new Zone(id, nom, dim_x, dim_y, room);				
				data.put(id, zone);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return zone;
	}
	
	public List<Zone> readAll() {
		List<Zone> zones = new ArrayList<Zone>();
		Zone zone = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_zone = rs.getInt(1);
				zone = ZoneDAO.getInstance().read(id_zone);
				zones.add(zone);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return zones;
	}
}
