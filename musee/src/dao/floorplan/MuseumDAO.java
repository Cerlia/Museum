package dao.floorplan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.Connect;
import dao.DAO;
import museum.floorplan.Floor;
import museum.floorplan.Museum;
import museum.floorplan.Room;

public class MuseumDAO extends DAO<Museum> {
	
	private static final String TABLE = "museum";
	private static final String PK = "id_museum";
	private static final String NAME = "museum_name";
	
	private static MuseumDAO instance=null;

	public static MuseumDAO getInstance(){
		if (instance==null){
			instance = new MuseumDAO();
		}
		return instance;
	}

	private MuseumDAO() {
		super();
	}

	@Override
	public boolean create(Museum museum) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+NAME+") VALUES (?)";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, museum.getMuseum_name());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				museum.setId_museum(rs.getInt(1));
			}
			data.put(museum.getId_museum(), museum);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean delete(Museum museum) {
		boolean success = true;
		try {
			int id_museum = museum.getId_museum();
			String requete = "DELETE FROM "+TABLE+" WHERE "+PK+" = ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete);
			pst.setInt(1, id_museum);
			pst.executeUpdate();
			data.remove(id_museum);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean update(Museum museum) {
		boolean success = true;
		try {
			String requete = "UPDATE "+TABLE+" SET "+NAME+"= ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, museum.getMuseum_name());
			pst.executeUpdate();
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Museum read(int id) {
		Museum museum = null;
		if (data.containsKey(id)) {
			museum=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String museum_name = rs.getString(NAME);				
				museum = new Museum(id, museum_name);
				data.put(id, museum);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return museum;
	}
	
	public List<Museum> readAll() {
		List<Museum> museums = new ArrayList<Museum>();
		Museum museum = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_museum = rs.getInt(1);
				museum = MuseumDAO.getInstance().read(id_museum);
				museums.add(museum);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return museums;	
	}
	
	public List<Floor> getMuseumFloors(int id_museum) {
		List<Floor> floors = new ArrayList<Floor>();
		Floor floor = null;
		try {
			String requete = "SELECT * FROM floor WHERE ref_museum= " + id_museum;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_floor = rs.getInt(1);
				floor = FloorDAO.getInstance().read(id_floor);
				floors.add(floor);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return floors;
	}
	
	public List<Room> getMuseumRooms(int id_museum) {
		List<Room> rooms = new ArrayList<Room>();
		Room room = null;
		try {
			String requete = "SELECT * FROM room r JOIN floor f ON (r.ref_floor = f.id_floor) WHERE f.ref_museum= " + id_museum;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_room = rs.getInt(1);
				room = RoomDAO.getInstance().read(id_room);
				rooms.add(room);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return rooms;
	}
}
