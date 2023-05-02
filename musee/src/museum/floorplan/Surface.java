package museum.floorplan;

import java.util.List;

import museum.display.Display;

public class Surface {
	private int id_surface;
	private Room room;
	public void setRoom(Room room) {
		this.room = room;
	}

	public void setSurface_type(SurfaceType surface_type) {
		this.surface_type = surface_type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDim_x(int dim_x) {
		this.dim_x = dim_x;
	}

	public void setDim_y(int dim_y) {
		this.dim_y = dim_y;
	}

	public void setDim_z(int dim_z) {
		this.dim_z = dim_z;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setDisplays(List<Display> displays) {
		this.displays = displays;
	}

	private SurfaceType surface_type;
	private String name;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private int number;
	private List<Display> displays;

	/**
	 * constructor for Surface if id_surface is known
	 * @param id_surface
	 * @param room
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param surface_type
	 * @param number
	 * @param displays
	 */
	public Surface(int id_surface, Room room, String name, int dim_x, int dim_y, int dim_z,
			SurfaceType surface_type, int number, List<Display> displays) {
		this.id_surface = id_surface;
		this.room = room;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.surface_type = surface_type;
		this.number = number;
		this.displays = displays;
	}
	
	/**
	 * constructor for Surface if id_surface is unknown
	 * @param id_surface
	 * @param room
	 * @parama name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param surface_type
	 * @param number
	 * @param displays
	 */
	public Surface(Room room, String name, int dim_x, int dim_y, int dim_z, SurfaceType surface_type,
			int number, List<Display> displays) {
		this.room = room;
		this.name = name;
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

	public SurfaceType getSurface_type() {
		return surface_type;
	}
	
	public List<Display> getDisplays() {
		return displays;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
