package museum.art;

public class Art {
	private int id_art;
	private String art_code;
	private String art_title;
	private String creation_date;
	private String materials;
	private int dim_x;
	private int dim_y;
	private int dim_z;
	private String image;
	private Author author;
	private ArtStatus art_status;
	private ArtType art_type;
	
	/**
	 * constructor for Art
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
	 */
	public Art(int id_art, String art_code, String art_title, String creation_date, String materials,
			int dim_x, int dim_y, int dim_z, String image, Author author, ArtStatus art_status, ArtType art_type) {
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
	}

	public int getId_art() {
		return id_art;
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
	
	public String getImage() {
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
	
	@Override
	public String toString() {
		return "Object [id=" + id_art + ", code=" + art_code + ", titre=" + art_title +
				", date=" + creation_date + ", matériaux=" + materials + ", dim_x=" + dim_x +
				", dim_y=" + dim_y + ", dim_z=" + dim_z + ", imageSrc=" + image + ", auteur=" + author.getLast_name() +
				", nomStatut=" + art_status.getName() + ", typeObjet=" + art_type.getName() + "]";
	}
}
