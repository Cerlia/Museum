package museum;

public class Role {
	private int id_role;
	private String name;
	
	/**
	 * constructor for Role
	 * @param id_role
	 * @param name
	 */
	public Role(int id_role, String name) {
		this.id_role = id_role;
		this.name = name;
	}

	public void setId_role(int id_role) {
		this.id_role = id_role;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId_role() {
		return id_role;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id_role + ", libellé=" + name + "]";
	}
}
