package museum.floorplan;

public class SurfaceType {

	private int id_surface_type;
	private String name;
	
	public void setId_surface_type(int id_surface_type) {
		this.id_surface_type = id_surface_type;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * constructor for SurfaceType
	 * @param id_surface_type
	 * @param name
	 */
	public SurfaceType(int id_surface_type, String name) {
		this.id_surface_type = id_surface_type;
		this.name = name;
	}

	public int getId_surface_type() {
		return id_surface_type;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "SurfaceType [id=" + id_surface_type + ", name=" + name + "]";
	}
}
