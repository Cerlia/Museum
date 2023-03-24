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
import museum.art.Author;

public class CuratorAuthorSelectControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut la première
	private int selectedAuthorLine = 0;
	private Stage notifWindow = new Stage();
	private boolean updatingAuthor = false;
	private boolean addingAuthor = false;
	private Pane dialogAuthorSaved;
	
	@FXML
	private Button btnAddAuthor;
	@FXML
	private Button btnSaveAuthor;
	@FXML
	private Button btnCancelAuthor;
	@FXML
	private Button btnUpdateAuthor;
	@FXML
	private Button btnDeleteAuthor;
	@FXML
	private Button btnSelectAuthor;
	@FXML
	private Button btnCancelSelectAuthor;
	@FXML
	private Label lblAuthorCreatEditTitle;
	@FXML
	private Label lblNotification;
	@FXML
	private AnchorPane pneAuthorCreatEdit;
	@FXML
	private TableView<Author> authorTable;
	@FXML
	private TableColumn<Author, String> lastNameColumn;
	@FXML
	private TableColumn<Author, String> firstNameColumn;
	@FXML
	private TableColumn<Author, String> addNameColumn;
	@FXML
	private TableColumn<Author, String> datesColumn;
	@FXML
	private TextField txtAuthorName;
	@FXML
	private TextField txtAuthorFirstName;
	@FXML
	private TextField txtAuthorAddName;
	@FXML
	private TextField txtAuthorDates;
	
	
	/*  ---------------------------
	 * 
	 *          MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public CuratorAuthorSelectControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainControler
	 */
	public void setMainControl(Main mainController) {
		this.mainController = mainController;
		refreshData();
	}
	
	public void refreshData() {
		authorTable.setItems(mainController.getAuthorData());
	}
	
	/**
	 * réinitialise la zone de création/modification d'auteur
	 */
	private void resetAuthorCreateEdit() {
		txtAuthorName.setText("");
		txtAuthorFirstName.setText("");
		txtAuthorAddName.setText("");
		txtAuthorDates.setText("");
	}
	
	/**
	 * affiche la zone de création/modification d'auteur
	 */
	private void showAuthorEditingPane() {
		pneAuthorCreatEdit.setVisible(true);
	}
	
	/**
	 * masque la zone de création/modification d'auteur
	 */
	private void hideAuthorEditingPane() {
		pneAuthorCreatEdit.setVisible(false);
	}
	
	/**
	 * active les boutons et la table des auteurs
	 */
	private void activateAuthorControls() {
		btnAddAuthor.setDisable(false);
		btnDeleteAuthor.setDisable(false);
		btnUpdateAuthor.setDisable(false);
		authorTable.setDisable(false);
	}
	
	/**
	 * désactive les boutons et la table des auteurs
	 */
	private void deactivateAuthorControls() {
		btnAddAuthor.setDisable(true);
		btnDeleteAuthor.setDisable(true);
		btnUpdateAuthor.setDisable(true);
		authorTable.setDisable(true);
	}
	
	/**
	 * demande au contrôleur principal d'ajouter un auteur
	 */
	public void addAuthor() {
		String lastName = txtAuthorName.getText();
		String firstName = txtAuthorFirstName.getText();
		String additionalName = txtAuthorAddName.getText();
		String authorDates = txtAuthorDates.getText();
		mainController.addAuthor(lastName, firstName, additionalName, authorDates);
	}
	
	/**
	 * demande au contrôleur principal de modifier un auteur
	 */
	public void updateAuthor() {
		Author selectedAuthor = authorTable.getItems().get(selectedAuthorLine);
		int id_author = selectedAuthor.getId_author();
		String lastName = txtAuthorName.getText();
		String firstName = txtAuthorFirstName.getText();
		String additionalName = txtAuthorAddName.getText();
		String authorDates = txtAuthorDates.getText();
		mainController.updateAuthor(id_author, lastName, firstName, additionalName, authorDates);
	}
	
	/**
	 * demande au contrôleur principal de supprimer un auteur
	 */
	public void deleteAuthor() {
		/*
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		int id_room = selectedRoom.getId_room();
		mainControler.deleteRoom(id_room);
		*/
	}
	
	/**
	 * à la demande du contrôleur principal, affiche une notification
	 */
	public void notifyAuthorSaved(String title, String body) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
		};		
		try {
			FXMLLoader loader = new FXMLLoader();		// lien avec la vue
			loader.setLocation(Main.class.getResource("../view/NotificationWindow.fxml"));			
			loader.setController(this);		            // passage de ce contrôleur à la vue
			dialogAuthorSaved = (Pane)loader.load();			
			resetAuthorCreateEdit();					// réinitialisation des champs de la zone d'édition d'auteur
			addingAuthor = false;						// réinitialisation des drapeaux d'ajout/modification
			updatingAuthor = false;
			hideAuthorEditingPane();					// masquage de la fenêtre de modif
			// affichage de la fenêtre pop-up					    
			Scene scene = new Scene(dialogAuthorSaved);
			notifWindow.setTitle(title);
			lblNotification.setText(body);	
			notifWindow.setScene(scene);
			notifWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		refreshData();									// récupération de la table mise à jour
	}
		
	
	/*  ---------------------------
	 * 
	 *    MÉTHODES LIEES À LA VUE
	 * 
	 *  --------------------------- */
	
	/**
	 * à l'ouverture de la fenêtre, initialise le contenu des colonnes
	 */
	@FXML
	private void initialize() {
		lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
		firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
		addNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdditional_name()));
		datesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDates()));
	}
	
	/**
	 * event listener du bouton "Créer" un auteur
	 * @param e
	 */
	@FXML
	private void handleAuthorAddition(ActionEvent e) {
		addingAuthor = true;
		lblAuthorCreatEditTitle.setText("Ajouter un auteur");
		deactivateAuthorControls();
		showAuthorEditingPane();
	}
	
	/**
	 * event listener du bouton "Modifier" l'auteur sélectionné
	 * @param e
	 */
	@FXML
	private void handleAuthorUpdate(ActionEvent event) {
		if (selectedAuthorLine != -1) {
			try {
				Author selectedAuthor = authorTable.getItems().get(selectedAuthorLine);
				updatingAuthor = true;
				lblAuthorCreatEditTitle.setText("Modifier un auteur existant");
				txtAuthorName.setText(selectedAuthor.getLast_name());
				txtAuthorFirstName.setText(selectedAuthor.getFirst_name());
				txtAuthorAddName.setText(selectedAuthor.getAdditional_name());
				txtAuthorDates.setText(selectedAuthor.getDates());
				deactivateAuthorControls();
				showAuthorEditingPane();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		} else {
			// TODO afficher un retour pour dire qu'une ligne doit être sélectionnée ?
		}
	}
	
	/**
	 * event listener du bouton "Supprimer" un auteur
	 * @param e
	 */
	@FXML
	private void handleAuthorDeletion(ActionEvent e) {
		// deleteAuthor();
	}
	
	/**
	 * event listener du bouton "Annuler" la création/modification d'un auteur
	 * @param e
	 */
	@FXML
	private void handleCancelAuthorEdit(ActionEvent e) {
		addingAuthor = false;
		updatingAuthor = false;
		activateAuthorControls();
		resetAuthorCreateEdit();		
		hideAuthorEditingPane();
	}
	
	/**
	 * event listener du bouton "Enregistrer" un auteur
	 * @param e
	 */
	@FXML
	private void handleSaveAuthor(ActionEvent e) {
		if (!txtAuthorName.getText().equals("")) {
			if (addingAuthor) {
				addAuthor();
			}
			else if (updatingAuthor) {
				updateAuthor();
			}	
		}
		else {
			mainController.notifyFail(null);
		}	
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification
	 * @param e
	 */
	@FXML
	private void confirm(ActionEvent e) {
		activateAuthorControls();
		notifWindow.close();
	}
		
	/**
	 * event listener de la liste d'auteurs, permet de récupérer la ligne sélectionnée
	 */
	@FXML
	private void handleAuthorTableAction(MouseEvent event) {
		selectedAuthorLine = authorTable.getSelectionModel().getSelectedIndex();
	}
	
	@FXML
	private void handleSelectAuthor(ActionEvent e) {
		if (selectedAuthorLine != -1) {
			Author selectedAuthor = authorTable.getItems().get(selectedAuthorLine);
			this.mainController.setAuthor(selectedAuthor);
			Stage stage = (Stage)btnSelectAuthor.getScene().getWindow();
			stage.close();
		}
	}
	
	@FXML
	private void handleCancelSelectAuthor(ActionEvent e) {
		Stage stage = (Stage)btnCancelSelectAuthor.getScene().getWindow();
		stage.close();
	}
}
