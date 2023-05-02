package museum.display;

public class DisplayModel {
	private int id_display_model;
	private String name;
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

	public void setDisplay_multiple(boolean display_multiple) {
		this.display_multiple = display_multiple;
	}

	public void setDisplay_type(DisplayType display_type) {
		this.display_type = display_type;
	}

	private int dim_x;
	private int dim_y;
	private int dim_z;
	private boolean display_multiple;
	private DisplayType display_type;
	
	/**
	 * constructor for DisplayModel
	 * @param id_display_model
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param display_multiple
	 * @param display_type
	 */	
	public DisplayModel(int id_display_model, String name, int dim_x, int dim_y, int dim_z,
			boolean display_multiple, DisplayType display_type) {
		this.setId_display_model(id_display_model);
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.display_multiple = display_multiple;
		this.display_type = display_type;		
	}

	public int getId_display_model() {
		return id_display_model;
	}

	public void setId_display_model(int id_display_model) {
		this.id_display_model = id_display_model;
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

	public boolean isDisplay_multiple() {
		return display_multiple;
	}

	public DisplayType getDisplay_type() {
		return display_type;
	}
	
	@Override
	public String toString() {
		return "DisplayModel [id=" + id_display_model + ", name=" + name + ", dim_x=" + dim_x +
				", dim_y=" + dim_y + ", dim_z=" + dim_z + ", multipleDisplay=" + display_multiple +
				", DisplayTypeId=" + display_type.getId_display_type() + "]";
	}
}
