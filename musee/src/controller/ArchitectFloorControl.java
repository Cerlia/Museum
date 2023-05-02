package controller;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import museum.floorplan.Floor;
import utils.MeasureConversion;

public class ArchitectFloorControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedFloorLine = -1;
	private boolean updatingFloor = false;
	private boolean addingFloor = false;
	
	@FXML
	private TableView<Floor> floorTable;
	@FXML
	private TableColumn<Floor, String> nameColumn;
	@FXML
	private TableColumn<Floor, String> nbRoomColumn;
	@FXML
	private TableColumn<Floor, String> nbArtsColumn;
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
	private Button btnShowRooms;
	@FXML
	private CheckBox chbIgnoreFloorDim;
	@FXML
	private Label floorFormTitle;
	@FXML
	private Label lblFloorName;
	@FXML
	private Label lblDimX;
	@FXML
	private Label lblDimY;
	@FXML
	private Label lblNbArts;
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
	public void setMainControl(Main mainController) {
		this.mainController = mainController;	
	}
	
	/**
	 * récupère les dernières infos de la BD
	 */
	public void refreshData() {
		floorTable.setItems(mainController.getFloorData());
		if (mainController.getFloorData().size() > 0) {
			selectedFloorLine = 0;
			floorTable.getSelectionModel().select(0);
			showFloorInfo();
		} else {
			selectedFloorLine = -1;
			hideFloorInfo();
		}		
	}
	
	/**
	 * demande au contrôleur principal d'ajouter un étage
	 */
	public void addFloor() {
		try {
			String floorName = txtFloorName.getText();
			int floorDimX = MeasureConversion.textToInt(txtFloorDimX.getText());
			int floorDimY = MeasureConversion.textToInt(txtFloorDimY.getText());
			mainController.addFloor(floorName, floorDimX, floorDimY);
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de l'étage");
		}
	}
	
	/**
	 * demande au contrôleur principal de modifier un étage
	 */
	public void updateFloor() {
		try {
			Floor selectedFloor = floorTable.getItems().get(selectedFloorLine);
			selectedFloor.setFloor_name(txtFloorName.getText());
			selectedFloor.setDim_x(MeasureConversion.textToInt(txtFloorDimX.getText()));
			selectedFloor.setDim_y(MeasureConversion.textToInt(txtFloorDimY.getText()));			
			mainController.updateFloor(selectedFloor);
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de l'étage");
		}		
	}
	
	/**
	 * demande au contrôleur principal de supprimer un étage
	 */
	public void deleteFloor() {
		Floor floor = floorTable.getItems().get(selectedFloorLine);
		if (mainController.getAllArtsOfFloor(floor).size() > 0) {
			mainController.notifyInfo("Impossible de supprimer un étage comportant "
					+ "des œuvres en exposition");
		} else {
			mainController.notifyConfirmDelete("Suppression d'étage", "Voulez-vous vraiment "
					+ "supprimer cet étage et toutes les salles qu'il contient ?", floor);
		}
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
			lblDimX.setText(MeasureConversion.intToString(selectedFloor.getDim_x()));
			lblDimY.setText(MeasureConversion.intToString(selectedFloor.getDim_y()));
			lblNbArts.setText(mainController.getAllArtsOfFloor(selectedFloor).size()+"");
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
	public void resetFloorCreateEdit() {
		// rafraîchissement des données
		refreshData();
		// désactivation des drapeaux Création et Modification d'étage
		addingFloor = false;
		updatingFloor = false;
		floorFormTitle.setText("");
		txtFloorName.setText("");
		txtFloorDimX.setText("");
		txtFloorDimY.setText("");
		txtFloorDimX.setDisable(false);
		txtFloorDimY.setDisable(false);
		chbIgnoreFloorDim.setSelected(false);
		pneFloorCreatEdit.setVisible(false);
		btnCreateFloor.setDisable(false);
		floorTable.setDisable(false);
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
		nbRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRooms().size()+""));
		nbArtsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(mainController.getAllArtsOfFloor(cellData.getValue()).size()+""));
	}
	
	/**
	 * event listener du bouton "Créer" un étage
	 * @param e
	 */
	@FXML
	private void handleFloorAddition(ActionEvent e) {
		addingFloor = true;
		floorFormTitle.setText("Créer un nouvel étage");
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
		floorFormTitle.setText("Modifier un étage existant");
		chbIgnoreFloorDim.setSelected(false);
		txtFloorName.setText(selectedFloor.getFloor_name());
		txtFloorDimX.setText(MeasureConversion.intToString(selectedFloor.getDim_x()));
		txtFloorDimX.setDisable(false);
		txtFloorDimY.setText(MeasureConversion.intToString(selectedFloor.getDim_y()));
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
	 * event listener du bouton "Voir les salles" d'un étage
	 * @param e
	 */
	@FXML
	private void handleShowRooms(ActionEvent e) {
		mainController.showArchitectRoomPane();
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
			mainController.notifyFail(null);
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
	 * event listener de la liste d'étages, permet de récupérer la ligne sélectionnée (clic)
	 */
	@FXML
	private void handleFloorTableAction(MouseEvent event) {
		selectedFloorLine = floorTable.getSelectionModel().getSelectedIndex();
		showFloorInfo();
	}
	
	/**
	 * event listener de la liste d'étages, permet de récupérer la ligne sélectionnée (bouton)
	 */
	@FXML
	private void handleFloorTableKeyPressed(KeyEvent event) {
		selectedFloorLine = floorTable.getSelectionModel().getSelectedIndex();
		showFloorInfo();	
	}
}
