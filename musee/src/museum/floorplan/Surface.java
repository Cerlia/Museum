package museum.floorplan;

public class Surface {
	private int id_surface;
	private String name;
	private int dim_x;
	private int dim_y;
	private Room room;
	
	/**
	 * constructor for Zone
	 * @param id_zone
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param room
	 */
	public Surface(int id_surface, String name, int dim_x, int dim_y, Room room) {
		this.id_surface = id_surface;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.room = room;
	}

	public int getId_surface() {
		return id_surface;
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
		return "Surface [id=" + id_surface + ", name=" + name + ", dim_x=" + dim_x + ", dim_y=" + dim_y +
				", roomId=" + room.getId_room() + "]";
	}
}
