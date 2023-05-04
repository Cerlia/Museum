package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import museum.Role;

public class RoleDAO extends DAO<Role> {
	
	private static final String TABLE = "role";
	private static final String PK = "id_role";
	private static final String NAME = "role_name";
	
	private static RoleDAO instance=null;

	public static RoleDAO getInstance(){
		if (instance==null){
			instance = new RoleDAO();
		}
		return instance;
	}

	private RoleDAO() {
		super();
	}

	// Not used
	@Override
	public boolean create(Role obj) {
		return false;
	}

	// Not used
	@Override
	public boolean delete(Role obj) {
		return false;
	}

	// Not used
	@Override
	public boolean update(Role obj) {
		return false;
	}

	@Override
	public Role read(int id) {
		Role role = null;
		if (data.containsKey(id)) {
			role=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();		
				String nom = rs.getString(NAME);			
				role = new Role(id, nom);				
				data.put(id, role);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return role;
	}
	
	public List<Role> readAll() {
		List<Role> roles = new ArrayList<Role>();
		Role role = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_role = rs.getInt(1);
				role = RoleDAO.getInstance().read(id_role);
				roles.add(role);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return roles;
	}
}
