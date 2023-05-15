package controller;

import java.util.List;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
// TODO C'est bien normal que le contrôleur de la vue ait accès au modèle, oui ?
import museum.floorplan.Floor;
import museum.floorplan.Room;
import museum.floorplan.Surface;
import utils.MeasureConversion;

public class ArchitectRoomControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedRoomLine = -1;
	private boolean updatingRoom = false;
	private boolean addingRoom = false;
	
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> nameColumn;
	@FXML
	private TableColumn<Room, String> floorColumn;
	@FXML
	private TableColumn<Room, String> nbArtColumn;
	@FXML
	private Button btnEditRoom;
	@FXML
	private Button btnCreateRoom;
	@FXML
	private Button btnDeleteRoom;
	@FXML
	private Button btnShowFloor;
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
	private CheckBox chbIgnoreRoomDim;
	@FXML
	private CheckBox chbIgnoreRoomPos;
	@FXML
	private ComboBox<Floor> cbbFloor;
	@FXML
	private Label roomFormTitle;
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
	private Label lblSurfaceName;
	@FXML
	private Label lblSurfaceX;
	@FXML
	private Label lblSurfaceY;
	@FXML
	private Label lblSurfaceZ;
	@FXML
	private Pane pneRoomCreatEdit;
	@FXML
	private Pane pneRoomDisplay;
	@FXML
	private Pane pneSurfaceInfo;
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
	public void setMainControl(Main mainController) {
		this.mainController = mainController;
	}
	
	/**
	 * récupère les dernières infos de la BD
	 */
	public void refreshData() {
		roomTable.setItems(mainController.getRoomData());
		roomTable.refresh();
		if (mainController.getRoomData().size() > 0) {
			selectedRoomLine = 0;
			roomTable.getSelectionModel().select(0);
			showRoomInfo();
		} else {
			selectedRoomLine = -1;
			hideRoomInfo();
		}
		cbbFloor.setItems(mainController.getFloorData());
	}
	
	/**
	 * demande au contrôleur principal d'ajouter une salle
	 */
	public void addRoom() {
		try {
			String name = txtRoomName.getText();
			Floor floor = cbbFloor.getValue();
			int dimX = MeasureConversion.textToInt(txtRoomDimX.getText());
			int dimY = MeasureConversion.textToInt(txtRoomDimY.getText());
			int dimZ = MeasureConversion.textToInt(txtRoomDimZ.getText());
			int posX = Integer.parseInt(txtRoomPosX.getText());
			int posY = Integer.parseInt(txtRoomPosY.getText());
			Room room = new Room(name, dimX, dimY, dimZ, posX, posY, floor, null);	
			mainController.addRoom(room);
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de la salle");
		}		
	}
	
	/**
	 * demande au contrôleur principal de modifier une salle
	 */
	public void updateRoom() {
		try {
			Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
			selectedRoom.setName(txtRoomName.getText());
			selectedRoom.setFloor(cbbFloor.getValue());
			selectedRoom.setDim_x(MeasureConversion.textToInt(txtRoomDimX.getText()));
			selectedRoom.setDim_y(MeasureConversion.textToInt(txtRoomDimY.getText()));
			selectedRoom.setDim_z(MeasureConversion.textToInt(txtRoomDimZ.getText()));
			selectedRoom.setPos_x(Integer.parseInt(txtRoomPosX.getText()));
			selectedRoom.setPos_y(Integer.parseInt(txtRoomPosY.getText()));
			mainController.updateRoom(selectedRoom);
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de la salle");
		}		
	}
	
	/**
	 * demande au contrôleur principal de supprimer une salle
	 */
	public void deleteRoom() {
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		int id_room = selectedRoom.getId_room();
		mainController.deleteRoom(id_room);
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
			Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
			lblRoomName.setText(selectedRoom.getName());
			lblFloorName.setText(selectedRoom.getFloor().getFloor_name());
			lblDimX.setText(MeasureConversion.intToString(selectedRoom.getDim_x()));
			lblDimY.setText(MeasureConversion.intToString(selectedRoom.getDim_y()));
			lblDimZ.setText(MeasureConversion.intToString(selectedRoom.getDim_z()));
			lblPosX.setText(selectedRoom.getPos_x()+"");
			lblPosY.setText(selectedRoom.getPos_y()+"");
			pneRoomCreatEdit.setVisible(false);
			pneRoomDisplay.setVisible(true);
			pneSurfaceInfo.setVisible(false);
		}		
	}
	
	/**
	 * masque la zone des informations de salle
	 */
	private void hideRoomInfo() {
		pneRoomDisplay.setVisible(false);
	}
	
	/**
	 * affiche le panneau de détails à propos d'une surface
	 * @param surfaceNb numéro de la surface dont il faut afficher les infos
	 */
	private void showSurfaceInfo(int surfaceNb) {
		if (this.selectedRoomLine > -1) {
			Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
			List<Surface> surfaces = selectedRoom.getSurfaces();
			lblSurfaceName.setText(surfaces.get(surfaceNb).getName());
			lblSurfaceX.setText(MeasureConversion.intToString(surfaces.get(surfaceNb).getDim_x()));
			lblSurfaceY.setText(MeasureConversion.intToString(surfaces.get(surfaceNb).getDim_y()));
			lblSurfaceZ.setText(MeasureConversion.intToString(surfaces.get(surfaceNb).getDim_z()));
			pneSurfaceInfo.setVisible(true);
		}
	}
	
	
	/**
	 * réinitialise et masque la zone de création/modification de salle
	 */
	public void resetRoomCreateEdit() {
		// rafraîchissement des données
		refreshData();
		// désactivation des drapeaux Création et Modification de salle
		addingRoom = false;
		updatingRoom = false;	
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
		showRoomInfo();
		roomTable.setDisable(false);
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
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		floorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFloor().getFloor_name()+""));
		nbArtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
				mainController.getAllArtsOfRoom(cellData.getValue()).size()+""));
	}
	
	/**
	 * event listener du bouton "Créer" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomAddition(ActionEvent e) {
		addingRoom = true;
		roomFormTitle.setText("Créer une nouvelle salle");
		roomTable.setDisable(true);
		showRoomEditingPane();
	}
	
	/**
	 * event listener du bouton "Modifier" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomUpdate(ActionEvent event) {
		Room room = roomTable.getItems().get(selectedRoomLine);
		if (mainController.getAllArtsOfRoom(room).size() == 0) {
			Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
			updatingRoom = true;
			roomFormTitle.setText("Modifier une salle existante");
			txtRoomName.setText(selectedRoom.getName());
			cbbFloor.setValue(selectedRoom.getFloor());
			txtRoomDimX.setText(MeasureConversion.intToString(selectedRoom.getDim_x()));
			txtRoomDimY.setText(MeasureConversion.intToString(selectedRoom.getDim_y()));
			txtRoomDimZ.setText(MeasureConversion.intToString(selectedRoom.getDim_z()));
			txtRoomPosX.setText(selectedRoom.getPos_x()/100+"");
			txtRoomPosY.setText(selectedRoom.getPos_y()/100+"");
			showRoomEditingPane();
			hideRoomInfo();
		}
		else {
			mainController.notifyFail("Vous ne pouvez pas modifier une salle ayant des œuvres en exposition");
		}
	}
	
	/**
	 * event listener du bouton "Supprimer" une salle
	 * @param e
	 */
	@FXML
	private void handleRoomDeletion(ActionEvent e) {
		Room room = roomTable.getItems().get(selectedRoomLine);
		if (mainController.getAllArtsOfRoom(room).size() == 0) {
			deleteRoom();
		}
		else {
			mainController.notifyFail("Vous ne pouvez pas supprimer une salle ayant des œuvres en exposition");
		}		
	}
	
	/**
	 * event listener du bouton "Voir l'étage" d'une salle
	 * @param e
	 */
	@FXML
	private void handleShowFloor(ActionEvent e) {
		mainController.showArchitectFloorPane();
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
			mainController.notifyFail(null);
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
	 * event listener de la liste de salles, permet de récupérer la ligne sélectionnée (clic)
	 */
	@FXML
	private void handleRoomTableAction(MouseEvent event) {
		selectedRoomLine = roomTable.getSelectionModel().getSelectedIndex();
		showRoomInfo();
	}
	
	/**
	 * event listener de la liste de salles, permet de récupérer la ligne sélectionnée (bouton)
	 */
	@FXML
	private void handleRoomTableKeyPressed(KeyEvent event) {
		selectedRoomLine = roomTable.getSelectionModel().getSelectedIndex();
		showRoomInfo();
	}
	
	/**
	 * event listener des 5 boutons de surface visibles dans les infos d'une salle
	 */
	@FXML
	private void handleBtnSurfaceAction(ActionEvent event) {
		if (selectedRoomLine != -1) {
			int surfaceNb = 0;
			// TODO ici equals peut être remplacé par le plus simple ==, à vérifier
			if (event.getSource() == btnFloor) {
				surfaceNb = 0;
			} else if (event.getSource() == btnWall1) {
				surfaceNb = 1;
			} else if (event.getSource() == btnWall2) {
				surfaceNb = 2;
			} else if (event.getSource() == btnWall3) {
				surfaceNb = 3;
			} else if (event.getSource() == btnWall4) {
				surfaceNb = 4;
			}
			showSurfaceInfo(surfaceNb);
		}		
	}
}
