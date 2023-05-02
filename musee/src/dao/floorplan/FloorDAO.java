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
import museum.floorplan.Room;

public class FloorDAO extends DAO<Floor> {
	
	private static final String TABLE = "floor";
	private static final String PK = "id_floor";
	private static final String NAME = "floor_name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	
	private static FloorDAO instance=null;

	public static FloorDAO getInstance(){
		if (instance==null){
			instance = new FloorDAO();
		}
		return instance;
	}

	private FloorDAO() {
		super();
	}

	@Override
	public boolean create(Floor floor) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+NAME+", "+DIMX+", "+DIMY+") VALUES (?, ?, ?)";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, floor.getFloor_name());
			pst.setInt(2, floor.getDim_x());
			pst.setInt(3, floor.getDim_y());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				floor.setId_floor(rs.getInt(1));
				floor.setRooms(RoomDAO.getInstance().readAllRoomsOfFloor(rs.getInt(1)));
			}
			data.put(floor.getId_floor(), floor);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean delete(Floor floor) {
		boolean success = true;
		try {
			int id_floor = floor.getId_floor();
			String requete = "DELETE FROM "+TABLE+" WHERE "+PK+" = ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete);
			pst.setInt(1, id_floor);
			pst.executeUpdate();
			data.remove(id_floor);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean update(Floor floor) {
		boolean success = true;
		try {
			String requete = "UPDATE "+TABLE+" SET "+NAME+"= ?,"+DIMX+"= ?,"+DIMY+"= ? WHERE "+PK+"= ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, floor.getFloor_name());
			pst.setInt(2, floor.getDim_x());
			pst.setInt(3, floor.getDim_y());
			pst.setInt(4, floor.getId_floor());
			pst.executeUpdate();
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Floor read(int id) {
		Floor floor = null;
		if (data.containsKey(id)) {
			floor=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();				
				String name = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				// on crée une liste de salles, vide
				List<Room> rooms = new ArrayList<Room>();
				// on crée l'objet Floor, avec cette liste vide en tant que liste de salles
				floor = new Floor(id, name, dim_x, dim_y, rooms);		
				// on stocke l'objet dans la hashmap, ou plus précisément son adresse
				data.put(id, floor);
				// dans l'objet rooms (référence à son adresse en mémoire plus exactement),
				// on ajoute toutes les salles présentes dans cet étage.
				// Pas besoin de refaire un put ! Ce sont des références qui sont stockées
				// dans la hashmap. La référence n'a pas changé, seulement le contenu de rooms
				rooms.addAll(RoomDAO.getInstance().readAllRoomsOfFloor(id));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return floor;
	}

	public List<Floor> readAll() {
		List<Floor> floors = new ArrayList<Floor>();
		Floor floor = null;
		try {			
			String requete = "SELECT * FROM " + TABLE + " ORDER BY " + NAME;
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
}
