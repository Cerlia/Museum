package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginControl {
	
	private Main mainControler;
	
	public LoginControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainControler
	 */
	public void setMainControl(Main mainControler) {
		this.mainControler = mainControler;
	}
	
	/**
	 * event listener du bouton "Connexion"
	 * @param event
	 */
	@FXML
	private void handleConnectionClick(ActionEvent event) {
		/* TODO
		 * Tester si login et mot de passe sont ceux d'un utilisateur
		 * Vérifier le rôle associé pour savoir quel contrôleur appeler ensuite
		 * Par défaut, on va utiliser le rôle architecte
		*/
		mainControler.showArchitectMuseumPane();
	}
}
