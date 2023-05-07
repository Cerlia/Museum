package controller;

import java.io.IOException;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
	private int selectedSurface = -1;
	private int selectedArtLine = -1;
	private Stage stgArtSelect = new Stage();
	private CuratorArtSelectControl artSelectCtrl = null;
	
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
	private Button btnAddArt;
	@FXML
	private Button btnRemoveArt;
	@FXML
	private Button confirmArtSaved;
	@FXML
	private Label lblDisplayedArtPaneTitle;
	@FXML
	private Label lblPaneTitle;
	@FXML
	private ChoiceBox<Room> chbRoom;
	@FXML
	private Pane pneRoomSurfaces;
	@FXML
	private Pane pneArtSelect;
	@FXML
	private Pane pneExhibit;
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
		pneExhibit.setVisible(false);
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
	 * initialisation de la vue JavaFX
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
	 * event listener de la liste de salles, permet de récupérer la ligne sélectionnée
	 */
	@FXML
	private void handleRoomListAction(ActionEvent event) {
		selectedRoomLine = chbRoom.getSelectionModel().getSelectedIndex();
		if (selectedRoomLine != -1) {
			Room room = chbRoom.getItems().get(selectedRoomLine);
			pneExhibit.setVisible(true);
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
			pneExhibit.setVisible(true);
			showDisplayedArts(room);
			showDisplays(room);
			updateExhibitTitle();
		}		
	}
	
	/**
	 * event listener du bouton "Retirer l'œuvre"
	 * @param e
	 */
	@FXML
	private void handleBtnRemoveArtAction(ActionEvent event) {
		if (selectedArtLine != -1) {
			int art_id = tblArtDisplay.getItems().get(selectedArtLine).getId_art();
			Art art = mainController.getFullArtData(art_id);
			Display display = art.getDisplay();
			// si le présentoir actuel ne contient qu'une œuvre, il faut le supprimer d'abord
			if (display.getArts().size() == 1) {
				mainController.deleteDisplay(display);
			}
			art.setDisplay(null);
			mainController.updateArt(art, "artRemoval");
		}
		else {
			mainController.notifyInfo("Une œuvre doit être sélectionné");
		}
	}
	
	// TODO à déplacer, normalement c'est main qui appelle les fenêtres
	/**
	 * event listener du bouton "Ajouter une œuvre" : ouvre une nouvelle fenêtre
	 * @param e
	 */
	@FXML
	private void handleBtnAddArtAction(ActionEvent event) {
		if (stgArtSelect.getModality() != Modality.APPLICATION_MODAL) {
			stgArtSelect.initModality(Modality.APPLICATION_MODAL);
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/CuratorArtSelect.fxml"));
			pneArtSelect = (Pane)loader.load();
			// récupération du contrôleur de la vue
			this.artSelectCtrl = loader.getController();
			// passage du contrôleur principal au sous-contrôleur
			this.artSelectCtrl.setMainControl(this.mainController);
			// rafraîchissement des données de la sous-fenêtre
			this.artSelectCtrl.refreshData();
			// affichage de la fenêtre
			Scene scene = new Scene(pneArtSelect);
			scene.getStylesheets().add("style.css");
			stgArtSelect.setTitle("Exposer une œuvre");
			stgArtSelect.setScene(scene);
			stgArtSelect.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * event listener de la table d'oeuvres actuellement en exposition
	 * @param event
	 */
	@FXML
	private void handleTableArtAction(MouseEvent event) {
		selectedArtLine = tblArtDisplay.getSelectionModel().getSelectedIndex();
	}
	
	public void closeArtSelectStage() {
		stgArtSelect.close();
	}
}
