package museum.floorplan;

public class Museum {
	private int id_museum;
	private String museum_name;
	
	/**
	 * constructor for Museum
	 * @param id_museum
	 * @param museum_name
	 */
	public Museum(int id_museum, String museum_name) {
		this.id_museum = id_museum;
		this.museum_name = museum_name;
	}

	public int getId_museum() {
		return id_museum;
	}
	
	public String getMuseum_name() {
		return museum_name;
	}
	
	@Override
	public String toString() {
		return "Museum [id=" + id_museum + ", name=" + museum_name + "]";
	}
}
