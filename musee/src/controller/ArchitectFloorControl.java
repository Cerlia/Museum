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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import museum.floorplan.Floor;

public class ArchitectFloorControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainControler;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedFloorLine = -1;
	private Stage notifWindow = new Stage();
	private Pane dialogFloorSaved;
	private boolean updatingFloor = false;
	private boolean addingFloor = false;
	
	@FXML
	private TableView<Floor> floorTable;
	@FXML
	private TableColumn<Floor, String> nameColumn;
	@FXML
	private AnchorPane pneFloorCreatEdit;
	@FXML
	private AnchorPane pneFloorDisplay;
	@FXML
	private Button btnCreateFloor;
	@FXML
	private Button btnEditFloor;
	@FXML
	private Button btnDeleteFloor;
	@FXML
	private CheckBox chbIgnoreFloorDim;
	@FXML
	private Label floorFormTitle;
	@FXML
	private Label lblMuseumName;
	@FXML
	private Label lblNotification;
	@FXML
	private Label lblFloorName;
	@FXML
	private Label lblDimX;
	@FXML
	private Label lblDimY;
	@FXML
	private TextField txtFloorName;
	@FXML
	private TextField txtFloorDimX;
	@FXML
	private TextField txtFloorDimY;
	
	
	/*  ---------------------------
	 * 
	 *           MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public ArchitectFloorControl() {
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
		floorTable.setItems(mainControler.getFloorData());
		lblMuseumName.setText(mainControler.getCurrentMuseum().getMuseum_name());
		showFloorInfo();
	}
	
	/**
	 * demande au contrôleur principal d'ajouter un étage
	 */
	public void addFloor() {
		try {
			String floorName = txtFloorName.getText();
			int floorDimX = Integer.parseInt(txtFloorDimX.getText());
			int floorDimY = Integer.parseInt(txtFloorDimY.getText());
			mainControler.addFloor(floorName, floorDimX, floorDimY);
		} catch (Exception e) {
			mainControler.notifyFail();
		}
	}
	
	/**
	 * demande au contrôleur principal de modifier un étage
	 */
	public void updateFloor() {
		try {
			Floor selectedFloor = floorTable.getItems().get(selectedFloorLine);
			int id_floor = selectedFloor.getId_floor();
			String floorName = txtFloorName.getText();
			int floorDimX = Integer.parseInt(txtFloorDimX.getText());
			int floorDimY = Integer.parseInt(txtFloorDimY.getText());
			mainControler.updateFloor(id_floor, floorName, floorDimX, floorDimY);
		} catch (Exception e) {
			mainControler.notifyFail();
		}		
	}
	
	/**
	 * demande au contrôleur principal de supprimer un étage
	 */
	public void deleteFloor() {
		// TODO en prenant en compte le fait qu'il faut supprimer les salles associées, etc
		/*
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		int id_room = selectedRoom.getId_room();
		mainControler.deleteRoom(id_room);
		*/
	}
	
	/**
	 * affiche la zone de création/modification d'étage
	 */
	private void showFloorEditingPane() {
		pneFloorCreatEdit.setVisible(true);
		btnCreateFloor.setDisable(true);
		hideFloorInfo();
	}
	
	/**
	 * affiche la zone des informations détage
	 */
	private void showFloorInfo() {
		if (selectedFloorLine != -1) {
			pneFloorCreatEdit.setVisible(false);
			pneFloorDisplay.setVisible(true);
			Floor selectedFloor = floorTable.getItems().get(selectedFloorLine);
			lblFloorName.setText(selectedFloor.getFloor_name());
			lblDimX.setText(selectedFloor.getDim_x()+"");
			lblDimY.setText(selectedFloor.getDim_y()+"");
			pneFloorDisplay.setVisible(true);
		}		
	}
	
	/**
	 * masque la zone des informations d'étage
	 */
	private void hideFloorInfo() {
		pneFloorDisplay.setVisible(false);
	}
	
	/**
	 * réinitialise et masque la zone de création/modification d'étage
	 */
	private void resetFloorCreateEdit() {
		floorFormTitle.setText("");
		txtFloorName.setText("");
		txtFloorDimX.setText("");
		txtFloorDimY.setText("");
		txtFloorDimX.setDisable(false);
		txtFloorDimY.setDisable(false);
		chbIgnoreFloorDim.setSelected(false);
		pneFloorCreatEdit.setVisible(false);
		btnCreateFloor.setDisable(false);
	}
	
	/**
	 * à la demande du contrôleur principal, affiche une notification
	 */
	public void notifyFloorSaved(String message) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
			notifWindow.setTitle("Confirmation de modification");
		};		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/NotifFloor.fxml"));
			loader.setController(this);
			dialogFloorSaved = (Pane)loader.load();
			// désactivation de la zone d'édition d'étage
			resetFloorCreateEdit();	
			showFloorInfo();
			floorTable.setDisable(false);
			// désactivation des drapeaux Création et Modification d'étage
			addingFloor = false;
			updatingFloor = false;
			// affichage de la fenêtre pop-up
			Scene scene = new Scene(dialogFloorSaved);
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
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFloor_name()));
	}
	
	/**
	 * event listener du bouton "Créer" un étage
	 * @param e
	 */
	@FXML
	private void handleFloorAddition(ActionEvent e) {
		addingFloor = true;
		floorFormTitle.setText("Création d'étage");
		floorTable.setDisable(true);
		showFloorEditingPane();
		hideFloorInfo();
	}
	
	/**
	 * event listener du bouton "Modifier" un étage
	 * @param e
	 */
	@FXML
	private void handleFloorUpdate(ActionEvent event) {
		Floor selectedFloor = floorTable.getItems().get(selectedFloorLine);
		updatingFloor = true;
		floorFormTitle.setText("Modification d'étage");
		chbIgnoreFloorDim.setSelected(false);
		txtFloorName.setText(selectedFloor.getFloor_name());
		txtFloorDimX.setText(selectedFloor.getDim_x()+"");
		txtFloorDimX.setDisable(false);
		txtFloorDimY.setText(selectedFloor.getDim_y()+"");
		txtFloorDimY.setDisable(false);
		showFloorEditingPane();
		hideFloorInfo();		
	}
	
	/**
	 * event listener du bouton "Supprimer" un étage
	 * @param e
	 */
	@FXML
	private void handleFloorDeletion(ActionEvent e) {
		deleteFloor();
	}
	
	/**
	 * event listener du bouton "Annuler" la création/modification d'un étage
	 * @param e
	 */
	@FXML
	private void handleCancelFloorEdit(ActionEvent e) {
		resetFloorCreateEdit();
		showFloorInfo();
		pneFloorCreatEdit.setVisible(false);
		btnCreateFloor.setDisable(false);
		floorTable.setDisable(false);
		addingFloor = false;
		updatingFloor = false;
	}
	
	/**
	 * event listener du bouton "Enregistrer" un étage
	 * @param e
	 */
	@FXML
	private void handleSaveFloor(ActionEvent e) {
		// on vérifie d'abord que tous les champs sont remplis
		if (txtFloorName.getText() != "" && txtFloorDimX.getText() != "" && txtFloorDimY.getText() != "") {
			if (addingFloor) {
				addFloor();
			}
			else if (updatingFloor) {
				updateFloor();
			}
		} else {
			mainControler.notifyFail();
		}				
	}
	
	@FXML
	private void handleIgnoreFloorDim() {
		if (chbIgnoreFloorDim.isSelected()) {
			txtFloorDimX.setText("0");
			txtFloorDimX.setDisable(true);
			txtFloorDimY.setText("0");
			txtFloorDimY.setDisable(true);
		} else {
			txtFloorDimX.setDisable(false);
			txtFloorDimY.setDisable(false);
		}		
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification
	 * @param e
	 */
	@FXML
	private void confirmFloorCreated(ActionEvent e) {
		notifWindow.close();
	}
	
	/**
	 * event listener de la liste d'étages, récupère la ligne sélectionnée et affiche les données
	 */
	@FXML
	private void handleFloorTableAction(MouseEvent event) {
		selectedFloorLine = floorTable.getSelectionModel().getSelectedIndex();
		showFloorInfo();
	}	
}
