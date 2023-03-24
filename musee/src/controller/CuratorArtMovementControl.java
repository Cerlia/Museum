package controller;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import museum.floorplan.Room;

public class CuratorArtMovementControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedRoomLine = 0;
	private Stage notifWindow = new Stage();
	
	@FXML
	private Button confirmArtSaved;
	@FXML
	private Label lblNotification;
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> roomColumn;
	@FXML
	private TableColumn<Room, String> floorColumn;
	
	
	
	/*  ---------------------------
	 * 
	 *          MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public CuratorArtMovementControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainController
	 */
	public void setMainControl(Main mainController) {
		this.mainController = mainController;
		refreshData();
	}
	
	public void refreshData() {
		roomTable.setItems(mainController.getRoomData());
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
		roomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		floorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFloor().getFloor_name()));
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
	private void handleRoomTableAction(MouseEvent event) {
		selectedRoomLine = roomTable.getSelectionModel().getSelectedIndex();		
	}		
}
