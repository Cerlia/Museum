package museum.floorplan;

public class SurfaceType {

	private int id_surface_type;
	private String name;
	private boolean can_have_door;
	
	/**
	 * constructor for SurfaceType
	 * @param id_surface_type
	 * @param name
	 * @param can_have_door
	 */
	public SurfaceType(int id_surface_type, String name, boolean can_have_door) {
		this.id_surface_type = id_surface_type;
		this.name = name;
		this.can_have_door = can_have_door;
	}

	public int getId_surface_type() {
		return id_surface_type;
	}

	public String getName() {
		return name;
	}

	public boolean isCan_have_door() {
		return can_have_door;
	}
	
	@Override
	public String toString() {
		return "SurfaceType [id=" + id_surface_type + ", name=" + name + ", canHaveDoors=" + can_have_door + "]";
	}
}
