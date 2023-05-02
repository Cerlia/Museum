package museum.art;

public class Author {
	private int id_author;
	private String last_name;
	private String first_name;
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setAdditional_name(String additional_name) {
		this.additional_name = additional_name;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	private String additional_name;
	private String dates;
	
	/**
	 * constructor for Author if id_author is known
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
	
	/**
	 * constructor for Author if id_author is unknown
	 * @param last_name
	 * @param first_name
	 * @param additional_name
	 * @param dates
	 */
	public Author(String last_name, String first_name, String additional_name, String dates) {
		this.last_name = last_name;
		this.first_name = first_name;
		this.additional_name = additional_name;
		this.dates = dates;
	}

	public int getId_author() {
		return id_author;
	}
	
	public void setId_author(int id) {
		this.id_author = id;		
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
	
	public String getFullName() {
		String fullName = this.last_name + " " + this.first_name;
		if (!this.additional_name.equals("")) {
			fullName += ", dit " + this.additional_name;
		}
		return fullName;
	}
	
	@Override
	public String toString() {
		return last_name + " " + first_name;
	}
}
