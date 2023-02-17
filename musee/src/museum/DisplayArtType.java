package museum;

public class DisplayArtType {
	private int id_display_art;
	private DisplayType display_type;
	private ArtType art_type;
	
	/**
	 * constructor for DisplayArtType
	 * @param display_type
	 * @param art_type
	 */
	public DisplayArtType(int id_display_art, DisplayType display_type, ArtType art_type) {
		this.id_display_art = id_display_art;
		this.display_type = display_type;
		this.art_type = art_type;
	}
	
	public int getId_display_art() {
		return id_display_art;
	}

	public DisplayType getDisplay_type() {
		return display_type;
	}

	public ArtType getArt_type() {
		return art_type;
	}
	
	@Override
	public String toString() {
		return "CompatibilitéDisplayArt [id=" + id_display_art + "displayTypeId=" + display_type.getId_display_type() +
				", objectTypeId=" + art_type.getId_Art_type() + "]";
	}
}
