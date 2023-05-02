package museum;

public class User {
	private int id_user;
	private String last_name;
	private String first_name;
	private String login;
	private String password;
	private Role role;
	
	/**
	 * constructor for User
	 * @param id_user
	 * @param last_name
	 * @param first_name
	 * @param login
	 * @param password
	 * @param role
	 */
	public User(int id_user, String last_name, String first_name, String login, String password, Role role) {
		this.id_user = id_user;
		this.last_name = last_name;
		this.first_name = first_name;
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getId_user() {
		return id_user;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public Role getRole() {
		return role;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id_user + ", nom=" + last_name + ", prénom=" + first_name +
				", login=" + login + "]";
	}
}
