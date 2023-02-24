package museum.floorplan;

public class Surface {
	private int id_surface;
	private Room room;
	private SurfaceType surface_type;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private int number;

	
	/**
	 * constructor for Surface if id_surface is known
	 * @param id_surface
	 * @param room
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param surface_type
	 * @param number
	 */
	public Surface(int id_surface, Room room, int dim_x, int dim_y, int dim_z, SurfaceType surface_type, int number) {
		this.id_surface = id_surface;
		this.room = room;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.surface_type = surface_type;
		this.number = number;
	}
	
	/**
	 * constructor for Surface if id_surface is unknown
	 * @param id_surface
	 * @param room
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param surface_type
	 * @param number
	 */
	public Surface(Room room, int dim_x, int dim_y, int dim_z, SurfaceType surface_type, int number) {
		this.room = room;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.surface_type = surface_type;
		this.number = number;
	}

	public int getId_surface() {
		return id_surface;
	}	

	public void setId_surface(int id_surface) {
		this.id_surface = id_surface;
	}
	
	public int getNumber() {
		return number;
	}

	public Room getRoom() {
		return room;
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

	public SurfaceType getSurface_type() {
		return surface_type;
	}
	
	@Override
	public String toString() {
		return "Surface [id=" + id_surface + ", dim_x=" + dim_x + ", dim_y=" + dim_y + ", nb=" + number +
				", roomId=" + room.getName() + ", surface=" + surface_type.getName() + "]";
	}
}
