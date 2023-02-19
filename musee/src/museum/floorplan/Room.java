package museum.floorplan;

public class Room {
	private int id_room;
	private String name;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private int pos_x;
	private int pos_y;
	private Floor floor;
	
	/**
	 * constructor for Room if id_room is known
	 * @param id_room
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param pos_x
	 * @param pos_y
	 * @param floor
	 */
	public Room(int id_room, String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y, Floor floor) {
		this.id_room= id_room;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.floor = floor;
	}
	
	/**
	 * constructor for Room if id_room is unknown
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param pos_x
	 * @param pos_y
	 * @param floor
	 */
	public Room(String name, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y, Floor floor) {
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.floor = floor;
	}

	public int getId_room() {
		return id_room;
	}
	
	public void setId_room(int id_room) {
		this.id_room = id_room;
	}

	public String getName() {
		return name;
	}

	public int getDim_x() {
		return dim_x;
	}

	public int getDim_y() {
		return dim_y;
	}

	public int getDim_z() {
		return dim_z;
	}

	public int getPos_x() {
		return pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}
	
	public Floor getFloor() {
		return floor;
	}
	
	@Override
	public String toString() {
		return "Room [id=" + id_room + ", nom=" + name + ", étage=" + floor.getId_floor() + ", dim_x=" + dim_x +
				", dim_y=" + dim_y + ", dim_z=" + dim_z + ", pos_x=" + pos_x + ", pos_y=" + pos_y + "]";
	}
}
