package museum.floorplan;

import java.util.List;

public class Floor {
	private int id_floor;
	private String floor_name;
	private int dim_x;
	private int dim_y;
	private List<Room> rooms;
	
	/**
	 * constructor for Floor if id_floor is known
	 * @param id_floor
	 * @param floor_name
	 */
	public Floor(int id_floor, String floor_name, int dim_x, int dim_y, List<Room> rooms) {
		this.setId_floor(id_floor);
		this.floor_name = floor_name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.rooms = rooms;
	}
	
	/**
	 * constructor for Floor if id_floor is unknown
	 * @param id_floor
	 * @param floor_name
	 */
	public Floor(String floor_name, int dim_x, int dim_y, List<Room> rooms) {
		this.floor_name = floor_name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.rooms = rooms;
	}

	public int getId_floor() {
		return id_floor;
	}

	public void setId_floor(int id_floor) {
		this.id_floor = id_floor;
	}

	public String getFloor_name() {
		return floor_name;
	}
	
	public int getDim_x() {
		return dim_x;
	}

	public int getDim_y() {
		return dim_y;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public int getRoom_nb() {
		return getRooms().size();
	}
	
	@Override
	public String toString() {
		return floor_name;
	}
}
