package museum.floorplan;

public class Museum {
	private int id_museum;
	private String museum_name;
	
	public void setMuseum_name(String museum_name) {
		this.museum_name = museum_name;
	}

	/**
	 * constructor for Museum if id_museum is known
	 * @param id_museum
	 * @param museum_name
	 */
	public Museum(int id_museum, String museum_name) {
		this.id_museum = id_museum;
		this.museum_name = museum_name;
	}
	
	/**
	 * constructor for Museum if id_museum is unknown
	 * @param id_museum
	 * @param museum_name
	 */
	public Museum(String museum_name) {
		this.museum_name = museum_name;
	}

	public void setId_museum(int id_museum) {
		this.id_museum = id_museum;
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
