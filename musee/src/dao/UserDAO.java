package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import museum.Role;
import museum.User;

public class UserDAO extends DAO<User> {
	
	private static final String TABLE = "museum_user";
	private static final String PK = "id_user";
	private static final String ROLE = "ref_role";
	private static final String NOM = "last_name";
	private static final String PRENOM = "first_name";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	
	private static UserDAO instance=null;

	public static UserDAO getInstance(){
		if (instance==null){
			instance = new UserDAO();
		}
		return instance;
	}

	private UserDAO() {
		super();
	}	
	
	// Not used
	@Override
	public boolean create(User obj) {
		return false;
	}

	// Not used
	@Override
	public boolean delete(User obj) {
		return false;
	}

	// Not used
	@Override
	public boolean update(User obj) {
		return false;
	}

	@Override
	public User read(int id) {
		User user = null;
		if (data.containsKey(id)) {
			user = data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();				
				String nom = rs.getString(NOM);
				String prenom = rs.getString(PRENOM);
				int ref_role = rs.getInt(ROLE);				
				String login = rs.getString(LOGIN);
				String pw = rs.getString(PASSWORD);
				Role role = RoleDAO.getInstance().read(ref_role);
				user = new User(id, nom, prenom, login, pw, role);
				data.put(id, user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public List<User> readAll(String login, byte[] hash) {
		List<User> users = new ArrayList<User>();
		User user = null;
		try {			
			String requete = "SELECT * FROM "+TABLE+" WHERE "+LOGIN+"=?"
					+ " AND " +PASSWORD+"=?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete);
			pst.setString(1, login);
			pst.setBytes(2, hash);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int id_user = rs.getInt(1);
				user = UserDAO.getInstance().read(id_user);
				users.add(user);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return users;
	}
}
