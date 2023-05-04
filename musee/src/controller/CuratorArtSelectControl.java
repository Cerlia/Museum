package controller;

import application.Main;
import dao.art.ArtDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import museum.art.Art;
import museum.display.Display;
import museum.display.DisplayModel;
import museum.floorplan.Room;
import museum.floorplan.Surface;

public class CuratorArtSelectControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut la première
	private int selectedArtLine = -1;
	private int selectedRoomLine = -1;
	private int selectedSurfaceLine = -1;
	private int selectedExistingDisplayLine = -1;
	private int selectedNewDisplayLine = -1;
	private boolean newDisplay = false;
	
	@FXML
	private ChoiceBox<Room> chbRoomChoice;
	@FXML
	private ChoiceBox<Surface> chbSurfaceChoice;
	@FXML
	private ChoiceBox<Display> chbDisplayChoice;
	@FXML
	private Button btnConfirmExhibit;
	@FXML
	private Button btnCancelSelectArt;
	@FXML
	private Button btnNextStep1;
	@FXML
	private Button btnNextStep2;
	@FXML
	private Button btnPreviousStep2;
	@FXML
	private Button btnPreviousStep3;
	@FXML
	private Label lblArtSelected;
	@FXML
	private Label lblRoomSurfaceSelected;
	@FXML
	private Label lblDisplaySelected;
	@FXML
	private Pane pneStep1;
	@FXML
	private Pane pneStep2;
	@FXML
	private Pane pneStep3;
	@FXML
	private Pane pneExistingDisplay;
	@FXML
	private Pane pneNewDisplay;
	@FXML
	private RadioButton rdoExistingDisplay;
	@FXML
	private RadioButton rdoNewDisplay;
	@FXML
	private TableView<Art> artTable;
	@FXML
	private TableColumn<Art, String> titleColumn;
	@FXML
	private TableColumn<Art, String> codeColumn;
	@FXML
	private TableColumn<Art, String> authorColumn;
	@FXML
	private TableColumn<Art, String> exhStatusColumn;
	@FXML
	private TableView<Display> tblExistingDisplay;	
	@FXML
	private TableColumn<Display, String> displayNameColumn;
	@FXML
	private TableColumn<Display, String> displayModelColumn;
	@FXML
	private TableColumn<Display, String> displayTypeColumn;
	@FXML
	private TableColumn<Display, String> displayArtNbColumn;
	@FXML
	private TableColumn<Display, String> displayXColumn;
	@FXML
	private TableColumn<Display, String> displayYColumn;
	@FXML
	private TableColumn<Display, String> displayZColumn;
	@FXML
	private TableColumn<Display, String> displayMultipleColumn;
	@FXML
	private TableView<DisplayModel> tblNewDisplay;
	@FXML
	private TableColumn<DisplayModel, String> newDispModelColumn;
	@FXML
	private TableColumn<DisplayModel, String> newDispTypeColumn;
	@FXML
	private TableColumn<DisplayModel, String> newDispXColumn;
	@FXML
	private TableColumn<DisplayModel, String> newDispYColumn;
	@FXML
	private TableColumn<DisplayModel, String> newDispZColumn;
	@FXML
	private TableColumn<DisplayModel, String> newDispMultipleColumn;
	@FXML
	private TextField txtDisplayName;
	
	
	
	/*  ---------------------------
	 * 
	 *          MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public CuratorArtSelectControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainControler
	 */
	public void setMainControl(Main mainController) {
		this.mainController = mainController;
	}
	
	public void refreshData() {
		artTable.setItems(mainController.getArtData());
	}
	
	public Display addDisplay(String name, Art art) {
		Surface surface = chbSurfaceChoice.getItems().get(selectedSurfaceLine);
		DisplayModel dispMod = tblNewDisplay.getItems().get(selectedNewDisplayLine);
		// par défaut, les dimensions du présentoir seront celles du modèle de présentoir
		int dimX = dispMod.getDim_x();
		int dimY = dispMod.getDim_y();
		int dimZ = dispMod.getDim_z();
		// ... sauf si c'est un modèle sans dimensions
		if (dimX == 0) {
			dimX = art.getDim_x();
			dimY = art.getDim_y();
			dimZ = art.getDim_z();
		}		
		Display display = new Display(name, dimX, dimY, dimZ, surface, dispMod, null);
		return mainController.addDisplay(display);
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
		titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_title()));
		codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_code()));
		authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor().getLast_name()));
		exhStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay() != null ?
				"Exposée (" + cellData.getValue().getDisplay().getSurface().getRoom().getName() + ")" : "En réserve"));
		ToggleGroup toggleGroup = new ToggleGroup();
		rdoExistingDisplay.setToggleGroup(toggleGroup);
		rdoNewDisplay.setToggleGroup(toggleGroup);		
		displayNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		displayModelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay_model().getName()));
		displayTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay_model().getDisplay_type().getName()));
		displayXColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_x()+""));
		displayYColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_y()+""));
		displayZColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_z()+""));
		displayArtNbColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArts().size()+""));
		displayMultipleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getDisplay_model().isDisplay_multiple() ? "Oui" : "Non"));
		newDispModelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		newDispTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplay_type().getName()));
		newDispXColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_x()+""));
		newDispYColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_y()+""));
		newDispZColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDim_z()+""));
		newDispMultipleColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().isDisplay_multiple() ? "Oui" : "Non"));
	}
		
	/**
	 * event listener de la liste d'œuvres, permet de récupérer la ligne sélectionnée
	 */
	@FXML
	private void handleArtTableAction(MouseEvent event) {
		try {
			selectedArtLine = artTable.getSelectionModel().getSelectedIndex();
			Art art = artTable.getItems().get(selectedArtLine);
			lblArtSelected.setText(art.getArt_title());
			chbRoomChoice.setItems(mainController.getRoomData());
			chbSurfaceChoice.getItems().clear();
			// quand une œuvre est sélectionnée, le bouton Suivant est activé
			btnNextStep1.setDisable(false);
		} catch (Exception e) {
			
		}
		
	}
	
	/**
	 * event listener du menu déroulant des salles
	 */
	@FXML
	private void handleRoomChoiceAction(ActionEvent event) {
		selectedRoomLine = chbRoomChoice.getSelectionModel().getSelectedIndex();
		if (selectedRoomLine != -1) {	
			Room selectedRoom = chbRoomChoice.getItems().get(selectedRoomLine);
			chbSurfaceChoice.setItems(mainController.getAllSurfacesOfRoom(selectedRoom));
		}
		lblRoomSurfaceSelected.setText("-");
		btnNextStep2.setDisable(true);
	}
	
	/**
	 * event listener du menu déroulant des surfaces
	 */
	@FXML
	private void handleSurfaceChoiceAction(ActionEvent event) {
		selectedSurfaceLine = chbSurfaceChoice.getSelectionModel().getSelectedIndex();
		lblDisplaySelected.setText("-");
		if (selectedRoomLine != -1 && selectedSurfaceLine != -1) {			
			String roomSurface = chbRoomChoice.getItems().get(selectedRoomLine).getName() + " - "
					+ chbSurfaceChoice.getItems().get(selectedSurfaceLine).getName();		
			lblRoomSurfaceSelected.setText(roomSurface);
			// quand une salle et une surface sont sélectionnées, le bouton Suivant est activé
			btnNextStep2.setDisable(false);
		}
	}
	
	/**
	 * event listener des boutons Suivant
	 */
	@FXML
	private void handleBtnNextStep(ActionEvent event) {
		if (event.getSource().equals(btnNextStep1)) {
			pneStep1.setVisible(false);
			pneStep2.setVisible(true);
			if (selectedRoomLine == -1 || selectedSurfaceLine == -1) {
				btnNextStep2.setDisable(true);
			}			
		} else if (event.getSource().equals(btnNextStep2)) {
			pneStep2.setVisible(false);
			pneStep3.setVisible(true);
			rdoNewDisplay.setSelected(false);
			rdoNewDisplay.fire();
		}
	}
	
	/**
	 * event listener des boutons Précédent
	 */
	@FXML
	private void handleBtnPreviousStep(ActionEvent event) {
		if (event.getSource().equals(btnPreviousStep2)) {
			pneStep2.setVisible(false);
			pneStep1.setVisible(true);
			if (selectedArtLine == -1) {
				btnNextStep1.setDisable(true);
			}			
		} else if (event.getSource().equals(btnPreviousStep3)) {
			pneStep3.setVisible(false);
			pneStep2.setVisible(true);
		}
	}
	
	/**
	 * event listener des boutons radio de choix de présentoir (existant/nouveau)
	 * @param event
	 */
	@FXML
	private void handleRadioButtonDisplay(ActionEvent event) {
		Surface surface = chbSurfaceChoice.getItems().get(selectedSurfaceLine);
		Art art = artTable.getItems().get(selectedArtLine);
		if (event.getSource().equals(rdoExistingDisplay)) {
			newDisplay = false;
			selectedNewDisplayLine = -1;
			pneExistingDisplay.setVisible(true);			
			tblExistingDisplay.setItems(mainController.getAllCompatibleExistingDisplays(surface, art));
			pneNewDisplay.setVisible(false);
		} else if (event.getSource().equals(rdoNewDisplay)) {
			newDisplay = true;
			selectedExistingDisplayLine = -1;
			pneNewDisplay.setVisible(true);
			tblNewDisplay.setItems(mainController.getAllCompatibleDisplayModels(surface, art));
			pneExistingDisplay.setVisible(false);
			txtDisplayName.setText("");
		}
		btnConfirmExhibit.setDisable(true);
	}
	
	/**
	 * event listener du bouton pour Annuler l'opération d'exposition d'une œuvre
	 * @param e
	 */
	@FXML
	private void handleCancelSelectArt(ActionEvent event) {
		Stage stage = (Stage)btnCancelSelectArt.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * event listener de la table de présentoirs compatibles existants
	 * @param event
	 */
	@FXML
	private void handleTableExistingDisplayAction(MouseEvent event) {
		selectedExistingDisplayLine = tblExistingDisplay.getSelectionModel().getSelectedIndex();
		Display display = tblExistingDisplay.getItems().get(selectedExistingDisplayLine);
		lblDisplaySelected.setText(display.getName() + " - " + display.getDisplay_model().getName() + " - " +
				display.getDisplay_model().getDisplay_type().getName());
		btnConfirmExhibit.setDisable(false);
	}
	
	/**
	 * event listener de la table de modèles de présentoirs compatibles
	 * @param event
	 */
	@FXML
	private void handleTableNewDisplayAction(MouseEvent event) {
		selectedNewDisplayLine = tblNewDisplay.getSelectionModel().getSelectedIndex();
		DisplayModel displayModel = tblNewDisplay.getItems().get(selectedNewDisplayLine);
		lblDisplaySelected.setText(displayModel.getName() + " - " +
				displayModel.getDisplay_type().getName());
	}
	
	public void handleBtnConfirmExhibit(ActionEvent event) {
		int art_id = artTable.getItems().get(selectedArtLine).getId_art();
		Art art = mainController.getFullArtData(art_id);
		// SI l'œuvre est déjà exposée dans un présentoir pour 1 seule œuvre
		if (art.getDisplay() != null && art.getDisplay().getDisplay_model().isDisplay_multiple() == false) {
			mainController.deleteDisplay(art.getDisplay());
		};
		Display display = null;
		// SI le présentoir choisi existe déjà
		if (newDisplay == false) {
			display = tblExistingDisplay.getItems().get(selectedExistingDisplayLine);
		}				
		// SI le présentoir choisi est nouveau
		if (newDisplay == true) {
			String name = txtDisplayName.getText();
			display = addDisplay(name, art);
		};
		// Dans tous les cas, update de l'œuvre avec la référence du présentoir
		art.setDisplay(display);
		mainController.updateArt(art, "artExhibit");
		display.setArts(ArtDAO.getInstance().reloadArtsOfDisplay(art.getDisplay().getId_display()));		
	}
	
	/**
	 * event listener du champ Nom du présentoir
	 * @param event
	 */
	@FXML
	private void handleTxtDisplayNameAction(KeyEvent event) {
		if (!txtDisplayName.getText().equals("") && selectedNewDisplayLine != -1) {
			btnConfirmExhibit.setDisable(false);
		} else {
			btnConfirmExhibit.setDisable(true);
		}
	}
}
