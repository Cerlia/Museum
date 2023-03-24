package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginControl {
	
	private Main mainControler;
	
	@FXML
	private TextField txtLogin;
	@FXML
	private TextField txtPassword;
	
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
		 * Par défaut, j'utilise le rôle conservateur
		*/
		if (txtLogin.getText().equals("a")) {
			mainControler.showArchitectMuseumPane();
		}
		else if (txtLogin.getText().equals("c")) {
			mainControler.showCuratorArtMovementPane();
		}
		else {
			mainControler.showCuratorArtMovementPane();
		}			
	}
}
