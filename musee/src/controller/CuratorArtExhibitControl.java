package controller;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import museum.art.Art;
import museum.display.Display;
import museum.floorplan.Room;

public class CuratorArtExhibitControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedRoomLine = -1;
	private Stage notifWindow = new Stage();
	private int selectedSurface = -1;
	
	private static final String SURF0 = "sol";
	private static final String SURF1 = "mur 1";
	private static final String SURF2 = "mur 2";
	private static final String SURF3 = "mur 3";
	private static final String SURF4 = "mur 4";
	private static final String SURFALL = "toutes les surfaces";
	
	@FXML
	private Button btnWall1;
	@FXML
	private Button btnWall2;
	@FXML
	private Button btnWall3;
	@FXML
	private Button btnWall4;
	@FXML
	private Button btnFloor;
	@FXML
	private Button btnAllSurfaces;
	@FXML
	private Button confirmArtSaved;
	@FXML
	private Label lblNotification;
	@FXML
	private Label lblDisplayedArtPaneTitle;
	@FXML
	private ChoiceBox<Room> chbRoom;
	@FXML
	private Pane pneRoomSurfaces;
	@FXML
	private TableView<Art> tblArtDisplay;
	@FXML
	private TableColumn<Art, String> tclTitle;
	@FXML
	private TableColumn<Art, String> tclCode;
	@FXML
	private TableColumn<Art, String> tclAuthor;
	@FXML
	private TableColumn<Art, String> tclDisplay;
	@FXML
	private TableColumn<Art, String> tclDisplayType;
	@FXML
	private TableColumn<Art, String> tclSurfaceArt;
	@FXML
	private TableView<Display> tblDisplay;
	@FXML
	private TableColumn<Display, String> tclName;
	@FXML
	private TableColumn<Display, String> tclModel;
	@FXML
	private TableColumn<Display, String> tclType;
	@FXML
	private TableColumn<Display, String> tclDimX;
	@FXML
	private TableColumn<Display, String> tclDimY;
	@FXML
	private TableColumn<Display, String> tclDimZ;
	@FXML
	private TableColumn<Display, String> tclNbArt;
	@FXML
	private TableColumn<Display, String> tclSurfaceDisp;
		
	
	/*  ---------------------------
	 * 
	 *          MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public CuratorArtExhibitControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainController
	 */
	public void setMainControl(Main mainController) {
		this.mainController = mainController;
	}
	
	public void refreshData() {
		ObservableList<Room> rooms = mainController.getRoomData();
		chbRoom.setItems(rooms);
		if (rooms.size()>0) {
			this.selectedRoomLine = 0;
			this.updateExhibitTitle();
			chbRoom.setValue(chbRoom.getItems().get(selectedRoomLine));
		}
	}
	
	public void showSurfaces(Room room) {
		if (selectedRoomLine != -1) {
			pneRoomSurfaces.setVisible(true);
		}
	}
	
	public void showDisplayedArts(Room room) {
		if (this.selectedRoomLine > -1) {
			if (this.selectedSurface == -1) {
				tblArtDisplay.setItems(mainController.getAllArtsOfRoom(room));
			} else {
				tblArtDisplay.setItems(mainController.getAllArtsOfSurface(room, selectedSurface));
			}
		}				
	}
	
	public void showDisplays(Room room) {
		if (this.selectedRoomLine > -1) {
			if (this.selectedSurface == -1) {
				tblDisplay.setItems(mainController.getAllDisplaysOfRoom(room));
			} else {
				tblDisplay.setItems(mainController.getAllDisplaysOfSurface(room, selectedSurface));
			}	
		}			
	}
	
	public void updateExhibitTitle() {
		Room room = chbRoom.getItems().get(selectedRoomLine);
		String titreSurface = "EXPOSITION - " + room.getName().toUpperCase() + " - ";
		switch (selectedSurface) {
		case -1:
			titreSurface += SURFALL;
			break;
		case 0:
			titreSurface += SURF0;
			break;
		case 1:
			titreSurface += SURF1;
			break;
		case 2:
			titreSurface += SURF2;
			break;
		case 3:
			titreSurface += SURF3;
			break;
		case 4:
			titreSurface += SURF4;
			break;
		}
		lblDisplayedArtPaneTitle.setText(titreSurface);				
	}
	
	/*  ---------------------------
	 * 
	 *    MÉTHODES LIEES À LA VUE
	 * 
	 *  --------------------------- */
	
	/**
	 * TODO à l'ouverture de la fenêtre, initialise je sais pas quoi, à revoir
	 */
	@FXML
	private void initialize() {
		// colonnes de la table des oeuvres
		tclTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_title()));
		tclCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_code()));
		tclAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor().getLast_name()));
		tclDisplay.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay().getName()));
		tclDisplayType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay().
				getDisplay_model().getDisplay_type().getName()));
		tclSurfaceArt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay().getSurface().getName()));
		// colonnes de la table des présentoirs
		tclName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		tclModel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay_model().getName()));
		tclType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay_model().getDisplay_type().getName()));
		tclDimX.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_x()+""));
		tclDimY.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_y()+""));
		tclDimZ.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_z()+""));
		tclNbArt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArts().size()+""));
		tclSurfaceDisp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurface().getName()));
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification
	 * @param e
	 */
	@FXML
	private void confirm(ActionEvent e) {
		notifWindow.close();
	}
	
	/**
	 * event listener de la liste de salles, permet de récupérer la ligne sélectionnée
	 */
	@FXML
	private void handleRoomListAction(ActionEvent event) {
		selectedRoomLine = chbRoom.getSelectionModel().getSelectedIndex();
		if (selectedRoomLine != -1) {
			Room room = chbRoom.getItems().get(selectedRoomLine);
			showSurfaces(room);
			showDisplayedArts(room);
			showDisplays(room);
			updateExhibitTitle();
		}
	}
	
	/**
	 * event listener des 6 boutons de surface visibles dans les infos d'une salle
	 */
	@FXML
	private void handleBtnSurfaceAction(ActionEvent event) {
		selectedRoomLine = chbRoom.getSelectionModel().getSelectedIndex();
		if (selectedRoomLine != -1) {
			int surfaceNb = 0;
			if (event.getSource().equals(btnFloor)) {
				surfaceNb = 0;
			} else if (event.getSource().equals(btnWall1)) {
				surfaceNb = 1;
			} else if (event.getSource().equals(btnWall2)) {
				surfaceNb = 2;
			} else if (event.getSource().equals(btnWall3)) {
				surfaceNb = 3;
			} else if (event.getSource().equals(btnWall4)) {
				surfaceNb = 4;
			} else if (event.getSource().equals(btnAllSurfaces)) {
				surfaceNb = -1;
			}
			this.selectedSurface = surfaceNb;
			Room room = chbRoom.getItems().get(selectedRoomLine);
			showDisplayedArts(room);
			showDisplays(room);
			updateExhibitTitle();
		}		
	}
}
