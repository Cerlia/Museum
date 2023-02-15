package museum;

public class Author {
	private int id_author;
	private String last_name;
	private String first_name;
	private String additional_name;
	private String dates;
	
	/**
	 * constructor for Author
	 * @param id_author
	 * @param last_name
	 * @param first_name
	 * @param additional_name
	 * @param dates
	 */
	public Author(int id_author, String last_name, String first_name, String additional_name, String dates) {
		this.id_author = id_author;
		this.last_name = last_name;
		this.first_name = first_name;
		this.additional_name = additional_name;
		this.dates = dates;
	}

	public int getId_author() {
		return id_author;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getAdditional_name() {
		return additional_name;
	}

	public String getDates() {
		return dates;
	}
	
	@Override
	public String toString() {
		return "Author [id=" + id_author + ", nom=" + last_name + ", prénom=" + first_name +
				", surnom=" + additional_name + ", dates=" + dates + "]";
	}
}
