package controller;

import java.io.IOException;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import museum.Room;

public class ArchitectControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainControler;
	// ligne sélectionnée dans la table des salles, par défaut la première
	private int selectedRoomLine = 0;
	private Stage notifWindow = new Stage();
	private Pane dialogRoomCreated;
	private boolean updatingRoom = false;
	private boolean addingRoom = false;
	
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> idColumn;
	@FXML
	private TableColumn<Room, String> nameColumn;
	@FXML
	private TableColumn<Room, String> floorColumn;
	@FXML
	private AnchorPane editRoom;
	@FXML
	private Button editAction;
	@FXML
	private Button createAction;
	@FXML
	private Button deleteAction;
	@FXML
	private Label roomFormTitle;
	@FXML
	private TextField inputRoomName;
	@FXML
	private TextField inputRoomFloor;
	@FXML
	private TextField inputRoomDimX;
	@FXML
	private TextField inputRoomDimY;
	@FXML
	private TextField inputRoomDimZ;
	@FXML
	private TextField inputRoomPosX;
	@FXML
	private TextField inputRoomPosY;
	@FXML
	private Label lblNotification;
	
	
	/*  ---------------------------
	 * 
	 *           MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public ArchitectControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainControler
	 */
	public void setMainControl(Main mainControler) {
		this.mainControler = mainControler;
		roomTable.setItems(mainControler.getRoomData());
	}
	
	/**
	 * demande au contrôleur principal d'ajouter une salle
	 */
	public void addRoom() {
		String roomName = inputRoomName.getText();
		int roomFloor = Integer.parseInt(inputRoomFloor.getText());
		int roomDimX = Integer.parseInt(inputRoomDimX.getText());
		int roomDimY = Integer.parseInt(inputRoomDimY.getText());
		int roomDimZ = Integer.parseInt(inputRoomDimZ.getText());
		int roomPosX = Integer.parseInt(inputRoomPosX.getText());
		int roomPosY = Integer.parseInt(inputRoomPosY.getText());
		mainControler.addRoom(roomName, roomFloor, roomDimX, roomDimY, roomDimZ, roomPosX, roomPosY);
	}
	
	/**
	 * demande au contrôleur principal de modifier une salle
	 */
	public void updateRoom() {
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		int id_room = selectedRoom.getId_room();
		String roomName = inputRoomName.getText();
		int roomFloor = Integer.parseInt(inputRoomFloor.getText());
		int roomDimX = Integer.parseInt(inputRoomDimX.getText());
		int roomDimY = Integer.parseInt(inputRoomDimY.getText());
		int roomDimZ = Integer.parseInt(inputRoomDimZ.getText());
		int roomPosX = Integer.parseInt(inputRoomPosX.getText());
		int roomPosY = Integer.parseInt(inputRoomPosY.getText());
		mainControler.updateRoom(id_room, roomName, roomFloor, roomDimX, roomDimY, roomDimZ, roomPosX, roomPosY);
	}
	
	/**
	 * demande au contrôleur principal de supprimer une salle
	 */
	public void deleteRoom() {
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		int id_room = selectedRoom.getId_room();
		mainControler.deleteRoom(id_room);
	}
	
	/**
	 * affiche la zone de création/modification de salle
	 */
	private void showRoomCreateEdit() {
		editRoom.setDisable(false);
		editAction.setDisable(true);
		createAction.setDisable(true);
		deleteAction.setDisable(true);
	}
	
	/**
	 * masque la zone de création/modification de salle
	 */
	private void resetRoomCreateEdit() {
		roomFormTitle.setText("");
		inputRoomName.setText("");
		inputRoomFloor.setText("");
		inputRoomDimX.setText("");
		inputRoomDimY.setText("");
		inputRoomDimZ.setText("");
		inputRoomPosX.setText("");
		inputRoomPosY.setText("");
		editRoom.setDisable(true);
		editAction.setDisable(false);
		createAction.setDisable(false);
		deleteAction.setDisable(false);
		addingRoom = false;
		updatingRoom = false;
	}
	
	/**
	 * à la demande du contrôleur principal, affiche une notification
	 */
	public void notifyRoomSaved(String message) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/NotifRoom.fxml"));
			// passage de ce contrôleur à la vue
			loader.setController(this);
			dialogRoomCreated = (Pane)loader.load();
			// désactivation de la zone d'édition de salle
			resetRoomCreateEdit();			
			// affichage de la fenêtre pop-up
			lblNotification.setText(message);
			Scene scene = new Scene(dialogRoomCreated);
			notifWindow.setScene(scene);
			notifWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// récupération de la table mise à jour
		roomTable.setItems(mainControler.getRoomData());
		roomTable.getSelectionModel().selectFirst();
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
		idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_room()+""));
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		floorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFloor()+""));
	}
	
	/**
	 * event listener du bouton "Créer" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomAddition(ActionEvent e) {
		addingRoom = true;
		roomFormTitle.setText("Création de salle");
		showRoomCreateEdit();
	}
	
	/**
	 * event listener du bouton "Modifier" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomUpdate(ActionEvent event) {
		// la modification est possible seulement si une salle est sélectionnée
		if (selectedRoomLine != -1) {
			try {
				Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
				updatingRoom = true;
				roomFormTitle.setText("Modification de salle");
				inputRoomName.setText(selectedRoom.getName());
				inputRoomFloor.setText(selectedRoom.getFloor()+"");
				inputRoomDimX.setText(selectedRoom.getDim_x()+"");
				inputRoomDimY.setText(selectedRoom.getDim_y()+"");
				inputRoomDimZ.setText(selectedRoom.getDim_z()+"");
				inputRoomPosX.setText(selectedRoom.getPos_x()+"");
				inputRoomPosY.setText(selectedRoom.getPos_y()+"");
				showRoomCreateEdit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * event listener du bouton "Supprimer" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomDeletion(ActionEvent e) {
		deleteRoom();
	}
	
	/**
	 * event listener du bouton "Annuler" la création/modification d'une salle
	 * @param e
	 */
	@FXML
	private void handleCancelRoomEdit(ActionEvent e) {
		resetRoomCreateEdit();
	}
	
	/**
	 * event listener du bouton "Enregistrer" une salle
	 * @param e
	 */
	@FXML
	private void handleSaveRoom(ActionEvent e) {
		if (addingRoom) {
			addRoom();
		}
		else if (updatingRoom) {
			updateRoom();
		}		
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification
	 * @param e
	 */
	@FXML
	private void confirmRoomCreated(ActionEvent e) {
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
