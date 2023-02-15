package museum;

public class ArtStatus {
	private int id_art_status;
	private String name;
	
	public ArtStatus(int id_art_status, String name) {
		this.id_art_status = id_art_status;
		this.name = name;
	}

	public int getId_art_status() {
		return id_art_status;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "ObjectStatus [id=" + id_art_status + ", libellé=" + name + "]";
	}
}
