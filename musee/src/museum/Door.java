package museum;

public class Door {
	private int id_door;
	private int dim_x;
	private int dim_z;
	private int pos_x;
	private int pos_y;
	private Room room;
	
	public Door(int id_door, int dim_x, int dim_z, int pos_x, int pos_y, Room room) {
		this.id_door = id_door;
		this.dim_x = dim_x;
		this.dim_z = dim_z;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.room = room;
	}

	public int getId_door() {
		return id_door;
	}

	public int getDim_x() {
		return dim_x;
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

	public Room getRoom() {
		return room;
	}
	
	@Override
	public String toString() {
		return "Door [id=" + id_door + ", dim_x=" + dim_x + ", dim_z=" + dim_z + ", pos_x=" + pos_x +
				", pos_y=" + pos_y + ", roomId=" + room.getId_room() + "]";
	}
}
