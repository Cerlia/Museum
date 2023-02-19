package museum.display;

import museum.floorplan.SurfaceType;

public class DisplaySurfaceType {
	private int id_display_surface;
	private SurfaceType surface_type;
	private DisplayType display_type;
	
	public DisplaySurfaceType(int id_display_surface, SurfaceType surface_type, DisplayType display_type) {
		this.setId_display_surface(id_display_surface);
		this.surface_type = surface_type;
		this.display_type = display_type;
	}

	public int getId_display_surface() {
		return id_display_surface;
	}

	public void setId_display_surface(int id_display_surface) {
		this.id_display_surface = id_display_surface;
	}

	public SurfaceType getSurface_type() {
		return surface_type;
	}

	public DisplayType getDisplay_type() {
		return display_type;
	}
	
	@Override
	public String toString() {
		return "CompatibilitéDisplaySurface [id=" + id_display_surface + "displayTypeId=" + display_type.getId_display_type() +
				", surfaceTypeId=" + surface_type.getId_surface_type() + "]";
	}
}
