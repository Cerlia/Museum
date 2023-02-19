package museum.display;

public class DisplayType {
	private int id_display_type;
	private String name;
	
	/**
	 * constructor for DisplayType
	 * @param id_display_type
	 * @param name
	 */
	public DisplayType(int id_display_type, String name) {
		this.id_display_type = id_display_type;
		this.name = name;
	}

	public int getId_display_type() {
		return id_display_type;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "DisplayType [id=" + id_display_type + ", libellé=" + name + "]";
	}
}
