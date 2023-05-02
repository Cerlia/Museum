package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import museum.User;

public class LoginControl {
	
	private Main mainController;
	
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
		this.mainController = mainControler;
	}
	
	/**
	 * vide les champs Login et Mot de passe
	 */
	public void resetControls() {
		txtLogin.setText("");
		txtPassword.setText("");
	}
	
	/**
	 * event listener du bouton "Connexion"
	 * @param event
	 */
	@FXML
	private void handleConnectionClick(ActionEvent event) {
		if (!txtLogin.getText().equals("") && !txtPassword.getText().equals("")) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA-512");
				String password = txtPassword.getText();
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				List<User> users = mainController.getUserData(txtLogin.getText(), hash);
				if (users.size() > 0) {
					User user = users.get(0);
					if (user.getRole().getName().equals("architecte")) {
						mainController.showArchitectMuseumPane();
					} else if (user.getRole().getName().equals("conservateur")) {
						mainController.showCuratorArtExhibitPane();
					}
				} else if (txtLogin.getText().equals("a") && txtPassword.getText().equals("a")) {
					mainController.showArchitectMuseumPane();
				} else if (txtLogin.getText().equals("c") && txtPassword.getText().equals("c")) {
					mainController.showCuratorArtExhibitPane();
				} else {
					// TODO remplacer par un message moins intrusif qu'une pop-up
					mainController.notifyFail("Identifiant et/ou mot de passe incorrect.");
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		} else {
			mainController.notifyFail("Tous les champs ne sont pas remplis");
		}


		/* Par défaut, j'utilise le rôle conservateur
		
		if (txtLogin.getText().equals("a")) {
			mainController.showArchitectMuseumPane();
		}
		else if (txtLogin.getText().equals("c")) {
			mainController.showCuratorArtExhibitPane();
		}
		else {
			mainControler.showCuratorArtExhibitPane();
		}		
		*/	
	}
}
