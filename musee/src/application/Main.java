package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import museum.Art;
import museum.ArtStatus;
import museum.ArtType;
import museum.Author;
import museum.Display;
import museum.DisplayArtType;
import museum.DisplayType;
import museum.Door;
import museum.Role;
import museum.Room;
import museum.User;
import museum.Zone;
import controller.ArchitectControl;
import controller.LoginControl;
import dao.ArtDAO;
import dao.ArtStatusDAO;
import dao.ArtTypeDAO;
import dao.AuthorDAO;
import dao.DisplayArtTypeDAO;
import dao.DisplayDAO;
import dao.DisplayTypeDAO;
import dao.DoorDAO;
import dao.RoleDAO;
import dao.RoomDAO;
import dao.UserDAO;
import dao.ZoneDAO;

public class Main extends Application {
	
	private Stage mainWindow;			// "stage" principal
	private BorderPane mainWindowRoot;	// fenêtre principale
	
	// sous-fenêtres
	private AnchorPane loginPane = null;
	private AnchorPane architectPane = null;
	
	// sous-contrôleurs des différentes sous-fenêtres
	private LoginControl loginCtrl = null;
	private ArchitectControl architectCtrl = null;
	
	// observableLists pour manipuler les données
	private ObservableList<Art> artData = FXCollections.observableArrayList();
	private ObservableList<ArtStatus> artStatusData = FXCollections.observableArrayList();
	private ObservableList<ArtType> artTypeData = FXCollections.observableArrayList();
	private ObservableList<Author> authorData = FXCollections.observableArrayList();
	private ObservableList<DisplayArtType> displayArtTypeData = FXCollections.observableArrayList();
	private ObservableList<Display> displayData = FXCollections.observableArrayList();
	private ObservableList<DisplayType> displayTypeData = FXCollections.observableArrayList();
	private ObservableList<Door> doorData = FXCollections.observableArrayList();	
	private ObservableList<Role> roleData = FXCollections.observableArrayList();
	private ObservableList<Room> roomData = FXCollections.observableArrayList();
	private ObservableList<User> userData = FXCollections.observableArrayList();	
	private ObservableList<Zone> zoneData = FXCollections.observableArrayList();
	
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
	
	public ObservableList<Art> getArtData() {
		artData = FXCollections.observableArrayList();
		List<Art> art_objects = ArtDAO.getInstance().readAll();
		for (Art art : art_objects) {
			artData.add(art);
		}
		return artData;
	}
	
	public ObservableList<ArtStatus> getArtStatusData() {
		artStatusData = FXCollections.observableArrayList();
		List<ArtStatus> art_statuses = ArtStatusDAO.getInstance().readAll();
		for (ArtStatus art_status : art_statuses) {
			artStatusData.add(art_status);
		}
		return artStatusData;
	}
	
	public ObservableList<ArtType> getArtTypeData() {
		artTypeData = FXCollections.observableArrayList();
		List<ArtType> art_types = ArtTypeDAO.getInstance().readAll();
		for (ArtType art_type : art_types) {
			artTypeData.add(art_type);
		}
		return artTypeData;
	}
	
	public ObservableList<Author> getAuthorData() {
		authorData = FXCollections.observableArrayList();
		List<Author> authors = AuthorDAO.getInstance().readAll();
		for (Author author : authors) {
			authorData.add(author);
		}
		return authorData;
	}
	
	public ObservableList<DisplayArtType> getDisplayArtTypeData() {
		displayArtTypeData = FXCollections.observableArrayList();
		List<DisplayArtType> display_art_types = DisplayArtTypeDAO.getInstance().readAll();
		for (DisplayArtType display_art_type : display_art_types) {
			displayArtTypeData.add(display_art_type);
		}
		return displayArtTypeData;
	}
	
	public ObservableList<Display> getDisplayData() {
		displayData = FXCollections.observableArrayList();
		List<Display> displays = DisplayDAO.getInstance().readAll();
		for (Display display : displays) {
			displayData.add(display);
		}
		return displayData;
	}
	
	public ObservableList<DisplayType> getDisplayTypeData() {
		displayTypeData = FXCollections.observableArrayList();
		List<DisplayType> display_types = DisplayTypeDAO.getInstance().readAll();
		for (DisplayType display_type : display_types) {
			displayTypeData.add(display_type);
		}
		return displayTypeData;
	}
	
	public ObservableList<Door> getDoorData() {
		doorData = FXCollections.observableArrayList();
		List<Door> doors = DoorDAO.getInstance().readAll();
		for (Door door : doors) {
			doorData.add(door);
		}
		return doorData;
	}
	
	public ObservableList<Role> getRoleData() {
		roleData = FXCollections.observableArrayList();
		List<Role> roles = RoleDAO.getInstance().readAll();
		for (Role role : roles) {
			roleData.add(role);
		}
		return roleData;
	}
	
	public ObservableList<Room> getRoomData() {
		roomData = FXCollections.observableArrayList();
		List<Room> rooms = RoomDAO.getInstance().readAll();
		for (Room room : rooms) {
			roomData.add(room);
		}
		return roomData;
	}
	
	public ObservableList<User> getUserData() {
		userData = FXCollections.observableArrayList();
		List<User> users = UserDAO.getInstance().readAll();
		for (User user : users) {
			userData.add(user);
		}
		return userData;
	}
	
	public ObservableList<Zone> getzoneData() {
		zoneData = FXCollections.observableArrayList();
		List<Zone> zones = ZoneDAO.getInstance().readAll();
		for (Zone zone : zones) {
			zoneData.add(zone);
		}
		return zoneData;
	}
	
	public void addRoom(String name, int floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		Room room = new Room(name, floor, dim_x, dim_y, dim_z, pos_x, pos_y);
		if (RoomDAO.getInstance().create(room)) {
			architectCtrl.notifyRoomSaved("La salle a bien été enregistrée");
		}		
	}
	
	public void updateRoom(int id_room, String name, int floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		Room room = new Room(id_room, name, floor, dim_x, dim_y, dim_z, pos_x, pos_y);
		if (RoomDAO.getInstance().update(room)) {
			architectCtrl.notifyRoomSaved("La salle a été modifiée");
		}		
	}
	
	public void deleteRoom(int id_room) {
		Room room = RoomDAO.getInstance().read(id_room);
		if (RoomDAO.getInstance().delete(room)) {
			architectCtrl.notifyRoomSaved("La salle a été supprimée");
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
			Scene scene = new Scene(mainWindowRoot);
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
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(loginPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre du rôle "architecte"
	 */
	public void showArchitectPane() {
		try {
			if (architectPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/Architect.fxml"));
				architectPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.architectCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.architectCtrl.setMainControl(this);
			}
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(architectPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			
	/**
	 * point d'entrée du programme, utilise les arguments spécifiés dans Run Configurations
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
