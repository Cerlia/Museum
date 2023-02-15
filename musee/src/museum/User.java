package museum;

public class User {
	private int id_user;
	private String last_name;
	private String first_name;
	private String login;
	private String password;
	
	public User(int id_user, String last_name, String first_name, String login, String password) {
		this.id_user = id_user;
		this.last_name = last_name;
		this.first_name = first_name;
		this.login = login;
		this.password = password;
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
	
	@Override
	public String toString() {
		return "User [id=" + id_user + ", nom=" + last_name + ", prénom=" + first_name +
				", login=" + login + "]";
	}
}
