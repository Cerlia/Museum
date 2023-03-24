package controller;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import museum.art.Art;
import museum.art.ArtStatus;
import museum.art.ArtType;
import museum.art.Author;
import museum.display.Display;

public class CuratorArtDataControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des œuvres, par défaut aucune
	private int selectedArtLine = 0;
	private Stage notifWindow = new Stage();
	private Pane dialogArtSaved;
	private boolean updatingArt = false;
	private boolean addingArt = false;
	private Stage stgAuthorAddition = new Stage();
	private CuratorAuthorSelectControl authorSelectCtrl = null;
	private Stage stgImageSelect = new Stage();
	// récupération des infos du système utilisé
	// TODO ligne suivante sert à quoi ?
	private Desktop desktop = Desktop.getDesktop();
	final FileChooser fileChooser = new FileChooser();
	private File file = null;
	
	@FXML
	private Button createAction;
	@FXML
	private Button cancelCreatEdit;
	@FXML
	private Button confirmArtSaved;
	@FXML
	private Button editArt;
	@FXML
	private Button deleteArt;
	@FXML
	private Button saveArt;
	@FXML
	private Button btnAuthorAddition;
	@FXML
	private Button btnAuthorSelect;
	@FXML
	private Button btnImageSelect;
	@FXML
	private ComboBox<ArtType> cbbArtType;
	@FXML
	private ComboBox<Author> cbbAuthor;
	@FXML
	private ImageView imgArt;
	@FXML
	private Label lblArtCreatEditTitle;
	@FXML
	private Label lblArtTitle;
	@FXML
	private Label lblArtCode;
	@FXML
	private Label lblArtDates;
	@FXML
	private Label lblMaterials;
	@FXML
	private Label lblArtX;
	@FXML
	private Label lblArtY;
	@FXML
	private Label lblArtZ;
	@FXML
	private Label lblAuthor;
	@FXML
	private Label lblArtType;
	@FXML
	private Label lblArtStatus;
	@FXML
	private Label lblNotification;
	@FXML
	private Label lblImgPath;
	@FXML
	private Pane pneAuthorSelect;
	@FXML
	private AnchorPane pneArtInfo;
	@FXML
	private AnchorPane pneArtCreatEdit;
	@FXML
	private TableView<Art> artTable;
	@FXML
	private TableColumn<Art, String> codeColumn;
	@FXML
	private TableColumn<Art, String> nameColumn;
	@FXML
	private TableColumn<Art, String> authorColumn;
	@FXML
	private TableColumn<Art, String> statusColumn;
	@FXML
	private TextField txtArtTitle;
	@FXML
	private TextField txtArtCode;
	@FXML
	private TextField txtArtDates;
	@FXML
	private TextField txtMaterials;
	@FXML
	private TextField txtDimX;
	@FXML
	private TextField txtDimY;
	@FXML
	private TextField txtDimZ;
	@FXML
	private TextField txtArtAuthor;
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
	public CuratorArtDataControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainController
	 */
	public void setMainControl(Main mainController) {
		this.mainController = mainController;
		refreshData();
	}
	
	public void refreshData() {
		artTable.setItems(mainController.getArtData());
		cbbArtType.setItems(mainController.getArtTypeData());
		cbbAuthor.setItems(mainController.getAuthorData());
	}
		
	/**
	 * réinitialise la zone de création/modification d'œuvre
	 */
	private void resetArtCreateEdit() {
		txtArtTitle.setText("");
		txtArtCode.setText("");
		txtArtDates.setText("");
		txtMaterials.setText("");
		txtDimX.setText("");
		txtDimY.setText("");
		txtDimZ.setText("");
		lblImgPath.setText("");
	}
	
	/**
	 * convertit un tableau d'octets en image
	 * @param imgData
	 * @return
	 * @throws IOException
	 */
	private Image byteArrayToImage(byte[] imgData) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
		Image artImage = new Image(bais);
		return artImage;	         	
	}
	
	/**
	 * convertit une image en tableau d'octets
	 * @param imgFile
	 * @return
	 * @throws IOException 
	 */
	private byte[] imageToByteArray(File imgFile) throws IOException {
		BufferedImage bImage = ImageIO.read(imgFile);
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(bImage, "jpg", baos);
	    byte[] imgData = baos.toByteArray();
	    baos.flush();
	    return imgData;    
	}
	
	/**
	 * affiche la zone des informations détaillées de l'œuvre
	 * @throws FileNotFoundException 
	 */
	private void showArtInfo() {
		if (selectedArtLine != -1) {
			Art lightArt = artTable.getItems().get(selectedArtLine);
			int id_art = lightArt.getId_art();
			Art selectedArt = mainController.getFullArtData(id_art);
			lblArtTitle.setText(selectedArt.getArt_title());
			lblArtCode.setText(selectedArt.getArt_code());
			lblArtDates.setText(selectedArt.getCreation_date());
			lblMaterials.setText(selectedArt.getMaterials());
			lblArtX.setText(selectedArt.getDim_x()+"");
			lblArtY.setText(selectedArt.getDim_y()+"");
			lblArtZ.setText(selectedArt.getDim_z()+"");
			String fullName = selectedArt.getAuthor().getLast_name() + " " + selectedArt.getAuthor().getFirst_name();
			if (!selectedArt.getAuthor().getAdditional_name().equals("")) {
				fullName += ", dit " + selectedArt.getAuthor().getAdditional_name();
			}
			lblAuthor.setText(fullName);
			lblArtType.setText(selectedArt.getArt_type().getName());
			lblArtStatus.setText(selectedArt.getArt_status().getName());			
			// affiche l'illustration de cette oeuvre si elle existe
			if (selectedArt.getImage() != null) {
				try {
					Image image = byteArrayToImage(selectedArt.getImage());
					imgArt.setImage(image);					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				imgArt.setImage(null);
			}
			pneArtInfo.setVisible(true);
		}		
	}
	
	private void showArtEditingPane() {
		createAction.setDisable(true);
		pneArtInfo.setVisible(false);
		pneArtCreatEdit.setVisible(true);
		artTable.setDisable(true);
	}
	
	private void hideArtEditingPane() {
		createAction.setDisable(false);
		pneArtInfo.setVisible(false);
		pneArtCreatEdit.setVisible(false);
		artTable.setDisable(false);
	}
	
	/**
	 * demande au contrôleur principal d'ajouter une œuvre
	 */
	private void addArt() {
		try {
			String artTitle = txtArtTitle.getText();
			String artCode = txtArtCode.getText();
			String artDates = txtArtDates.getText();
			String artMaterials = txtMaterials.getText();			
			int artDimX = Integer.parseInt(txtDimX.getText());
			int artDimY = Integer.parseInt(txtDimY.getText());
			int artDimZ = Integer.parseInt(txtDimZ.getText());
			byte[] artImage = null;
			if (this.file != null) {
				artImage = imageToByteArray(this.file);
			}
			Author author = cbbAuthor.getValue();
			ArtType artType = cbbArtType.getValue();
			mainController.addArt(artCode, artTitle, artDates, artMaterials, artDimX, artDimY, artDimZ,
					artImage, author, null, artType, null);
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de l'œuvre");
		}
	}
	
	private void updateArt() {
		try {
			Art selectedArt = artTable.getItems().get(selectedArtLine);
			int id_art = selectedArt.getId_art();
			String artTitle = txtArtTitle.getText();
			String artCode = txtArtCode.getText();
			String artDates = txtArtDates.getText();
			String artMaterials = txtMaterials.getText();			
			int artDimX = Integer.parseInt(txtDimX.getText());
			int artDimY = Integer.parseInt(txtDimY.getText());
			int artDimZ = Integer.parseInt(txtDimZ.getText());
			Author author = cbbAuthor.getValue();
			ArtStatus artStatus = selectedArt.getArt_status();
			ArtType artType = cbbArtType.getValue();
			Display display = selectedArt.getDisplay();
			byte[] artImage = null;
			if (this.file != null) {
				artImage = imageToByteArray(this.file);
			} else {
				artImage = selectedArt.getImage();
			}
			mainController.updateArt(id_art, artCode, artTitle, artDates, artMaterials, artDimX,
				artDimY, artDimZ, artImage, author, artStatus, artType, display);			
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de l'œuvre");
		}
	}
	
	/**
	 * à la demande du contrôleur principal, affiche une notification
	 */
	public void notifyArtSaved(String title, String body) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/NotificationWindow.fxml"));
			// passage de ce contrôleur à la vue
			loader.setController(this);
			dialogArtSaved = (Pane)loader.load();
			// réinitialisation et désactivation de la zone d'édition de salle
			resetArtCreateEdit();
			// réinitialisation des drapeaux d'ajout/modification
			addingArt = false;
			updatingArt = false;
			// masque la fenêtre de modification
			hideArtEditingPane();
			// affichage de la fenêtre pop-up
			notifWindow.setTitle(title);
			lblNotification.setText(body);
			Scene scene = new Scene(dialogArtSaved);
			notifWindow.setScene(scene);
			notifWindow.show();
			// réinitialisation du fichier image
			this.file = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// récupération de la table mise à jour
		refreshData();
	}
	
	/**
	 * à la demande du contrôleur principal, affiche une notification
	 */
	public void notifyAuthorSaved(String title, String body) {
		authorSelectCtrl.notifyAuthorSaved(title, body);
	}
	
	public void setAuthor(Author author) {
		cbbAuthor.setItems(mainController.getAuthorData());
		cbbAuthor.setValue(author);
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
		codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_code()));
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_title()));
		authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor().getLast_name()));
		statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_status().getName()));
	}
	
	/**
	 * event listener du bouton "Modifier" une œuvre
	 * @param e
	 */
	@FXML
	private void handleArtUpdate(ActionEvent event) {
		// la modification est possible seulement si une salle est sélectionnée
		if (selectedArtLine != -1) {
			try {
				Art selectedArt = artTable.getItems().get(selectedArtLine);
				updatingArt = true;
				lblArtCreatEditTitle.setText("Modification des informations de l'œuvre");
				txtArtTitle.setText(selectedArt.getArt_title());
				txtArtCode.setText(selectedArt.getArt_code());
				txtArtDates.setText(selectedArt.getCreation_date());
				txtMaterials.setText(selectedArt.getMaterials());				
				txtDimX.setText(selectedArt.getDim_x()+"");
				txtDimY.setText(selectedArt.getDim_y()+"");
				txtDimZ.setText(selectedArt.getDim_z()+"");
				cbbAuthor.setValue(selectedArt.getAuthor());
				cbbArtType.setValue(selectedArt.getArt_type());
				showArtEditingPane();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * event listener du bouton "Ajouter" une œuvre
	 * @param e
	 */
	@FXML
	private void handleArtAddition(ActionEvent e) {
		addingArt = true;
		lblArtCreatEditTitle.setText("Saisie d'une nouvelle œuvre");
		showArtEditingPane();
	}
	
	/**
	 * event listener du bouton "Annuler" l'ajout ou la modification d'une œuvre
	 * @param e
	 */
	@FXML
	private void handleCancelCreatEdit(ActionEvent e) {
		addingArt = false;
		updatingArt = false;
		resetArtCreateEdit();
		hideArtEditingPane();
	}
	
	/**
	 * event listener du bouton "Sauvegarder" l'ajout ou la modification d'une œuvre
	 * @param e
	 */
	@FXML
	private void handleSaveArt(ActionEvent e) {
		// on vérifie d'abord que tous les champs sont remplis
		if (txtArtTitle.getText() != "" && txtArtCode.getText() != "" && txtArtDates.getText() != "" &&
			txtMaterials.getText() != "" && txtDimX.getText() != "" && txtDimY.getText() != "" &&
			txtDimZ.getText() != "" && cbbAuthor.getValue() != null && cbbArtType.getValue() != null) {
			if (addingArt) {
				addArt();
			}
			else if (updatingArt) {
				updateArt();
			}
		} else {
			mainController.notifyFail(null);
		}
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification
	 * @param e
	 */
	@FXML
	private void confirm(ActionEvent e) {
		notifWindow.close();
	}
	
	/**
	 * event listener de la liste d'œuvres, permet de récupérer la ligne sélectionnée
	 */
	@FXML
	private void handleArtTableAction(MouseEvent event) {
		selectedArtLine = artTable.getSelectionModel().getSelectedIndex();
		showArtInfo();		
	}
	
	/**
	 * event listener du bouton pour ajouter un artiste : ouvre une nouvelle fenêtre
	 */
	@FXML
	private void handleAuthorSelect(ActionEvent event) {
		if (stgAuthorAddition.getModality() != Modality.APPLICATION_MODAL) {
			stgAuthorAddition.initModality(Modality.APPLICATION_MODAL);
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/CuratorAuthorSelect.fxml"));
			pneAuthorSelect = (Pane)loader.load();
			// récupération du contrôleur de la vue
			this.authorSelectCtrl = loader.getController();
			// passage du contrôleur principal au sous-contrôleur
			this.authorSelectCtrl.setMainControl(this.mainController);
			Scene scene = new Scene(pneAuthorSelect);
			stgAuthorAddition.setScene(scene);
			stgAuthorAddition.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * event listener du bouton pour ajouter le fichier contenant l'image de l'œuvre
	 */
	@FXML
	private void handleImageSelect(ActionEvent event) {
		// sélection du fichier JPG
		stgImageSelect.setTitle("Choisir un fichier");
		this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        this.file = fileChooser.showOpenDialog(stgImageSelect);
        // affichage du nom du fichier dans le formulaire de modification de l'œuvre
        String filename = file.getName();
        lblImgPath.setText(filename);
	}		
}
