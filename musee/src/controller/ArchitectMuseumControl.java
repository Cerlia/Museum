package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import museum.floorplan.Museum;

public class ArchitectMuseumControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// musée sur lequel on travaille actuellement
	private Museum currentMuseum;
	private boolean updatingMuseum = false;
	private boolean addingMuseum = false;
	
	@FXML
	private Pane addUpdateMuseumPane;
	@FXML
	private Button btnShowFloorPane;
	@FXML
	private Button btnShowRoomPane;
	@FXML
	private Label lblMuseumName;
	@FXML
	private Label lblFloorNb;
	@FXML
	private Label lblRoomNb;
	@FXML
	private Label lblInfo;
	@FXML
	private TextField txtMuseumName;
	@FXML
	private Button btnMuseumSave;
	@FXML
	private Button btnCancelSave;

	
	
	/*  ---------------------------
	 * 
	 *           MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public ArchitectMuseumControl() {
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
		this.currentMuseum = mainController.getCurrentMuseum();
		if (currentMuseum == null) {
			// affichage des champs permettant l'ajout d'un musée			
			addUpdateMuseumPane.setVisible(true);
			addingMuseum = true;
			lblInfo.setText("Aucun musée trouvé dans la base de données. Pour commencer, donnez un nom à votre musée.");
			btnShowFloorPane.setVisible(false);
			btnShowRoomPane.setVisible(false);
		}
		else {
			// affichage des infos concernant le musée
			this.currentMuseum = mainController.getCurrentMuseum();
			addUpdateMuseumPane.setVisible(false);
			lblMuseumName.setText(currentMuseum.getMuseum_name());
			lblFloorNb.setText(mainController.getFloorData().size()+"");
			lblRoomNb.setText(mainController.getRoomData().size()+"");
			btnShowFloorPane.setVisible(true);
			btnShowRoomPane.setVisible(true);
		}
	}
	
	/**
	 * demande au contrôleur principal de sauvegarder le musée
	 */
	public void addMuseum() {
		String museumName = txtMuseumName.getText();
		mainController.addMuseum(museumName);
	}
	
	/**
	 * demande au contrôleur principal de modifier le musée
	 */
	public void updateMuseum() {
		currentMuseum.setMuseum_name(txtMuseumName.getText());
		mainController.updateMuseum(currentMuseum);
	}
	
	/**
	 * demande au contrôleur principal de supprimer le musée
	 */
	public void deleteMuseum() {
		// TODO en tenant compte du fait qu'il faut supprimer de la BD
		// les étages associés, et les salles avec leurs surfaces, qui elles-mêmes
		// peuvent avoir des portes. Supprimer aussi les displays contenus dans
		// la salle, et donc avant ça replacer les oeuvres en stock !
		// Je suppose qu'il va falloir déléguer : deleteMuseum va faire appel à MuseumDAO,
		// qui dans son delete fera appel à FloorDAO pour supprimer les étages de ce musée,
		// FloorDAO qui lui-même appelera RoomDAO pour supprimer tout ce qui dépend
		// d'une salle, bref gérer les suppressions en cascade
		/*
		Room selectedRoom = roomTable.getItems().get(selectedRoomLine);
		int id_room = selectedRoom.getId_room();
		mainControler.deleteRoom(id_room);
		*/
	}
	
	/**
	 * 
	 */
	public void resetMuseumCreateEdit() {
		// rafraîchissement des infos à afficher
		refreshData();
		// désactivation des modes création/édition de musée
		updatingMuseum = false;
		addingMuseum = false;		
	}
	
	
	/*  ---------------------------
	 * 
	 *    MÉTHODES LIEES À LA VUE
	 * 
	 *  --------------------------- */	
	
	/**
	 * méthode d'initialisation indispensable, se lance à l'ouverture de la fenêtre
	 */
	@FXML
	private void initialize() {
	}
		
	/**
	 * event listener du bouton "Modifier" le musée
	 * @param e
	 */
	@FXML
	private void showMuseumUpdateForm(ActionEvent event) {
		addingMuseum = false;
		updatingMuseum = true;
		txtMuseumName.setText(currentMuseum.getMuseum_name());
		addUpdateMuseumPane.setVisible(true);
		lblInfo.setText("Entrez le nouveau nom de votre musée.");
		btnCancelSave.setVisible(true);
		btnMuseumSave.setText("Enregistrer");
	}
	
	/**
	 * event listener du bouton "Créer le musée"
	 * @param e
	 */
	@FXML
	private void handleMuseumSave(ActionEvent e) {
		if (addingMuseum) {
			addMuseum();
		} else if (updatingMuseum) {
			updateMuseum();
		}
	}
	
	/**
	 * event listener du bouton "Annuler" la modification du musée
	 * @param e
	 */
	@FXML
	private void handleCancelSave(ActionEvent e) {
		addUpdateMuseumPane.setVisible(false);
	}
		
	/**
	 * event listener du bouton pour afficher les étages
	 */
	@FXML
	private void showArchitectFloorPane(ActionEvent event) {
		this.mainController.showArchitectFloorPane();
	}	
	
	/**
	 * event listener du bouton pour afficher les salles
	 */
	@FXML
	private void showArchitectRoomPane(ActionEvent event) {
		this.mainController.showArchitectRoomPane();
	}
}
