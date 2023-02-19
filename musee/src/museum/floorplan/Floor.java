package museum.floorplan;

public class Floor {
	private int id_floor;
	private String floor_name;
	private int dim_x;
	private int dim_y;
	private Museum museum;
	
	/**
	 * constructor for Floor
	 * @param id_floor
	 * @param floor_name
	 * @param museum
	 */
	public Floor(int id_floor, int dim_x, int dim_y, String floor_name, Museum museum) {
		this.setId_floor(id_floor);
		this.floor_name = floor_name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.museum = museum;
	}

	public int getId_floor() {
		return id_floor;
	}

	public void setId_floor(int id_floor) {
		this.id_floor = id_floor;
	}

	public String getFloor_name() {
		return floor_name;
	}
	
	public int getDim_x() {
		return dim_x;
	}

	public int getDim_y() {
		return dim_y;
	}

	public Museum getMuseum() {
		return museum;
	}
	
	@Override
	public String toString() {
		return "Floor [id=" + id_floor + ", name=" + floor_name + ", dim_x=" + dim_x + ", dim_y=" + dim_y +
				", museum=" + museum.getMuseum_name() + "]";
	}


}
