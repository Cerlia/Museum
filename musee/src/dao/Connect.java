package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class Connect {

	private static Connection connection = null;
	
	private static final String ID = "museum";
	private static final String MDP = "admin";

	/**
	 * Patron de conception Singleton
	 * @return l'instance unique de connexion
	 */
	public static Connection getInstance() {
		if (connection==null) {
			try { 
				SQLServerDataSource ds = new SQLServerDataSource();
				ds.setUser(ID);
				ds.setPassword(MDP);
				ds.setServerName("localhost\\SQLEXPRESS");
				ds.setDatabaseName("museum-full");
				connection = ds.getConnection();
				System.out.println("connecté");
			}
			catch (SQLException e){
				System.out.println("Echec de la tentative de connexion : " + e.getMessage() + e.getStackTrace()) ;
			}
		}
		return connection;
	}
	
	private Connect() {
		super();
	}

	
	// TODO revoir le fichier à partir d'ici, je sais pas à quoi tout sert
	
	public static ResultSet executeQuery(String requete){
		Statement st = null ;
		ResultSet rs = null;
		//System.out.println("requete = "+requete);
		try{
			st = getInstance().createStatement() ;
			rs = st.executeQuery(requete) ;
		}catch(SQLException e){
			System.out.println("Echec de la tentative d'exécution de requete : " +requete + " ["+ e.getMessage()+"]") ;
		}
		return rs;
	}

	/**
	 * Une m�thode statique qui permet de faire une mise � jour d'une table (INSERT / UPDATE / DELETE)
	 * @param requete
	 * @return
	 */
	public static boolean executeUpdate(String requete){
		boolean succes = true;
		//System.out.println(requete);
		Statement st = null ;
		try{
			st = getInstance().createStatement() ;
			st.executeUpdate(requete) ;
		}catch(SQLException e){
			succes = false;
			System.out.println("Echec de la tentative d'ex�cution de Mise � Jour : " +requete + " ["+ e.getMessage()+"]") ;
		}
		return succes;
	}

	/**
	 * Fermeture de la connexion au SGBD SQL ServerExpress
	 */
	public static void fermer()
	{
		try
		{
			getInstance().close();
			System.out.println("deconnexion ok");
		}
		catch (SQLException e)
		{
			System.out.println("echec de la fermeture");
		}
		connection=null;
	}

	
	/* TODO vérifier si ces méthodes servent vraiment, j'ai l'impression qu'elles
	 * ne sonts appelées par aucune fonction dans le projet du prof !
	 * 
	 * 

	 * Requ�te qui permet de r�cup�rer le plus grand id de la table, a priori celui qui vient d'�tre affect�
	 * � une ligne cr��e via identity.
	 * @param cle
	 * @param table
	 * @return
	public static int getMaxId(String cle, String table) {
		String requete = "SELECT MAX("+cle+")as max FROM "+table;
		ResultSet rs = Connect.executeQuery(requete);
		int id= -1;
		try {
			rs.next();
			id = rs.getInt("max");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	


	public static List<Integer> getLesIds(String attribut, String table, String clauseWhere) {
		String requete = "SELECT DISTINCT "+attribut+" FROM "+table;
		if (clauseWhere!=null) {
			requete += " WHERE "+clauseWhere;
		}		
		ResultSet rs = Connect.executeQuery(requete);
		List<Integer> liste = new ArrayList<Integer>();
		try {
			while (rs.next()) {
			int id = rs.getInt(attribut);
			liste.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;		
	}	
	
		 */
	
}
