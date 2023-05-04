package controller;

import java.util.ArrayList;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import museum.art.Art;
import museum.floorplan.Floor;
import museum.floorplan.Room;

public class VisitorControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedRoomLine = -1;
	private int floorMax;
	private Floor currentFloor = null;
	private int currentFloorNb;
	private ObservableList<Floor> floors = null;
	
	@FXML
	private Canvas cnvFloorplan;
	@FXML
	private Label lblMuseum;
	@FXML
	private Label lblFloorName;
	@FXML
	private ListView<Room> lstRooms;
	@FXML
	private TableView<Art> tblArts;
	@FXML
	private TableColumn<Art, String> colTitle;
	@FXML
	private TableColumn<Art, String> colAuthor;
	
	
	/*  ---------------------------
	 * 
	 *           MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public VisitorControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainControler
	 */
	public void setMainControl(Main mainController) {
		this.mainController = mainController;	
	}
	
	/**
	 * récupère les dernières infos de la BD
	 */
	public void refreshData() {
		if (mainController.getFloorData() != null) {
			this.floors = mainController.getFloorData();
			this.currentFloor = floors.get(0);
			int currentFloorNb = 0;
			floorMax = floors.size()-1;
			showRoomList(currentFloor);
		}
		else {
			// TODO 
			// Afficher "pas d'étage dans le musée" ?
		}			
	}
	
	public void showRoomList(Floor floor) {
		lstRooms.setItems(mainController.getRoomData(floor));
	}
	
	public void showArtList() {
		Room room = lstRooms.getSelectionModel().getSelectedItem();
		tblArts.setItems(mainController.getAllArtsOfRoom(null));
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
		colTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_title()));
		colAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor().getFullName()));
	}
	

	/**
	 * event listener de la liste d'étages, permet de récupérer la ligne sélectionnée (clic)
	 */
	@FXML
	private void handleFloorTableAction(MouseEvent event) {
		selectedRoomLine = lstRooms.getSelectionModel().getSelectedIndex();
		// showArtList();
	}
	
	/**
	 * event listener de la liste d'étages, permet de récupérer la ligne sélectionnée (bouton)
	 */
	@FXML
	private void handleFloorTableKeyPressed(KeyEvent event) {
		selectedRoomLine = lstRooms.getSelectionModel().getSelectedIndex();
		// showArtList();	
	}
	
	/**
	 * event listener de l'image "arrow up"
	 */
	@FXML
	private void handleImgFloorUpClick(MouseEvent event) {
		if (currentFloorNb < floorMax) {
			currentFloorNb+=1;
			showRoomList(floors.get(currentFloorNb));
		}
	}
	
	/**
	 * event listener de l'image "arrow down"
	 */
	@FXML
	private void handleImgFloorDownClick(MouseEvent event) {
		if (currentFloorNb > 0) {
			currentFloorNb-=1;
			showRoomList(floors.get(currentFloorNb));
		}
	}
}
