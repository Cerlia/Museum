package dao.art;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.Connect;
import dao.DAO;
import dao.display.DisplayDAO;
import museum.art.Art;
import museum.art.ArtStatus;
import museum.art.ArtType;
import museum.art.Author;
import museum.display.Display;

public class ArtDAO extends DAO<Art> {
	
	private static final String TABLE = "art";
	private static final String PK = "id_art";
	private static final String TYPE = "ref_art_type";
	private static final String AUTHOR = "ref_author";
	private static final String STATUS = "ref_art_status";
	private static final String CODE = "art_code";
	private static final String TITLE = "art_title";
	private static final String DATE = "creation_date";
	private static final String MATERIALS = "materials";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String DIMZ = "dim_z";
	private static final String IMAGE = "image";
	private static final String DISPLAY = "ref_display";
	private static final String OWNER = "owner";
	
	private static ArtDAO instance=null;
	
	// hashmap dégradé pour stocker les œuvres sans l'image
	private final HashMap<Integer, Art> dataLight = new HashMap<Integer, Art>();
	
	public static ArtDAO getInstance(){
		if (instance==null){
			instance = new ArtDAO();
		}
		return instance;
	}
	
	private ArtDAO() {
		super();
	}

	@Override
	public boolean create(Art art) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+TYPE+", "+AUTHOR+", "+STATUS+", "+CODE+
					", "+TITLE+", "+DATE+", "+ MATERIALS+", "+DIMX+", "+DIMY+", "+DIMZ+ ", "+
					IMAGE +", "+DISPLAY+", "+OWNER+") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, art.getArt_type().getId_Art_type());
			pst.setInt(2, art.getAuthor().getId_author());
			pst.setInt(3, art.getArt_status().getId_art_status());
			pst.setString(4, art.getArt_code());
			pst.setString(5, art.getArt_title());
			pst.setString(6, art.getCreation_date());
			pst.setString(7, art.getMaterials());
			pst.setInt(8, art.getDim_x());
			pst.setInt(9, art.getDim_y());
			pst.setInt(10, art.getDim_z());
			pst.setBytes(11, art.getImage());
			pst.setNull(12, Types.INTEGER);
			pst.setBoolean(13, art.isOwner());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				art.setId_art(rs.getInt(1));
			}
			data.put(art.getId_art(), art);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;		
	}

	@Override
	public boolean delete(Art art) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Art art) {		
		boolean success = true;
		try {
			String requete = "UPDATE "+TABLE+" SET "+ TYPE+"=?, "+AUTHOR+"=?, "+STATUS+
					"=?, "+CODE+"=?, "+TITLE+"=?, "+DATE+"=?, "+MATERIALS+"=?, "+DIMX+"=?, "+
					DIMY+"=?, "+DIMZ+"=?, "+IMAGE+"=?, "+DISPLAY+"=?, "+OWNER+"=? WHERE "+PK+"= ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, art.getArt_type().getId_Art_type());
			pst.setInt(2, art.getAuthor().getId_author());
			pst.setInt(3, art.getArt_status().getId_art_status());
			pst.setString(4, art.getArt_code());
			pst.setString(5, art.getArt_title());
			pst.setString(6, art.getCreation_date());
			pst.setString(7, art.getMaterials());
			pst.setInt(8, art.getDim_x());
			pst.setInt(9, art.getDim_y());
			pst.setInt(10, art.getDim_z());
			pst.setBytes(11, art.getImage());			
			if (art.getDisplay() != null) {
				pst.setInt(12, art.getDisplay().getId_display());
			} else {
				pst.setNull(12, Types.INTEGER);
			}
			pst.setBoolean(13, art.isOwner());
			pst.setInt(14, art.getId_art());
			pst.executeUpdate();
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * lit une œuvre en BD dans sa totalité (inclut l'image)
	 */
	@Override
	public Art read(int id) {
		Art art = null;
		if (data.containsKey(id) && data.get(id).getImage() != null) {
			art=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_art_type = rs.getInt(TYPE);
				int ref_author = rs.getInt(AUTHOR);
				int ref_art_status = rs.getInt(STATUS);
				String code = rs.getString(CODE);
				String title = rs.getString(TITLE);
				String date = rs.getString(DATE);
				String materials = rs.getString(MATERIALS);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				byte[] image = rs.getBytes(IMAGE);
				int ref_display = rs.getInt(DISPLAY);
				boolean owner = rs.getBoolean(OWNER);
				Display display = null;
				if (ref_display != 0) {
					display = DisplayDAO.getInstance().read(ref_display);
				}
				ArtType art_type = ArtTypeDAO.getInstance().read(ref_art_type);
				Author author = AuthorDAO.getInstance().read(ref_author);
				ArtStatus art_status = ArtStatusDAO.getInstance().read(ref_art_status);
				art = new Art(id, code, title, date, materials, dim_x, dim_y, dim_z,
						image, author, art_status, art_type, display, owner);
				data.put(id, art);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return art;
	}
	
	public Art readLight(int id_art) {
		Art art = null;
		if (data.containsKey(id_art)) {
			art=data.get(id_art);
		}
		else {
			art=readLightFromDB(id_art);
		}
		return art;
	}
	
	/**
	 * lit une œuvre en BD en version allégée (sans l'image)
	 */
	public Art readLightFromDB(int id) {
		Art artLight = null;
		if (dataLight.containsKey(id)) {
			artLight=dataLight.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				int ref_art_type = rs.getInt(TYPE);
				int ref_author = rs.getInt(AUTHOR);
				int ref_art_status = rs.getInt(STATUS);
				String code = rs.getString(CODE);
				String title = rs.getString(TITLE);
				String date = rs.getString(DATE);
				String materials = rs.getString(MATERIALS);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				int ref_display = rs.getInt(DISPLAY);
				boolean owner = rs.getBoolean(OWNER);
				Display display = null;
				if (ref_display != 0) {
					display = DisplayDAO.getInstance().read(ref_display);
				}
				ArtType art_type = ArtTypeDAO.getInstance().read(ref_art_type);
				Author author = AuthorDAO.getInstance().read(ref_author);
				ArtStatus art_status = ArtStatusDAO.getInstance().read(ref_art_status);
				artLight = new Art(id, code, title, date, materials, dim_x, dim_y, dim_z,
						null, author, art_status, art_type, display, owner);
				dataLight.put(id, artLight);
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}	
		return artLight;
	}
	
	/**
	 * retourne la liste des œuvres d'un présentoir donné
	 * @param id_display
	 * @return
	 */
	public List<Art> readAllArtsOfDisplay(int id_display) {
		List<Art> arts = new ArrayList<Art>();
		Art art = null;
		try {			
			String requete = "SELECT * FROM " + TABLE +" WHERE "+DISPLAY+"="+id_display;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLight(id_art);
				arts.add(art);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return arts;
	}
	
	/**
	 * recharge la liste des œuvres d'un présentoir donné
	 * @param id_room
	 * @return
	 */
	public List<Art> reloadArtsOfDisplay(int id_display) {
		List<Art> arts = new ArrayList<Art>();
		Art art = null;
		try {
			String requete = "SELECT * FROM " + TABLE +" WHERE "+DISPLAY+"="+id_display;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLightFromDB(id_art);
				arts.add(art);
			}
		} catch (SQLException e) {
		// e.printStackTrace();
		System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return arts;
	}
	
	/**
	 * retourne la liste des œuvres d'un étage donné
	 * @param id_floor
	 * @return
	 */
	public List<Art> readAllArtsOfFloor(int id_floor) {
		List<Art> arts = new ArrayList<Art>();
		Art art = null;
		try {			
			String requete = "SELECT a."+PK+" FROM "+TABLE+" a JOIN display d ON (a.ref_display = d.id_display)"
					+ " JOIN surface s ON (s.id_surface = d.ref_surface)"
					+ " JOIN room r ON (r.id_room = s.ref_room)"
					+ " WHERE r.ref_floor=" + id_floor;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLight(id_art);
				arts.add(art);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return arts;
	}
	
	/**
	 * retourne la liste des œuvres d'une salle donnée
	 * @param id_room
	 * @return
	 */
	public List<Art> readAllArtsOfRoom(int id_room) {
		List<Art> arts = new ArrayList<Art>();
		Art art = null;
		try {			
			String requete = "SELECT a."+PK+" FROM "+TABLE+" a JOIN display d ON (a.ref_display = d.id_display)"
					+ " JOIN surface s ON (s.id_surface = d.ref_surface)"
					+ " JOIN room r ON (r.id_room = s.ref_room)"
					+ " WHERE r.id_room=" + id_room;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLight(id_art);
				arts.add(art);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return arts;
	}
	
	/**
	 * retourne la liste des œuvres d'une surface donnée
	 * @param id_room
	 * @return
	 */
	public List<Art> readAllArtsOfSurface(int id_surface) {
		List<Art> arts = new ArrayList<Art>();
		Art art = null;
		try {			
			String requete = "SELECT a."+PK+" FROM "+TABLE+" a JOIN display d ON (a.ref_display = d.id_display)"
					+ " JOIN surface s ON (s.id_surface = d.ref_surface)"
					+ " WHERE s.id_surface=" + id_surface;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLight(id_art);
				arts.add(art);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return arts;
	}
	
	public List<Art> readAll() {
		List<Art> art_objects = new ArrayList<Art>();
		Art art = null;
		try {			
			String requete = "SELECT * FROM " + TABLE + " ORDER BY "+CODE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art = rs.getInt(1);
				art = ArtDAO.getInstance().readLight(id_art);
				art_objects.add(art);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return art_objects;		
	}
}
