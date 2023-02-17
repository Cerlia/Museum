package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import museum.Door;
import museum.Room;

public class DoorDAO extends DAO<Door> {
	
	private static final String TABLE = "door";
	private static final String PK = "id_door";
	private static final String ROOM = "ref_room";
	private static final String DIMX = "dim_x";
	private static final String DIMZ = "dim_z";
	private static final String POSX = "pos_x";
	private static final String POSY = "pos_y";
	
	private static DoorDAO instance=null;

	public static DoorDAO getInstance(){
		if (instance==null){
			instance = new DoorDAO();
		}
		return instance;
	}

	private DoorDAO() {
		super();
	}

	@Override
	public boolean create(Door obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Door obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Door obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Door read(int id) {
		Door door = null;
		if (data.containsKey(id)) {
			door=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_room = rs.getInt(ROOM);
				int dim_x = rs.getInt(DIMX);
				int dim_z = rs.getInt(DIMZ);
				int pos_x = rs.getInt(POSX);
				int pos_y = rs.getInt(POSY);
				Room room = RoomDAO.getInstance().read(ref_room);				
				door = new Door(id, dim_x, dim_z, pos_x, pos_y, room);
				data.put(id, door);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return door;
	}
	
	public List<Door> readAll() {
		List<Door> doors = new ArrayList<Door>();
		Door door = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_door = rs.getInt(1);
				door = DoorDAO.getInstance().read(id_door);
				doors.add(door);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return doors;
	}
}
