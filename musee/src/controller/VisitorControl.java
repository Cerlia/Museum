package controller;

import java.io.IOException;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import museum.art.Art;
import museum.floorplan.Floor;
import museum.floorplan.Museum;
import museum.floorplan.Room;
import utils.ImageConversion;

public class VisitorControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des salles, par défaut aucune
	private int selectedRoomLine = -1;
	private int selectedArtLine = -1;
	private int floorMax;
	private Floor currentFloor = null;
	private int currentFloorNb;
	private ObservableList<Floor> floors = null;
	private Museum museum;
	
	
	@FXML
	private Pane pneArtInfo;
	@FXML
	private Label lblMuseum;
	@FXML
	private Label lblArtListTitle;
	@FXML
	private Label lblArtTitle;
	@FXML
	private Label lblAuthor;
	@FXML
	private Label lblArtDates;
	@FXML
	private Label lblMaterials;
	@FXML
	private Label lblArtType;
	@FXML
	private Label lblArtDim;
	@FXML
	private ImageView imgArt;
	@FXML
	private ListView<Room> lstRooms;
	@FXML
	private TableView<Art> tblArts;
	@FXML
	private TableColumn<Art, String> colTitle;
	@FXML
	private TableColumn<Art, String> colAuthor;
	
	
	/*  ---------------------------
	 * 
	 *           MÉTHODES
	 * 
	 *  --------------------------- */
	
	/**
	 * constructeur de la classe
	 */
	public VisitorControl() {
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
		if (mainController.getCurrentMuseum() != null && mainController.getFloorData() != null) {
			this.museum = mainController.getCurrentMuseum();
			this.floors = mainController.getFloorData();
			this.currentFloor = floors.get(0);
			lblMuseum.setText(this.museum.getMuseum_name() + " - " + this.currentFloor.getFloor_name());
			this.currentFloorNb = 0;
			floorMax = floors.size()-1;
			showFloorInfo();
		}
		else {
			// TODO 
			// Afficher "pas d'étage dans le musée" ?
		}			
	}
	
	public void showFloorInfo() {
		this.currentFloor = floors.get(currentFloorNb);
		lblMuseum.setText(this.museum.getMuseum_name() + " - " + this.currentFloor.getFloor_name());
		lstRooms.setItems(mainController.getRoomData(this.currentFloor));
	}
	
	public void showArtList() {
		Room room = lstRooms.getItems().get(selectedRoomLine);
		tblArts.setItems(mainController.getAllArtsOfRoom(room));
	}
	
	public void showArtInfo() {
		if (selectedArtLine != -1) {
			Art lightArt = tblArts.getItems().get(selectedArtLine);
			int id_art = lightArt.getId_art();
			Art selectedArt = mainController.getFullArtData(id_art);
			lblArtTitle.setText(selectedArt.getArt_title());
			lblArtDates.setText(selectedArt.getCreation_date());
			lblMaterials.setText(selectedArt.getMaterials());
			lblArtDim.setText(selectedArt.getDim_x()+" cm");
			if (selectedArt.getDim_y() != 0) {
				lblArtDim.setText(lblArtDim.getText() + " x " + selectedArt.getDim_y()+" cm");
			}
			if (selectedArt.getDim_z() != 0) {
				lblArtDim.setText(lblArtDim.getText() + " x " + selectedArt.getDim_z()+" cm");
			}
			String fullName = selectedArt.getAuthor().getFullName();
			lblAuthor.setText(fullName);
			lblArtType.setText(selectedArt.getArt_type().getName());			
			// affiche l'illustration de cette oeuvre si elle existe
			if (selectedArt.getImage() != null) {
				try {
					Image image = ImageConversion.byteArrayToImage(selectedArt.getImage());
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
		colTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_title()));
		colAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor().getFullName()));
	}
	

	/**
	 * event listener de la liste de salles, permet de récupérer la ligne sélectionnée (clic)
	 */
	@FXML
	private void handleRoomTableAction(MouseEvent event) {
		selectedRoomLine = lstRooms.getSelectionModel().getSelectedIndex();
		showArtList();
	}
	
	/**
	 * event listener de la liste de salles, permet de récupérer la ligne sélectionnée (bouton)
	 */
	@FXML
	private void handleRoomTableKeyPressed(KeyEvent event) {
		selectedRoomLine = lstRooms.getSelectionModel().getSelectedIndex();
		showArtList();	
	}
	
	/**
	 * event listener de la liste d'œuvres, permet de récupérer la ligne sélectionnée (clic)
	 */
	@FXML
	private void handleArtTableAction(MouseEvent event) {
		selectedArtLine = tblArts.getSelectionModel().getSelectedIndex();
		showArtInfo();	
	}
	
	/**
	 * event listener de la liste d'œuvres, permet de récupérer la ligne sélectionnée (bouton)
	 */
	@FXML
	private void handleArtTableKeyPressed(KeyEvent event) {
		selectedArtLine = tblArts.getSelectionModel().getSelectedIndex();
		showArtInfo();	
	}
	
	/**
	 * event listener de l'image "arrow up"
	 */
	@FXML
	private void handleImgFloorUpClick(MouseEvent event) {
		if (currentFloorNb < floorMax) {
			currentFloorNb+=1;
			showFloorInfo();			
		}
	}
	
	/**
	 * event listener de l'image "arrow down"
	 */
	@FXML
	private void handleImgFloorDownClick(MouseEvent event) {
		if (currentFloorNb > 0) {
			currentFloorNb-=1;
			showFloorInfo();
		}
	}
}
