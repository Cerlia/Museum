package museum.display;

import museum.floorplan.Surface;

public class Display {
	private int id_display;
	private String name;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private Surface surface;
	private DisplayModel display_model;
	
	/**
	 * constructor for Display
	 * @param id_display
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param surface
	 * @param display_model
	 */
	public Display(int id_display, String name, int dim_x, int dim_y, int dim_z, Surface surface, DisplayModel display_model) {
		this.id_display = id_display;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.surface = surface;
		this.display_model = display_model;
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

	public Surface getSurface() {
		return surface;
	}
	
	public DisplayModel getDisplay_model() {
		return display_model;
	}
	
	@Override
	public String toString() {
		return "Display [id=" + id_display + ", nom=" + name + ", dim_x=" + dim_x +
				", dim_y=" + dim_y + ", dim_z=" + dim_z +  ", zoneId=" + surface.getId_surface() +
				", displayTypeID=" + display_model.getId_display_model() + "]";
	}
}
