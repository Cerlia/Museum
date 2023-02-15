package museum;

public class DisplayArtType {
	private DisplayType display_type;
	private ArtType art_type;
	
	public DisplayArtType(DisplayType display_type, ArtType art_type) {
		this.display_type = display_type;
		this.art_type = art_type;
	}

	public DisplayType getDisplay_type() {
		return display_type;
	}

	public ArtType getArt_type() {
		return art_type;
	}
	
	@Override
	public String toString() {
		return "CompatibilitéDisplayArt [displayTypeId=" + display_type.getId_display_type() +
				", objectTypeId=" + art_type.getId_Art_type() + "]";
	}
}
