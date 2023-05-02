package museum.display;

import museum.art.ArtType;

public class DisplayArtType {
	private int id_display_art;
	public void setId_display_art(int id_display_art) {
		this.id_display_art = id_display_art;
	}

	public void setDisplay_type(DisplayType display_type) {
		this.display_type = display_type;
	}

	public void setArt_type(ArtType art_type) {
		this.art_type = art_type;
	}

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
				", artTypeId=" + art_type.getId_Art_type() + "]";
	}
}
