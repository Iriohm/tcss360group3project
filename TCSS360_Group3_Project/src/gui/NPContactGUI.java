package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Calendar;
import model.NPContact;

public class NPContactGUI implements Initializable {
	
	/** The "Submit auction request" button. */
	@FXML
	private Button mySubmitAuctionRequestBtn;
	
	/** The "Submit auction request" button. */
	@FXML
	private Button myViewAuctionsBtn;
	
	/** The "Item inventory" button. */
	@FXML
	private Button myItemInvBtn;
	
	/** The "Log out" button. Pressing this should return the user to the login screen. */
	@FXML
	private Button myLogoutBtn;
	
	/** The GUI label that will show "Logged in as: " and the user's username. */
	@FXML
	private Label myUsernameLabel;
	
	/** The GUI image box that will hold the logo. */
	@FXML
	private ImageView myLogoImageView;
	
	/** The Calendar where the auctions are stored. */
	private Calendar myCalendar;
	
	/** The current GUI window. */
	private Stage myStage;
	
	/** The current user. */
	private NPContact myNPContact;
	
	/**
	 * The constructor that is used by JavaFX.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitAuctionRequestBtn != null : "fx:id=\"mySubmitAuctionRequestBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myViewAuctionsBtn != null : "fx:id=\"myViewAuctionsBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myItemInvBtn != null : "fx:id=\"myItemInvBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myLogoutBtn != null : "fx:id=\"myLogoutBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myUsernameLabel != null : "fx:id=\"myUsernameLabel\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myLogoImageView != null : "fx:id=\"myLogoImageView\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";

		Image logo = new Image("file:logo2_v3.png");
		myLogoImageView.setImage(logo);
	}
	
	/**
	 * Initializes all of the fields, works like a constructor.
	 * 
	 * @param theStage The current GUI window.
	 * @param theCalendar The Calendar where the auctions are stored.
	 * @param theNPContact The current user.
	 */
	public void sendData(Stage theStage, NPContact theNPContact, Calendar theCalendar) {
		myStage = theStage;
		myNPContact = theNPContact;
		myCalendar = theCalendar;
		
		if (myNPContact.hasAuctionUpcomingOrLastYear()) {
			mySubmitAuctionRequestBtn.setDisable(true);
		} else {
			myItemInvBtn.setDisable(true);
		}
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		initalizeBtns();
	}
	
	/**
	 * Initializes all of the GUI buttons.
	 */
	private void initalizeBtns() {
		setupSubmitAuctionRequestBtn();
		
		setupViewAuctionsBtn();
		
		setupItemInvBtn();
		
		myLogoutBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Authenticate.setupAuthenticate(myStage);
			}
		});

	}
	
	private void setupSubmitAuctionRequestBtn() {
		mySubmitAuctionRequestBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (myCalendar.validateAuctionRequestMaxAuctions()) {
					final Stage auctionRequestStage = new Stage();
					auctionRequestStage.setTitle("Auction Central");
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactAuctionRequestForm.fxml"));
						Parent root = (Parent)fxmlLoader.load();
						NPContactAuctionRequestFormGUI ctrlAuctionRequestFormGUI = fxmlLoader.<NPContactAuctionRequestFormGUI>getController();
	
						ctrlAuctionRequestFormGUI.initVariables(myStage, auctionRequestStage, myCalendar, myNPContact, mySubmitAuctionRequestBtn , myItemInvBtn);
	
						Scene scene = new Scene(root);
						auctionRequestStage.setScene(scene);
						auctionRequestStage.setResizable(false);
						myStage.hide();
						auctionRequestStage.show();
					} catch(Exception anException) {
						anException.printStackTrace();
					}
				} else {
					displayErrorPopup();
				}
			}
		});
	}
	
	private void displayErrorPopup() {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Auction Central");
		errorAlert.setHeaderText("Maximum Auctions Reached");
		errorAlert.setContentText("Sorry, but we have reached our limit of " + myCalendar.getMaxAuctionsLimit() + " upcoming auctions in a month-long period. Please try again at a later date.");
		
		errorAlert.showAndWait();
	}
	
	private void setupViewAuctionsBtn() {
		myViewAuctionsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage viewAuctionsStage = new Stage();
				viewAuctionsStage.setTitle("Auction Central");
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactViewAuctionsGUI.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactViewAuctionsGUI ctrlViewAuctionsGUI = fxmlLoader.<NPContactViewAuctionsGUI>getController();

					ctrlViewAuctionsGUI.initVariables(myStage, viewAuctionsStage, myCalendar, myNPContact, myItemInvBtn, mySubmitAuctionRequestBtn);

					Scene scene = new Scene(root);
					viewAuctionsStage.setScene(scene);
					viewAuctionsStage.setResizable(false);
					myStage.hide();
					viewAuctionsStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
			}
		});
	}
	
	private void setupItemInvBtn() {
		myItemInvBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage itemInventoryStage = new Stage();
				itemInventoryStage.setTitle("Auction Central");
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactItemInventoryGUI.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactItemInventoryGUI ctrlItemInventoryGUI = fxmlLoader.<NPContactItemInventoryGUI>getController();

					ctrlItemInventoryGUI.initVariables(myStage, itemInventoryStage, myCalendar, myNPContact);

					Scene scene = new Scene(root);
					itemInventoryStage.setScene(scene);
					itemInventoryStage.setResizable(false);
					myStage.hide();
					itemInventoryStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
			}
		});
	}
}
