package museum.art;

import museum.display.Display;

public class Art {
	private int id_art;
	private String art_code;
	private String art_title;
	private String creation_date;
	private String materials;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private byte[] image;
	private Author author;
	private boolean owner;
	
	public boolean isOwner() {
		return owner;
	}

	public void setArt_code(String art_code) {
		this.art_code = art_code;
	}

	public void setArt_title(String art_title) {
		this.art_title = art_title;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
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

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setArt_status(ArtStatus art_status) {
		this.art_status = art_status;
	}

	public void setArt_type(ArtType art_type) {
		this.art_type = art_type;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	private ArtStatus art_status;
	private ArtType art_type;
	private Display display;
	
	/**
	 * constructor for Art if id_art is known
	 * @param id_art
	 * @param art_code
	 * @param art_title
	 * @param creation_date
	 * @param materials
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param image
	 * @param author
	 * @param art_status
	 * @param art_type
	 * @param display
	 * @param owner
	 */
	public Art(int id_art, String art_code, String art_title, String creation_date,
			String materials, int dim_x, int dim_y, int dim_z, byte[] image, Author author,
			ArtStatus art_status, ArtType art_type, Display display, boolean owner) {
		this.id_art = id_art;
		this.art_code = art_code;
		this.art_title = art_title;
		this.creation_date = creation_date;
		this.materials = materials;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.image = image;
		this.author = author;
		this.art_status = art_status;
		this.art_type = art_type;
		this.display = display;
		this.owner = owner;
	}
	
	/**
	 * constructor for Art if id_art is unknown
	 * @param art_code
	 * @param art_title
	 * @param creation_date
	 * @param materials
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param image
	 * @param author
	 * @param art_status
	 * @param art_type
	 * @param display
	 * @param owner
	 */
	public Art(String art_code, String art_title, String creation_date, String materials,
			int dim_x, int dim_y, int dim_z, byte[] image, Author author, ArtStatus art_status,
			ArtType art_type, Display display, boolean owner) {
		this.art_code = art_code;
		this.art_title = art_title;
		this.creation_date = creation_date;
		this.materials = materials;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
		this.dim_z = dim_z;
		this.image = image;
		this.author = author;
		this.art_status = art_status;
		this.art_type = art_type;
		this.display = display;
		this.owner = owner;
	}

	public int getId_art() {
		return id_art;
	}

	public void setId_art(int id_art) {
		this.id_art = id_art;
	}
	
	public String getArt_code() {
		return art_code;
	}

	public String getArt_title() {
		return art_title;
	}

	public String getCreation_date() {
		return creation_date;
	}

	public String getMaterials() {
		return materials;
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
	
	public byte[] getImage() {
		return image;
	}

	public Author getAuthor() {
		return author;
	}

	public ArtStatus getArt_status() {
		return art_status;
	}
	
	public ArtType getArt_type() {
		return art_type;
	}
	
	public Display getDisplay() {
		return display;
	}
	
	@Override
	public String toString() {
		return art_title + " (" + author.getFullName() + ")";
	}
}
