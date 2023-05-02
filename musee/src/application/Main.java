package application;
	
import java.io.IOException;
import java.util.List;

import controller.ArchitectFloorControl;
import controller.ArchitectMuseumControl;
import controller.ArchitectRoomControl;
import controller.CuratorArtDataControl;
import controller.CuratorArtExhibitControl;
import controller.CuratorArtMovementControl;
import controller.CuratorAuthorSelectControl;
import controller.LoginControl;
import dao.RoleDAO;
import dao.UserDAO;
import dao.art.ArtDAO;
import dao.art.ArtStatusDAO;
import dao.art.ArtTypeDAO;
import dao.art.AuthorDAO;
import dao.display.DisplayArtTypeDAO;
import dao.display.DisplayDAO;
import dao.display.DisplayModelDAO;
import dao.display.DisplayTypeDAO;
import dao.floorplan.FloorDAO;
import dao.floorplan.MuseumDAO;
import dao.floorplan.RoomDAO;
import dao.floorplan.SurfaceDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import museum.Role;
import museum.User;
import museum.art.Art;
import museum.art.ArtStatus;
import museum.art.ArtType;
import museum.art.Author;
import museum.display.Display;
import museum.display.DisplayArtType;
import museum.display.DisplayModel;
import museum.display.DisplayType;
import museum.floorplan.Floor;
import museum.floorplan.Museum;
import museum.floorplan.Room;
import museum.floorplan.Surface;

public class Main extends Application {
	
	private Stage mainWindow;			// "stage" principal
	private BorderPane mainWindowRoot;	// fenêtre principale
	private Museum currentMuseum;
	private Stage stgAuthorSelect = new Stage();     // "stage" de gestion des auteurs
	
	private final String NTF_TITLE_FORM_NG = "Enregistrement impossible";
	private final String NTF_BODY_FORM_NG = "Les champs ne sont pas correctement remplis.\n" +
			"Vérifiez les données saisies, puis réessayez.";
	private final String NTF_TITLE_SAVE_OK = "Confirmation d'enregistrement";
	private final String NTF_BODY_SAVE_OK = "Les données ont bien été enregistrées";
	
	// sous-fenêtres
	private AnchorPane loginPane = null;
	private AnchorPane architectMuseumPane = null;
	private AnchorPane architectFloorPane = null;
	private AnchorPane architectRoomPane = null;
	private AnchorPane curatorArtDataPane = null;
	private AnchorPane curatorArtMovementPane = null;
	private Pane curatorAuthorSelectPane = null;
	private FlowPane curatorArtExhibitPane = null;
	
	// sous-contrôleurs des différentes sous-fenêtres
	private LoginControl loginCtrl = null;
	private ArchitectMuseumControl architectMuseumCtrl = null;
	private ArchitectFloorControl architectFloorCtrl = null;
	private ArchitectRoomControl architectRoomCtrl = null;
	private CuratorArtDataControl curatorArtDataCtrl = null;
	private CuratorArtMovementControl curatorArtMovementCtrl = null;
	private CuratorArtExhibitControl curatorArtExhibitCtrl = null;
	private CuratorAuthorSelectControl authorSelectCtrl = null;
	
	// observableLists pour manipuler les données
	private ObservableList<Art> artData = FXCollections.observableArrayList();
	private ObservableList<ArtStatus> artStatusData = FXCollections.observableArrayList();
	private ObservableList<ArtType> artTypeData = FXCollections.observableArrayList();
	private ObservableList<Author> authorData = FXCollections.observableArrayList();
	private ObservableList<DisplayArtType> displayArtTypeData = FXCollections.observableArrayList();
	private ObservableList<Display> displayData = FXCollections.observableArrayList();
	private ObservableList<DisplayType> displayTypeData = FXCollections.observableArrayList();
	private ObservableList<Floor> floorData = FXCollections.observableArrayList();
	private ObservableList<Museum> museumData = FXCollections.observableArrayList();
	private ObservableList<Role> roleData = FXCollections.observableArrayList();
	private ObservableList<Room> roomData = FXCollections.observableArrayList();
	private ObservableList<User> userData = FXCollections.observableArrayList();	
	private ObservableList<Surface> surfaceData = FXCollections.observableArrayList();
	private ObservableList<Art> surfaceArtsData = FXCollections.observableArrayList();
	private ObservableList<Art> roomArtsData = FXCollections.observableArrayList();
	private ObservableList<Art> floorArtsData = FXCollections.observableArrayList();
	private ObservableList<Display> surfaceDisplaysData = FXCollections.observableArrayList();
	private ObservableList<Display> roomDisplaysData = FXCollections.observableArrayList();
	private ObservableList<Surface> roomSurfacesData = FXCollections.observableArrayList();
	private ObservableList<Display> compatibleExistingDisplays = FXCollections.observableArrayList();
	private ObservableList<DisplayModel> compatibleDisplayModels = FXCollections.observableArrayList();
	
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
	@FXML
	private Line shpDvdLine;
	@FXML
	private Pane pneMainActions;


	/**
	 * constructeur du contrôleur principal 
	 */
	public Main() {
		super();
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
	public ObservableList<User> getUserData(String login, byte[] hash) {
		userData = FXCollections.observableArrayList();
		List<User> users = UserDAO.getInstance().readAll(login, hash);
		for (User user : users) {
			userData.add(user);
		}
		return userData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @return
	 */
	public ObservableList<Surface> getsurfaceData() {
		surfaceData = FXCollections.observableArrayList();
		List<Surface> surfaces = SurfaceDAO.getInstance().readAll();
		for (Surface surface : surfaces) {
			surfaceData.add(surface);
		}
		return surfaceData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @param surface
	 * @return
	 */
	public ObservableList<Art> getAllArtsOfSurface(Room room, int surfaceNb) {
		surfaceArtsData = FXCollections.observableArrayList();
		List<Surface> roomSurfaces = SurfaceDAO.getInstance().readAllSurfacesOfRoom(room.getId_room());
		Surface surface = roomSurfaces.get(surfaceNb);
		List<Art> arts = ArtDAO.getInstance().readAllArtsOfSurface(surface.getId_surface());
		for (Art art : arts) {
			surfaceArtsData.add(art);
		}
		return surfaceArtsData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @param room
	 * @return
	 */
	public ObservableList<Art> getAllArtsOfRoom(Room room) {
		roomArtsData = FXCollections.observableArrayList();
		List<Art> arts = ArtDAO.getInstance().readAllArtsOfRoom(room.getId_room());
		for (Art art : arts) {
			roomArtsData.add(art);
		}
		return roomArtsData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @param room
	 * @return
	 */
	public ObservableList<Art> getAllArtsOfFloor(Floor floor) {
		floorArtsData = FXCollections.observableArrayList();
		List<Art> arts = ArtDAO.getInstance().readAllArtsOfFloor(floor.getId_floor());
		for (Art art : arts) {
			floorArtsData.add(art);
		}
		return floorArtsData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @param room
	 * @return
	 */
	public ObservableList<Display> getAllDisplaysOfSurface(Room room, int surfaceNb) {
		surfaceDisplaysData = FXCollections.observableArrayList();
		List<Surface> roomSurfaces = SurfaceDAO.getInstance().readAllSurfacesOfRoom(room.getId_room());
		Surface surface = roomSurfaces.get(surfaceNb);
		List<Display> displays = DisplayDAO.getInstance().readAllDisplaysOfSurface(surface.getId_surface());
		for (Display display : displays) {
			surfaceDisplaysData.add(display);
		}
		return surfaceDisplaysData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @param room
	 * @return
	 */
	public ObservableList<Display> getAllDisplaysOfRoom(Room room) {
		roomDisplaysData = FXCollections.observableArrayList();
		List<Display> displays = DisplayDAO.getInstance().readAllDisplaysOfRoom(room.getId_room());
		for (Display display : displays) {
			roomDisplaysData.add(display);
		}
		return roomDisplaysData;
	}
	
	/**
	 * construit une liste d'observables exploitable par une vue JavaFX
	 * @param room
	 * @return
	 */
	public ObservableList<Surface> getAllSurfacesOfRoom(Room room) {
		roomSurfacesData = FXCollections.observableArrayList();
		List<Surface> surfaces = room.getSurfaces();
		for (Surface surface : surfaces) {
			roomSurfacesData.add(surface);
		}
		return roomSurfacesData;
	}
	
	
	public ObservableList<Display> getAllCompatibleExistingDisplays(Surface surface, Art art) {
		compatibleExistingDisplays = FXCollections.observableArrayList();
		List<Display> displays = DisplayDAO.getInstance().
				readAllExistingCompatibleDisplaysOfSurface(surface.getId_surface(), art.getArt_type().getId_Art_type());
		for (Display display : displays) {
			compatibleExistingDisplays.add(display);
		}
		return compatibleExistingDisplays;
	}
	
	public ObservableList<DisplayModel> getAllCompatibleDisplayModels(Surface surface, Art art) {
		compatibleDisplayModels = FXCollections.observableArrayList();
		List<DisplayModel> displayModels = DisplayModelDAO.getInstance().
				readAllCompatibleDisplayModels(surface.getSurface_type().getId_surface_type(), art.getArt_type().getId_Art_type());
		for (DisplayModel displayModel : displayModels) {
			compatibleDisplayModels.add(displayModel);
		}
		return compatibleDisplayModels;
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
	 * retourne les informations détaillées d'une œuvre (inclut image)
	 * @param id_art
	 * @return
	 */
	public Art getFullArtData(int id_art) {
		Art fullArt = ArtDAO.getInstance().read(id_art);
		return fullArt;
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
			this.notifySuccess("Le musée a été créé.");
			architectMuseumCtrl.resetMuseumCreateEdit();
		} else {
			this.notifyFail("Impossible de créer le musée");
		}
	}
	
	/**
	 * demande à la DAO d'ajouter un étage dans la base
	 * @param name
	 * @param dim_x
	 * @param dim_y
	 */
	public void addFloor(String name, int dim_x, int dim_y) {
		Floor floor = new Floor(name, dim_x, dim_y, null);
		if (FloorDAO.getInstance().create(floor)) {
			this.notifySuccess("L'étage a été créé.");
			architectFloorCtrl.resetFloorCreateEdit();
		} else {
			this.notifyFail("Impossible de créer l'étage");
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
		Room room = new Room(name, dim_x, dim_y, dim_z, pos_x, pos_y, floor, null);	
		if (RoomDAO.getInstance().create(room)) {
			this.notifySuccess("La salle a été créée.");
			architectRoomCtrl.resetRoomCreateEdit();
		} else {
			this.notifyFail("Impossible de créer la salle");
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
		// par défaut, une œuvre est "Possédée" (id_art_status = 1)
		// et elle n'a pas de présentoir (display = null)
		// TODO l'oeuvre pourrait avoir le statut "Prêté" ou "Emprunté"
		ArtStatus artStatus = ArtStatusDAO.getInstance().read(1);
		Art art = new Art(art_code, art_title, creation_date, materials, dim_x, dim_y, dim_z, image,
				author, artStatus, art_type, null);
		if (ArtDAO.getInstance().create(art)) {
			this.notifySuccess("L'œuvre a été créée.");
			curatorArtDataCtrl.resetArtCreateEdit();
		} else {
			this.notifyFail("Impossible de créer l'œuvre");
		}		
	}
	
	/**
	 * crée un nouveau présentoir dans la BD
	 * @param art
	 * @param dispMod
	 * @param surface
	 * @param name
	 * @return
	 */
	public Display addDisplay(Art art, DisplayModel dispMod, Surface surface, String name) {
		Display display = new Display(name, dispMod.getDim_x(), dispMod.getDim_y(), dispMod.getDim_z(),
			surface, dispMod, null);
		if (!DisplayDAO.getInstance().create(display)) {
			display = null;
		}
		return display;
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
			this.notifySuccess("L'auteur a été créé.");
			authorSelectCtrl.resetAuthorCreateEdit();
		} else {
			this.notifyFail("Impossible de créer l'auteur");
		}		
	}
		
	
	/// Méthodes de modification de la base (update)
	
	public void updateMuseum(Museum museum) {
		if (MuseumDAO.getInstance().update(museum)) {
			this.notifySuccess("Le musée a été modifié.");
			architectMuseumCtrl.resetMuseumCreateEdit();
		} else {
			this.notifyFail("Impossible de créer le musée");
		}
	}
	
	public void updateFloor(Floor floor) {
		if (FloorDAO.getInstance().update(floor)) {
			this.notifySuccess("L'étage a été modifié.");
			architectFloorCtrl.resetFloorCreateEdit();
		} else {
			this.notifyFail("Impossible de modifier l'étage");
		}
	}
	
	public void updateRoom(Room room) {
		if (RoomDAO.getInstance().update(room)) {
			this.notifySuccess("La salle a été modifiée.");
			architectRoomCtrl.resetRoomCreateEdit();
		} else {
			this.notifyFail("Impossible de modifier la salle");
		}	
	}
	
	public void updateArt(Art art, String requester) {
		if (ArtDAO.getInstance().update(art)) {			
			switch (requester) {
			case "artData":
				this.notifySuccess("L'œuvre a été modifiée.");
				curatorArtDataCtrl.resetArtCreateEdit();
				break;
			case "artExhibit":
				this.notifySuccess("L'œuvre a été associée au présentoir.");
				curatorArtExhibitCtrl.refreshData();
				curatorArtExhibitCtrl.closeArtSelectStage();
				break;
			}
		} else {
			this.notifyFail("Impossible de modifier l'œuvre");
		}
	}
	
	public void updateAuthor(Author author) {
		if (AuthorDAO.getInstance().update(author)) {
			this.notifySuccess("L'auteur a été modifié.");
			authorSelectCtrl.resetAuthorCreateEdit();
		} else {
			this.notifyFail("Impossible de modifier l'auteur");
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
			this.notifySuccess("La salle a été supprimée.");
			architectRoomCtrl.resetRoomCreateEdit();
		} else {
		this.notifyFail("Impossible de supprimer la salle");
		}
	}
	
	public boolean deleteDisplay(Display display) {
		return DisplayDAO.getInstance().delete(display);
	}

	
	/*  ---------------------------
	 * 
	 *    MÉTHODES LIEES À LA VUE
	 * 
	 *  --------------------------- */
	
	@Override
	public void start(Stage primaryStage) {
		this.mainWindow = primaryStage;
		this.mainWindow.setResizable(false);
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
			pneMainActions.setLayoutX(width-pneMainActions.getPrefWidth()-20);
			shpDvdLine.setEndX(width);
			mainWindow.setScene(scene);
			// TODO tester de supprimer la taille des côtés Gauche et Droite
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
			// réinitilisation des champs
			this.loginCtrl.resetControls();
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
			// rafraîchissement des données de la sous-fenêtre
			this.architectMuseumCtrl.refreshData();
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(true);
			curator_menu.setVisible(false);
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
			// rafraîchissement des données de la sous-fenêtre
			this.architectFloorCtrl.refreshData();
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(true);
			curator_menu.setVisible(false);
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(architectFloorPane);
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
			// rafraîchissement des données de la sous-fenêtre
			this.architectRoomCtrl.refreshData();
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
			// rafraîchissement des données de la sous-fenêtre
			this.curatorArtDataCtrl.refreshData();
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
	 * affichage la modale de sélection de l'auteur
	 */
	public void showCuratorAuthorSelectionStage() {
		try {
			if (stgAuthorSelect.getModality() != Modality.APPLICATION_MODAL) {
				stgAuthorSelect.initModality(Modality.APPLICATION_MODAL);
			}			
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/CuratorAuthorSelect.fxml"));
			curatorAuthorSelectPane = (Pane)loader.load();
			// récupération du contrôleur de la vue
			this.authorSelectCtrl = loader.getController();
			// passage du contrôleur principal au sous-contrôleur
			this.authorSelectCtrl.setMainControl(this);
			// rafraîchissement des données de la sous-fenêtre
			this.authorSelectCtrl.refreshData();
			// affichage de la fenêtre
			Scene scene = new Scene(curatorAuthorSelectPane);
			stgAuthorSelect.setScene(scene);
			stgAuthorSelect.show();
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
			// rafraîchissement des données de la sous-fenêtre
			this.curatorArtMovementCtrl.refreshData();
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
	 * affiche la sous-fenêtre ArtExhibit du rôle "conservateur"
	 */
	public void showCuratorArtExhibitPane() {
		try {
			if (curatorArtExhibitPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/CuratorArtExhibit.fxml"));
				curatorArtExhibitPane = loader.load();
				// récupération du contrôleur de la vue
				this.curatorArtExhibitCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.curatorArtExhibitCtrl.setMainControl(this);				
			}			
			// rafraîchissement des données de la sous-fenêtre
			this.curatorArtExhibitCtrl.refreshData();
			// définition des menus accessibles
			menu_bar.setVisible(true);
			architect_menu.setVisible(false);
			curator_menu.setVisible(true);
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(curatorArtExhibitPane);
			lblRoleStatus.setText("Connecté avec le rôle Conservateur");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche une notification d'erreur avec un message personnalisé
	 */
	public void notifyFail(String body) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(NTF_TITLE_FORM_NG);
		String message;
		if (body != null) {
			message = body;
		} else {
			message = NTF_BODY_FORM_NG;
		}
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	/**
	 * affiche une confirmation d'opération réussie avec un message personnalisé
	 */
	public void notifySuccess(String body) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(NTF_TITLE_SAVE_OK);
		String message;
		if (body != null) {
			message = body;
		} else {
			message = NTF_BODY_SAVE_OK;
		}
		alert.setHeaderText(message);
		alert.showAndWait();
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
