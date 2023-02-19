package dao.floorplan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.Connect;
import dao.DAO;
import museum.floorplan.Floor;
import museum.floorplan.Museum;

public class FloorDAO extends DAO<Floor> {
	
	private static final String TABLE = "floor";
	private static final String PK = "id_floor";
	private static final String NAME = "floor_name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String MUSEUM = "ref_museum";
	
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
	public boolean create(Floor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Floor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Floor obj) {
		// TODO Auto-generated method stub
		return false;
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
				int ref_museum = rs.getInt(MUSEUM);				
				String name = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				Museum museum = MuseumDAO.getInstance().read(ref_museum);				
				floor = new Floor(id, dim_x, dim_y, name, museum);				
				data.put(id, floor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return floor;
	}

	public List<Floor> readAll() {
		// TODO
		return null;
	}

}
