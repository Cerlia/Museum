package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import museum.Role;
import museum.User;
import museum.art.Art;
import museum.art.ArtStatus;
import museum.art.ArtType;
import museum.art.Author;
import museum.display.Display;
import museum.display.DisplayArtType;
import museum.display.DisplayType;
import museum.floorplan.Door;
import museum.floorplan.Floor;
import museum.floorplan.Museum;
import museum.floorplan.Room;
import museum.floorplan.Surface;
import museum.floorplan.SurfaceType;
import controller.ArchitectFloorControl;
import controller.ArchitectMuseumControl;
import controller.ArchitectRoomControl;
import controller.CuratorArtDataControl;
import controller.CuratorArtMovementControl;
import controller.LoginControl;
import dao.RoleDAO;
import dao.UserDAO;
import dao.art.ArtDAO;
import dao.art.ArtStatusDAO;
import dao.art.ArtTypeDAO;
import dao.art.AuthorDAO;
import dao.display.DisplayArtTypeDAO;
import dao.display.DisplayDAO;
import dao.display.DisplayTypeDAO;
import dao.floorplan.DoorDAO;
import dao.floorplan.FloorDAO;
import dao.floorplan.MuseumDAO;
import dao.floorplan.RoomDAO;
import dao.floorplan.SurfaceDAO;
import dao.floorplan.SurfaceTypeDAO;

public class Main extends Application {
	
	private Stage mainWindow;			// "stage" principal
	private BorderPane mainWindowRoot;	// fenêtre principale
	private Museum currentMuseum;
	private Stage notifWindow = new Stage();   // "stage" des fenêtres de notification
	private Pane dialogFail;
	
	private final String NTF_TITLE_FORM_NG = "Échec de l'enregistrement";
	private final String NTF_BODY_FORM_NG = "Enregistrement impossible : les champs ne sont pas "
			+ "correctement remplis. Vérifiez les données saisies, puis réessayez.";
	private final String NTF_TITLE_SAVE_OK = "Confirmation d'enregistrement";
	
	// sous-fenêtres
	private AnchorPane loginPane = null;
	private AnchorPane architectMuseumPane = null;
	private AnchorPane architectFloorPane = null;
	private AnchorPane architectRoomPane = null;
	private AnchorPane curatorArtDataPane = null;
	private AnchorPane curatorArtMovementPane = null;
	
	// sous-contrôleurs des différentes sous-fenêtres
	private LoginControl loginCtrl = null;
	private ArchitectMuseumControl architectMuseumCtrl = null;
	private ArchitectFloorControl architectFloorCtrl = null;
	private ArchitectRoomControl architectRoomCtrl = null;
	private CuratorArtDataControl curatorArtDataCtrl = null;
	private CuratorArtMovementControl curatorArtMovementCtrl = null;
	
	// observableLists pour manipuler les données
	private ObservableList<Art> artData = FXCollections.observableArrayList();
	private ObservableList<ArtStatus> artStatusData = FXCollections.observableArrayList();
	private ObservableList<ArtType> artTypeData = FXCollections.observableArrayList();
	private ObservableList<Author> authorData = FXCollections.observableArrayList();
	private ObservableList<DisplayArtType> displayArtTypeData = FXCollections.observableArrayList();
	private ObservableList<Display> displayData = FXCollections.observableArrayList();
	private ObservableList<DisplayType> displayTypeData = FXCollections.observableArrayList();
	private ObservableList<Door> doorData = FXCollections.observableArrayList();
	private ObservableList<Floor> floorData = FXCollections.observableArrayList();
	private ObservableList<Museum> museumData = FXCollections.observableArrayList();
	private ObservableList<Role> roleData = FXCollections.observableArrayList();
	private ObservableList<Room> roomData = FXCollections.observableArrayList();
	private ObservableList<User> userData = FXCollections.observableArrayList();	
	private ObservableList<Surface> zoneData = FXCollections.observableArrayList();
	
	// éléments de la vue
	@FXML
	private Button btnQuit;
	@FXML
	private Button btnDisconnect;
	@FXML
	private ImageView imgLogo;
	@FXML
	private Label lblNotification;
	@FXML
	private Label lblRoleStatus;
	@FXML
	private MenuBar menu_bar;
	@FXML
	private Menu curator_menu;
	@FXML
	private Menu architect_menu;


	
	/**
	 * constructeur du contrôleur principal 
	 */
	public Main() {
		super();
		this.roleData = getRoleData();
		this.userData = getUserData();
	}
	
	/*  ---------------------------
	 * 
	 *   MÉTHODES LIEES AU MODÈLE
	 * 
	 *  --------------------------- */
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Art> getArtData() {
		artData = FXCollections.observableArrayList();
		List<Art> art_objects = ArtDAO.getInstance().readAll();
		for (Art art : art_objects) {
			artData.add(art);
		}
		return artData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<ArtStatus> getArtStatusData() {
		artStatusData = FXCollections.observableArrayList();
		List<ArtStatus> art_statuses = ArtStatusDAO.getInstance().readAll();
		for (ArtStatus art_status : art_statuses) {
			artStatusData.add(art_status);
		}
		return artStatusData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<ArtType> getArtTypeData() {
		artTypeData = FXCollections.observableArrayList();
		List<ArtType> art_types = ArtTypeDAO.getInstance().readAll();
		for (ArtType art_type : art_types) {
			artTypeData.add(art_type);
		}
		return artTypeData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Author> getAuthorData() {
		authorData = FXCollections.observableArrayList();
		List<Author> authors = AuthorDAO.getInstance().readAll();
		for (Author author : authors) {
			authorData.add(author);
		}
		return authorData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<DisplayArtType> getDisplayArtTypeData() {
		displayArtTypeData = FXCollections.observableArrayList();
		List<DisplayArtType> display_art_types = DisplayArtTypeDAO.getInstance().readAll();
		for (DisplayArtType display_art_type : display_art_types) {
			displayArtTypeData.add(display_art_type);
		}
		return displayArtTypeData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Display> getDisplayData() {
		displayData = FXCollections.observableArrayList();
		List<Display> displays = DisplayDAO.getInstance().readAll();
		for (Display display : displays) {
			displayData.add(display);
		}
		return displayData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<DisplayType> getDisplayTypeData() {
		displayTypeData = FXCollections.observableArrayList();
		List<DisplayType> display_types = DisplayTypeDAO.getInstance().readAll();
		for (DisplayType display_type : display_types) {
			displayTypeData.add(display_type);
		}
		return displayTypeData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Door> getDoorData() {
		doorData = FXCollections.observableArrayList();
		List<Door> doors = DoorDAO.getInstance().readAll();
		for (Door door : doors) {
			doorData.add(door);
		}
		return doorData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Floor> getFloorData() {
		floorData = FXCollections.observableArrayList();
		List<Floor> floors = FloorDAO.getInstance().readAll();
		for (Floor floor : floors) {
			floorData.add(floor);
		}
		return floorData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Museum> getMuseumData() {
		museumData = FXCollections.observableArrayList();
		List<Museum> museums = MuseumDAO.getInstance().readAll();
		for (Museum museum : museums) {
			museumData.add(museum);
		}
		return museumData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Role> getRoleData() {
		roleData = FXCollections.observableArrayList();
		List<Role> roles = RoleDAO.getInstance().readAll();
		for (Role role : roles) {
			roleData.add(role);
		}
		return roleData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Room> getRoomData() {
		roomData = FXCollections.observableArrayList();
		List<Room> rooms = RoomDAO.getInstance().readAll();
		for (Room room : rooms) {
			roomData.add(room);
		}
		return roomData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<User> getUserData() {
		userData = FXCollections.observableArrayList();
		List<User> users = UserDAO.getInstance().readAll();
		for (User user : users) {
			userData.add(user);
		}
		return userData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Surface> getzoneData() {
		zoneData = FXCollections.observableArrayList();
		List<Surface> zones = SurfaceDAO.getInstance().readAll();
		for (Surface zone : zones) {
			zoneData.add(zone);
		}
		return zoneData;
	}
	
	/**
	 * retourne l'objet Musée correspondant au musée actuel
	 * @return l'objet Musée correspondant au musée actuel
	 */
	public Museum getCurrentMuseum() {
		try {
			return currentMuseum;
		} catch (Exception e){
			return null;
		}
	}
	
	/**
	 * définit le musée sur lequel on travaille actuellement
	 */
	public void setCurrentMuseum() {
		List<Museum> museums = getMuseumData();
		if (!museums.isEmpty()) {
			// dans cette version, il ne peut y avoir qu'un musée
			this.currentMuseum = museums.get(0);
		} else {
			this.currentMuseum = null;
		}
	}
	
	/**
	 * retourne la liste de surfaces liées à une salle (TODO remplacer par un attribut de la classe Room ?)
	 * @param id_room
	 * @return
	 */
	public List<Surface> getRoomSurfaces(int id_room) {
		return SurfaceDAO.getInstance().readRoomSurfaces(id_room);
	}
	
	/**
	 * retourne les informations détaillées d'une œuvre (inclut image)
	 * @param id_art
	 * @return
	 */
	public Art getFullArtData(int id_art) {
		Art fullArt = ArtDAO.getInstance().read(id_art);
		return fullArt;
	}
	
	/**
	 * à la demande d'un des sous-contrôleurs, affiche une notification
	 */
	public void notifyFail(String body) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/NotificationWindow.fxml"));
			// passage de ce contrôleur à la vue
			loader.setController(this);
			dialogFail = (Pane)loader.load();
			// affichage de la fenêtre pop-up
			Scene scene = new Scene(dialogFail);
			notifWindow.setTitle(NTF_TITLE_FORM_NG);
			String message = NTF_BODY_FORM_NG;
			if (!body.equals(null)) {
				message = body;
			}
			lblNotification.setText(message);
			notifWindow.setScene(scene);
			notifWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * transmet à curatorArtDataCtrl la demande de curatorAuthorSelectCtrl de mettre à jour l'auteur d'une œuvre 
	 * @param author
	 */
	public void setAuthor(Author author) {
		curatorArtDataCtrl.setAuthor(author);
	}
		
	/// Méthodes d'ajout dans la base (add)
	
	/**
	 * demande à la DAO d'ajouter un musée dans la base
	 * @param name
	 */
	public void addMuseum(String name) {
		Museum museum = new Museum(name);
		if (MuseumDAO.getInstance().create(museum)) {
			architectMuseumCtrl.notifyMuseumSaved(NTF_TITLE_SAVE_OK, "Le musée été créé.");
			setCurrentMuseum();
		} else {
			// TODO gérer ça avec une exception, ou je sais pas, à voir
			System.out.println("Impossible de créer le musée");
		}
	}
	
	/**
	 * demande à la DAO d'ajouter un étage dans la base
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 */
	public void addFloor(String name, int dim_x, int dim_y) {
		Floor floor = new Floor(name, dim_x, dim_y);
		if (FloorDAO.getInstance().create(floor)) {
			architectFloorCtrl.notifyFloorSaved(NTF_TITLE_SAVE_OK, "L'étage a été créé.");
		} else {
			// TODO gérer ça avec une exception, ou je sais pas, à voir
			System.out.println("Impossible de créer l'étage");
		}
	}
	
	/**
	 * demande à la DAO d'ajouter une salle dans la base
	 * @param name
	 * @param floor
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param pos_x
	 * @param pos_y
	 */
	public void addRoom(String name, Floor floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		Room room = new Room(name, dim_x, dim_y, dim_z, pos_x, pos_y, floor);
		try {
			if (RoomDAO.getInstance().create(room)) {
				// création de la surface de type Sol
				// TODO je sais que c'est le type 2, mais un enum serait sûrement préférable
				// pour récupérer l'info
				SurfaceType surfaceType = SurfaceTypeDAO.getInstance().read(2);
				Surface surface = new Surface(room, dim_x, dim_y, 0, surfaceType, 0);
				if (SurfaceDAO.getInstance().create(surface)) {
					// si la création du sol a réussi, création des surfaces Mur
					for (int i = 1; i < 5; i++) {
						surfaceType = SurfaceTypeDAO.getInstance().read(1);
						if (i%2 == 1) { // mur impair 
							surface = new Surface(room, dim_x, 0, dim_z, surfaceType, i);
						}
						else {          // mur pair
							surface = new Surface(room, dim_y, 0, dim_z, surfaceType, i);
						}
						SurfaceDAO.getInstance().create(surface);
					}
					architectRoomCtrl.notifyRoomSaved(NTF_TITLE_SAVE_OK, "La salle été créée.");
				}
			}			
		} catch (Exception e) {
			System.out.println("Impossible de créer la salle");
		}		
	}
	
	/**
	 * demande à la DAO d'ajouter une œuvre dans la base
	 * @param art_code
	 * @param art_title
	 * @param creation_date
	 * @param materials
	 * @param dim_x
	 * @param dim_y
	 * @param dim_z
	 * @param image
	 * @param author
	 * @param art_status
	 * @param art_type
	 */
	public void addArt(String art_code, String art_title, String creation_date,
			String materials, int dim_x, int dim_y, int dim_z, byte[] image, Author author,
			ArtStatus art_status, ArtType art_type, Display display) {
		// par défaut, une œuvre est placée "En réserve" (id_art_status = 1)
		// et elle n'a pas de présentoir (display = null)
		ArtStatus artStatus = ArtStatusDAO.getInstance().read(1);
		Art art = new Art(art_code, art_title, creation_date, materials, dim_x, dim_y, dim_z, image,
				author, artStatus, art_type, null);
		if (ArtDAO.getInstance().create(art)) {
			curatorArtDataCtrl.notifyArtSaved(NTF_TITLE_SAVE_OK, "L'œuvre a été ajoutée à la base de données");
		}		
	}
		
	/**
	 * demande à la DAO d'ajouter un auteur dans la base
	 * @param last_name
	 * @param first_name
	 * @param additional_name
	 * @param dates
	 */
	public void addAuthor(String last_name, String first_name, String additional_name, String dates) {
		Author author = new Author(last_name, first_name, additional_name, dates);
		if (AuthorDAO.getInstance().create(author)) {
			curatorArtDataCtrl.notifyAuthorSaved(NTF_TITLE_SAVE_OK, "L'auteur a été enregistré enregistré");
		}		
	}
		
	
	/// Méthodes de modification de la base (update)
	
	public void updateMuseum(int id_museum, String museum_name) {
		Museum museum = new Museum(id_museum, museum_name);
		if (MuseumDAO.getInstance().update(museum)) {
			architectMuseumCtrl.notifyMuseumSaved(NTF_TITLE_SAVE_OK, "Le nom du musée a été modifié");
			// mise à jour du musée actuel avec les dernières infos
			setCurrentMuseum();
		}
	}
	
	public void updateFloor(int id_floor, String name, int dim_x, int dim_y) {
		Floor floor = new Floor(id_floor, name, dim_x, dim_y);
		if (FloorDAO.getInstance().update(floor)) {
			architectFloorCtrl.notifyFloorSaved(NTF_TITLE_SAVE_OK, "L'étage a été modifié");
		}	
	}
	
	public void updateRoom(int id_room, String name, Floor floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		try {
			Room room = new Room(id_room, name, dim_x, dim_y, dim_z, pos_x, pos_y, floor);		
			if (RoomDAO.getInstance().update(room)) {
				List<Surface> surfaces = getRoomSurfaces(id_room);
				Surface newSurface = null;
				for (Surface oldSurface : surfaces) {
					// si c'est un sol
					if (oldSurface.getSurface_type().getId_surface_type() == 2) {
						newSurface = new Surface(oldSurface.getId_surface(), room, dim_x, dim_y, 0, oldSurface.getSurface_type(), oldSurface.getNumber());
					}
					// si c'est un mur					
					else {
						int wallNb = oldSurface.getNumber();
						if (wallNb %2 == 1) {  // mur impair
							newSurface = new Surface(oldSurface.getId_surface(), room, dim_x, 0, dim_z, oldSurface.getSurface_type(),
									oldSurface.getNumber());
						}
						else {
							newSurface = new Surface(oldSurface.getId_surface(), room, dim_y, 0, dim_z, oldSurface.getSurface_type(),
									oldSurface.getNumber());
						}						
					}
					SurfaceDAO.getInstance().update(newSurface);
				}				
				architectRoomCtrl.notifyRoomSaved(NTF_TITLE_SAVE_OK, "La salle a été modifiée");
			}
		} catch (Exception e) {
			System.out.println("Impossible de modifier la salle");
		}		
	}
	
	public void updateArt(int art_id, String art_code, String art_title, String creation_date, String materials, int dim_x,
			int dim_y, int dim_z, byte[] image, Author author, ArtStatus art_status, ArtType art_type, Display display) {
		Art art = new Art(art_id, art_code, art_title, creation_date, materials, dim_x, dim_y, dim_z, image,
				author, art_status, art_type, display);
		if (ArtDAO.getInstance().update(art)) {
			curatorArtDataCtrl.notifyArtSaved(NTF_TITLE_SAVE_OK, "L'œuvre a été modifiée");
		}		
	}
	
	public void updateAuthor(int id_author, String last_name, String first_name, String additional_name, String dates) {
		Author author = new Author(id_author, last_name, first_name, additional_name, dates);
		if (AuthorDAO.getInstance().update(author)) {
			curatorArtDataCtrl.notifyAuthorSaved(NTF_TITLE_SAVE_OK, "L'auteur a été enregistré");
		}		
	}
	
	
	/// Méthodes de suppression dans la base (delete)
	
	public void deleteMuseum() {
		// TODO
		this.currentMuseum = null;
	}
	
	public void deleteFloor() {
		// TODO
	}
	public void deleteRoom(int id_room) {
		Room room = RoomDAO.getInstance().read(id_room);
		if (RoomDAO.getInstance().delete(room)) {
			architectRoomCtrl.notifyRoomSaved(NTF_TITLE_SAVE_OK, "La salle a été supprimée");
		}
	}

	
	/*  ---------------------------
	 * 
	 *    MÉTHODES LIEES À LA VUE
	 * 
	 *  --------------------------- */
	
	@Override
	public void start(Stage primaryStage) {
		this.mainWindow = primaryStage;
		this.mainWindow.setTitle("Gestion de musée");
		// initialisation de la fenêtre principale
		initWindowRoot();
		// affichage de la sous-fenêtre de connexion
		showLoginPane();
	}
	
	/**
	 * affiche la fenêtre principale de l'application
	 */
	public void initWindowRoot() {
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/MainWindow.fxml"));
			// passage du contrôleur principal (this) à la vue
			loader.setController(this);
			mainWindowRoot = (BorderPane)loader.load();
			// affichage de la fenêtre principale
			double height = Screen.getPrimary().getBounds().getHeight();   
			double width = Screen.getPrimary().getBounds().getWidth();   
			Scene scene = new Scene(mainWindowRoot, width, height);
			imgLogo.setImage(new Image("/img/logo_bandw.png"));
			btnQuit.setLayoutX(width-150);
			btnDisconnect.setLayoutX(btnQuit.getLayoutX()-150);
			mainWindow.setScene(scene);
			mainWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre de connexion
	 */
	public void showLoginPane() {
		try {
			if (loginPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/Login.fxml"));
				loginPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.loginCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.loginCtrl.setMainControl(this);
			}
			// définition des menus accessibles
			menu_bar.setVisible(false);
			menu_bar.prefWidthProperty().bind(mainWindow.widthProperty());			
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(loginPane);
			lblRoleStatus.setText("Non connecté");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre Musée du rôle "architecte"
	 */
	public void showArchitectMuseumPane() {
		try {
			if (architectMuseumPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/ArchitectMuseum.fxml"));
				architectMuseumPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.architectMuseumCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.architectMuseumCtrl.setMainControl(this);
			}
			// définition du musée courant
			setCurrentMuseum();
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(true);
			curator_menu.setVisible(false);
			// rafraîchissement des données pour cette sous-fenêtre
			architectMuseumCtrl.refreshData();
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(architectMuseumPane);
			lblRoleStatus.setText("Connecté avec le rôle Architecte");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre Étages du rôle "architecte"
	 */
	public void showArchitectFloorPane() {
		try {
			if (architectFloorPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/ArchitectFloor.fxml"));
				architectFloorPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.architectFloorCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.architectFloorCtrl.setMainControl(this);
			}
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(true);
			curator_menu.setVisible(false);
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(architectFloorPane);
			// rafraîchissement des données
			architectFloorCtrl.refreshData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre Salles du rôle "architecte"
	 */
	public void showArchitectRoomPane() {
		try {
			if (architectRoomPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/ArchitectRoom.fxml"));
				architectRoomPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.architectRoomCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.architectRoomCtrl.setMainControl(this);
			}
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(true);
			curator_menu.setVisible(false);
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(architectRoomPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre ArtData du rôle "conservateur"
	 */
	public void showCuratorArtDataPane() {
		try {
			if (curatorArtDataPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/CuratorArtData.fxml"));
				curatorArtDataPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.curatorArtDataCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.curatorArtDataCtrl.setMainControl(this);
			}
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(false);
			curator_menu.setVisible(true);
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(curatorArtDataPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre ArtMovement du rôle "conservateur"
	 */
	public void showCuratorArtMovementPane() {
		try {
			if (curatorArtMovementPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/CuratorArtMovement.fxml"));
				curatorArtMovementPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.curatorArtMovementCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.curatorArtMovementCtrl.setMainControl(this);
			}
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(false);
			curator_menu.setVisible(true);
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(curatorArtMovementPane);
			lblRoleStatus.setText("Connecté avec le rôle Conservateur");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification d'échec
	 * @param e
	 */
	@FXML
	private void confirm(ActionEvent e) {
		notifWindow.close();
	}
	
	/**
	 * event listener du bouton "Quitter" de la bannière
	 * @param e
	 */
	@FXML
	private void handleQuit(ActionEvent e) {
		Platform.exit();
	}
	
	/**
	 * event listener du bouton "Se déconnecter" de la bannière
	 * @param e
	 */
	@FXML
	private void handleDisconnect(ActionEvent e) {
		this.showLoginPane();
	}
			
	/**
	 * point d'entrée du programme, utilise les arguments spécifiés dans Run Configurations
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
