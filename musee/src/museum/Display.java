package museum;

public class Display {
	private int id_display;
	private String name;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private Zone zone;
	private DisplayType display_type;
	
	/**
	 * constructor for Display
	 * @param id_display
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param zone
	 * @param display_type
	 */
	public Display(int id_display, String name, int dim_x, int dim_y, int dim_z, Zone zone, DisplayType display_type) {
		this.id_display = id_display;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.zone = zone;
		this.display_type = display_type;
	}

	public int getId_display() {
		return id_display;
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

	public Zone getZone() {
		return zone;
	}
	
	public DisplayType getDisplay_type() {
		return display_type;
	}
	
	@Override
	public String toString() {
		return "Display [id=" + id_display + ", nom=" + name + ", dim_x=" + dim_x +
				", dim_y=" + dim_y + ", dim_z=" + dim_z +  ", zoneId=" + zone.getId_zone() +
				", displayTypeID=" + display_type.getId_display_type() + "]";
	}
}
