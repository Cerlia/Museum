package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.Main;
import dao.art.ArtStatusDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import museum.art.Art;
import museum.art.ArtStatus;
import museum.art.ArtType;
import museum.art.Author;
import utils.ImageConversion;
import utils.InputCheck;

public class CuratorArtDataControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des œuvres, par défaut aucune
	private int selectedArtLine = -1;
	private boolean updatingArt = false;
	private boolean addingArt = false;
	private Stage stgImageSelect = new Stage();
	// fenêtre de sélection de fichier
	final FileChooser fileChooser = new FileChooser();
	private File file = null;
	// taille maximale acceptée pour une illustration d'œuvre
	private final static int IMGMAXSIZE = 250000;
	
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
	private ComboBox<String> cbbOwner;
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
	private Label lblImgPath;
	@FXML
	private Label lblOwner;
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
	}
	
	public void refreshData() {
		artTable.setItems(mainController.getArtData());
		cbbArtType.setItems(mainController.getArtTypeData());
		cbbAuthor.setItems(mainController.getAuthorData());
		cbbOwner.setItems(FXCollections.observableArrayList(
			    new String("Oui"),
			    new String("Non")));
	}
		
	/**
	 * réinitialise la zone de création/modification d'œuvre
	 */
	public void resetArtCreateEdit() {
		// rafraîchissement des données
		this.refreshData();
		// réinitialisation des drapeaux d'ajout/modification
		addingArt = false;
		updatingArt = false;
		// masque la fenêtre de modification
		hideArtEditingPane();
		txtArtTitle.setText("");
		txtArtCode.setText("");
		txtArtDates.setText("");
		txtMaterials.setText("");
		txtDimX.setText("");
		txtDimY.setText("");
		txtDimZ.setText("");
		lblImgPath.setText("aucune image sélectionnée");
		// réinitialisation du fichier image
		this.file = null;
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
			lblAuthor.setText(selectedArt.getAuthor().getFullName());
			lblArtType.setText(selectedArt.getArt_type().getName());
			lblOwner.setText(selectedArt.isOwner() ? "Oui" : "Non");
			// affiche l'illustration de cette oeuvre si elle existe
			if (selectedArt.getImage() != null) {
				try {
					Image image = ImageConversion.byteArrayToImage(selectedArt.getImage());
					imgArt.setImage(image);		
					lblImgPath.setText("image présente dans la base");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				imgArt.setImage(null);
				lblImgPath.setText("aucune image dans la base");
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
			String title = txtArtTitle.getText();
			String code = txtArtCode.getText();
			String date = txtArtDates.getText();
			String materials = txtMaterials.getText();			
			int dimX = Integer.parseInt(txtDimX.getText());
			int dimY = Integer.parseInt(txtDimY.getText());
			int dimZ = Integer.parseInt(txtDimZ.getText());
			byte[] image = null;
			if (this.file != null) {
				image = ImageConversion.imageToByteArray(this.file);
			}
			Author author = cbbAuthor.getValue();
			ArtType type = cbbArtType.getValue();
			boolean artOwner = (cbbOwner.getValue() == "Oui" ? true : false);
			// par défaut, une œuvre est "Possédée" (id_art_status = 1)
			// et elle n'a pas de présentoir (display = null)
			// TODO l'oeuvre pourrait avoir le statut "Prêté" ou "Emprunté"
			ArtStatus artStatus = ArtStatusDAO.getInstance().read(1);
			Art art = new Art(code, title, date, materials, dimX, dimY, dimZ, image,
					author, artStatus, type, null, artOwner);
			mainController.addArt(art);
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de l'œuvre");
			e.printStackTrace();
		}
	}
	
	private void updateArt() {
		try {
			Art selectedArt = artTable.getItems().get(selectedArtLine);
			selectedArt.setArt_title(txtArtTitle.getText());
			selectedArt.setArt_code(txtArtCode.getText());
			selectedArt.setCreation_date(txtArtDates.getText());
			selectedArt.setMaterials(txtMaterials.getText());
			selectedArt.setDim_x(Integer.parseInt(txtDimX.getText()));
			selectedArt.setDim_y(Integer.parseInt(txtDimY.getText()));
			selectedArt.setDim_z(Integer.parseInt(txtDimZ.getText()));
			selectedArt.setAuthor(cbbAuthor.getValue());
			selectedArt.setArt_status(selectedArt.getArt_status());
			selectedArt.setArt_type(cbbArtType.getValue());
			if (this.file != null) {
				selectedArt.setImage(ImageConversion.imageToByteArray(this.file));
			}
			mainController.updateArt(selectedArt, "artData");			
		} catch (Exception e) {
			mainController.notifyFail("Échec lors de l'enregistrement de l'œuvre");
			e.printStackTrace();
		}
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
	 * initialisation de la vue JavaFX
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
	 * event listener de la liste d'œuvres, permet de récupérer la ligne sélectionnée (clic)
	 */
	@FXML
	private void handleArtTableAction(MouseEvent event) {
		selectedArtLine = artTable.getSelectionModel().getSelectedIndex();
		showArtInfo();	
	}
	
	/**
	 * event listener de la liste d'œuvres, permet de récupérer la ligne sélectionnée (bouton)
	 */
	@FXML
	private void handleArtTableKeyPressed(KeyEvent event) {
		selectedArtLine = artTable.getSelectionModel().getSelectedIndex();
		showArtInfo();	
	}
	
	/**
	 * event listener du bouton pour ajouter un artiste : ouvre une nouvelle fenêtre
	 */
	@FXML
	private void handleAuthorSelect(ActionEvent event) {
		mainController.showCuratorAuthorSelectionStage();
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
        // si un fichier a été sélectionné
        if (this.file != null) {
            // vérification du contenu et de la taille du fichier
            // TODO
            if (InputCheck.CheckType(this.file) && InputCheck.CheckFileSize(this.file, IMGMAXSIZE)) {
                // affichage du nom du fichier dans le formulaire de modification de l'œuvre        	
                String filename = file.getName();
                lblImgPath.setText(filename);
            } else {
            	mainController.notifyFail("Ce fichier ne remplit pas les critères demandés.");
            }
        }
	}		
}
