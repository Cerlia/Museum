package controller;

import java.io.IOException;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import museum.floorplan.Floor;
import museum.floorplan.Room;

public class ArchitectRoomControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainControler;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedRoomLine = -1;
	private Stage notifWindow = new Stage();
	private Pane dialogRoomSaved;
	private boolean updatingRoom = false;
	private boolean addingRoom = false;
	
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> nameColumn;
	@FXML
	private TableColumn<Room, String> floorColumn;
	@FXML
	private Button btnEditRoom;
	@FXML
	private Button btnCreateRoom;
	@FXML
	private Button btnDeleteRoom;
	@FXML
	private CheckBox chbIgnoreRoomDim;
	@FXML
	private CheckBox chbIgnoreRoomPos;
	@FXML
	private ComboBox<Floor> cbbFloor;
	@FXML
	private Label roomFormTitle;
	@FXML
	private Label lblMuseumName;
	@FXML
	private Label lblRoomName;
	@FXML
	private Label lblFloorName;
	@FXML
	private Label lblDimX;
	@FXML
	private Label lblDimY;
	@FXML
	private Label lblDimZ;
	@FXML
	private Label lblPosX;
	@FXML
	private Label lblPosY;
	@FXML
	private Pane pneRoomCreatEdit;
	@FXML
	private Pane pneRoomDisplay;
	@FXML
	private TextField txtRoomName;
	@FXML
	private TextField txtRoomDimX;
	@FXML
	private TextField txtRoomDimY;
	@FXML
	private TextField txtRoomDimZ;
	@FXML
	private TextField txtRoomPosX;
	@FXML
	private TextField txtRoomPosY;
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
	public ArchitectRoomControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainControler
	 */
	public void setMainControl(Main mainControler) {
		this.mainControler = mainControler;
		refreshData();
	}
	
	/**
	 * récupère les dernières infos de la BD
	 */
	public void refreshData() {
		roomTable.setItems(mainControler.getRoomData());
		cbbFloor.setItems(mainControler.getFloorData());
		lblMuseumName.setText(mainControler.getCurrentMuseum().getMuseum_name());
		showRoomInfo();
	}
	
	/**
	 * demande au contrôleur principal d'ajouter une salle
	 */
	public void addRoom() {
		try {
			String roomName = txtRoomName.getText();
			Floor floor = cbbFloor.getValue();
			int roomDimX = Integer.parseInt(txtRoomDimX.getText());
			int roomDimY = Integer.parseInt(txtRoomDimY.getText());
			int roomDimZ = Integer.parseInt(txtRoomDimZ.getText());
			int roomPosX = Integer.parseInt(txtRoomPosX.getText());
			int roomPosY = Integer.parseInt(txtRoomPosY.getText());
			mainControler.addRoom(roomName, floor, roomDimX, roomDimY, roomDimZ, roomPosX, roomPosY);
		} catch (Exception e) {
			mainControler.notifyFail();
		}		
	}
	
	/**
	 * demande au contrôleur principal de modifier une salle
	 */
	public void updateRoom() {
		try {
			Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
			int id_room = selectedRoom.getId_room();
			String roomName = txtRoomName.getText();
			Floor floor = cbbFloor.getValue();
			int roomDimX = Integer.parseInt(txtRoomDimX.getText());
			int roomDimY = Integer.parseInt(txtRoomDimY.getText());
			int roomDimZ = Integer.parseInt(txtRoomDimZ.getText());
			int roomPosX = Integer.parseInt(txtRoomPosX.getText());
			int roomPosY = Integer.parseInt(txtRoomPosY.getText());
			mainControler.updateRoom(id_room, roomName, floor, roomDimX, roomDimY, roomDimZ, roomPosX, roomPosY);
		} catch (Exception e) {
			mainControler.notifyFail();
		}		
	}
	
	/**
	 * demande au contrôleur principal de supprimer une salle
	 */
	public void deleteRoom() {
		// TODO à revoir, salle doit être supprimée avec tout ce qui en dépend
		/*
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		int id_room = selectedRoom.getId_room();
		mainControler.deleteRoom(id_room);
		*/
	}
	
	/**
	 * affiche la zone de création/modification de salle et masque la zone d'infos
	 */
	private void showRoomEditingPane() {
		pneRoomCreatEdit.setVisible(true);
		btnCreateRoom.setDisable(true);
		hideRoomInfo();
	}
	
	/**
	 * affiche la zone des informations de la salle
	 */
	private void showRoomInfo() {
		if (selectedRoomLine != -1) {
			pneRoomCreatEdit.setVisible(false);
			pneRoomDisplay.setVisible(true);
			Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
			lblRoomName.setText(selectedRoom.getName());
			lblFloorName.setText(selectedRoom.getFloor().getFloor_name());
			lblDimX.setText(selectedRoom.getDim_x()+"");
			lblDimY.setText(selectedRoom.getDim_y()+"");
			lblDimZ.setText(selectedRoom.getDim_z()+"");
			lblPosX.setText(selectedRoom.getPos_x()+"");
			lblPosY.setText(selectedRoom.getPos_y()+"");
			pneRoomDisplay.setVisible(true);
		}		
	}
	
	/**
	 * masque la zone des informations de salle
	 */
	private void hideRoomInfo() {
		pneRoomDisplay.setVisible(false);
	}
	
	/**
	 * réinitialise et masque la zone de création/modification de salle
	 */
	private void resetRoomCreateEdit() {
		roomFormTitle.setText("");
		txtRoomName.setText("");
		txtRoomDimX.setText("");
		txtRoomDimY.setText("");
		txtRoomDimZ.setText("");
		txtRoomPosX.setText("");
		txtRoomPosY.setText("");
		txtRoomDimX.setDisable(false);
		txtRoomDimY.setDisable(false);
		txtRoomDimZ.setDisable(false);
		txtRoomPosX.setDisable(false);
		txtRoomPosY.setDisable(false);
		chbIgnoreRoomDim.setSelected(false);
		chbIgnoreRoomPos.setSelected(false);
		pneRoomCreatEdit.setVisible(false);
		btnCreateRoom.setDisable(false);
	}
	
	
	/**
	 * à la demande du contrôleur principal, affiche une notification
	 */
	public void notifyRoomSaved(String message) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
			notifWindow.setTitle("Confirmation de modification");
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/NotifRoom.fxml"));
			// passage de ce contrôleur à la vue
			loader.setController(this);
			dialogRoomSaved = (Pane)loader.load();
			// désactivation de la zone d'édition de salle
			resetRoomCreateEdit();
			showRoomInfo();
			roomTable.setDisable(false);
			// désactivation des drapeaux Création et Modification de salle
			addingRoom = false;
			updatingRoom = false;			
			// affichage de la fenêtre pop-up
			Scene scene = new Scene(dialogRoomSaved);
			lblNotification.setText(message);
			notifWindow.setScene(scene);
			notifWindow.show();
			// rafraîchissement des données
			refreshData();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		floorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFloor().getFloor_name()+""));
	}
	
	/**
	 * event listener du bouton "Créer" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomAddition(ActionEvent e) {
		addingRoom = true;
		roomFormTitle.setText("Création de salle");
		roomTable.setDisable(true);
		showRoomEditingPane();
	}
	
	/**
	 * event listener du bouton "Modifier" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomUpdate(ActionEvent event) {
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		updatingRoom = true;
		roomFormTitle.setText("Modification de salle");
		txtRoomName.setText(selectedRoom.getName());
		cbbFloor.setValue(selectedRoom.getFloor());
		txtRoomDimX.setText(selectedRoom.getDim_x()+"");
		txtRoomDimY.setText(selectedRoom.getDim_y()+"");
		txtRoomDimZ.setText(selectedRoom.getDim_z()+"");
		txtRoomPosX.setText(selectedRoom.getPos_x()+"");
		txtRoomPosY.setText(selectedRoom.getPos_y()+"");
		showRoomEditingPane();
		hideRoomInfo();
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
		showRoomInfo();
		pneRoomCreatEdit.setVisible(false);
		btnCreateRoom.setDisable(false);
		roomTable.setDisable(false);
		addingRoom = false;
		updatingRoom = false;
	}
	
	/**
	 * event listener du bouton "Enregistrer" une salle
	 * @param e
	 */
	@FXML
	private void handleSaveRoom(ActionEvent e) {
		// on vérifie d'abord que tous les champs sont remplis
		if (txtRoomName.getText() != "" && txtRoomDimX.getText() != "" && txtRoomDimY.getText() != ""
				&& txtRoomDimZ.getText() != "" && txtRoomPosX.getText() != "" && txtRoomPosY.getText() != "" &&
				cbbFloor.getValue() != null) {
			if (addingRoom) {
				addRoom();
			}
			else if (updatingRoom) {
				updateRoom();
			}	
		}
		else {
			mainControler.notifyFail();
		}
	}
	
	@FXML
	private void handleIgnoreRoomDim() {
		if (chbIgnoreRoomDim.isSelected()) {
			txtRoomDimX.setText("0");
			txtRoomDimX.setDisable(true);
			txtRoomDimY.setText("0");
			txtRoomDimY.setDisable(true);
			txtRoomDimZ.setText("0");
			txtRoomDimZ.setDisable(true);
		} else {
			txtRoomDimX.setDisable(false);
			txtRoomDimY.setDisable(false);
			txtRoomDimZ.setDisable(false);
		}		
	}
	
	@FXML
	private void handleIgnoreRoomPos() {
		if (chbIgnoreRoomPos.isSelected()) {
			txtRoomPosX.setText("0");
			txtRoomPosX.setDisable(true);
			txtRoomPosY.setText("0");
			txtRoomPosY.setDisable(true);
		} else {
			txtRoomPosX.setDisable(false);
			txtRoomPosY.setDisable(false);
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
		showRoomInfo();
	}
}
