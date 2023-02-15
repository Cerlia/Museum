package museum;

public class Zone {
	private int id_zone;
	private String name;
	private int dim_x;
	private int dim_y;
	private Room room;
	
	public Zone(int id_zone, String name, int dim_x, int dim_y, Room room) {
		this.id_zone = id_zone;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.room = room;
	}

	public int getId_zone() {
		return id_zone;
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

	public Room getRoom() {
		return room;
	}
	
	@Override
	public String toString() {
		return "Zone [id=" + id_zone + ", nom=" + name + ", dim_x=" + dim_x + ", dim_y=" + dim_y +
				", roomId=" + room.getId_room() + "]";
	}
}
