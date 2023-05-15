package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import museum.floorplan.Floor;
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
			addingMuseum = true;
			lblMuseumName.setText("");
			lblFloorNb.setText("");
			lblRoomNb.setText("");
			// affichage des champs permettant l'ajout d'un musée			
			addUpdateMuseumPane.setVisible(true);
			lblInfo.setText("Aucun musée trouvé dans la base de données. Pour commencer, donnez un nom à votre musée.");
			btnShowFloorPane.setVisible(false);
			btnShowRoomPane.setVisible(false);
			btnCancelSave.setVisible(false);
		}
		else {
			addingMuseum = false;
			// affichage des infos concernant le musée
			this.currentMuseum = mainController.getCurrentMuseum();
			addUpdateMuseumPane.setVisible(false);
			lblMuseumName.setText(currentMuseum.getMuseum_name());
			lblFloorNb.setText(mainController.getFloorData().size()+"");
			lblRoomNb.setText(mainController.getRoomData().size()+"");
			btnShowFloorPane.setVisible(true);
			btnShowRoomPane.setVisible(true);
		}
		updatingMuseum = false;
	}
	
	/**
	 * demande au contrôleur principal de sauvegarder le musée
	 */
	public void addMuseum() {
		String museumName = txtMuseumName.getText();
		Museum museum = new Museum(museumName);
		mainController.addMuseum(museum);
	}
	
	/**
	 * demande au contrôleur principal de modifier le musée
	 */
	public void updateMuseum() {
		currentMuseum.setMuseum_name(txtMuseumName.getText());
		mainController.updateMuseum(currentMuseum);
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
	 * initialisation de la vue JavaFX
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
	 * event listener du bouton "Supprimer" le musée
	 * @param e
	 */
	@FXML
	private void handleMuseumDelete(ActionEvent event) {
		int nbArts = 0;
		for (Floor floor : mainController.getFloorData()) {
				nbArts += mainController.getAllArtsOfFloor(floor).size();
			}
		if (nbArts == 0) {
			mainController.notifyConfirmDelete("Suppression du musée", "Voulez-vous vraiment supprimer "
					+ "le musée ? Les étages et salles seront définitivement supprimés.", currentMuseum);
		} else {
			mainController.notifyInfo("Impossible de supprimer un musée ayant des œuvres exposées");
		}		
	};
	
	/**
	 * event listener du bouton "Créer le musée"
	 * @param e
	 */
	@FXML
	private void handleMuseumSave(ActionEvent e) {
		if (!txtMuseumName.getText().equals("")) {
			if (addingMuseum) {
				addMuseum();
			} else if (updatingMuseum) {
				updateMuseum();
			}
		}
		else {
			mainController.notifyInfo("Le nom du musée ne peut pas être vide");
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
