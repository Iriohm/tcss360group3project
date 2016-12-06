package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Auction;
import model.Calendar;
import model.NPContact;

/**
* This class is used to control the View Auctions GUI for NPContact users.
*
* @author Vlad Kaganyuk
* @version 5 Dec 2016
*
*/
public class NPContactViewAuctionsGUI implements Initializable {
	
	private static final int NEED_PRECEDING_ZERO = 10;
	
	private static final int SUCCESSFUL_REMOVAL = 0;
	
	private static final int ERROR_LOCATING_AUCTION = -2;

	/** The cancel auction button on this GUI. */
	@FXML
	private Button myCancelAuctionBtn;
	
	/** The back button on this GUI. */
	@FXML
	private Button myBackBtn;
	
	/** The GUI label that will show "Logged in as: " and the user's username. */
	@FXML
	private Label myUsernameLabel;
	
	/** The GUI table that will hold all of the auction info. */
	@FXML
	private TableView<AuctionCell> myAuctionTable;
	
	/** The GUI table column that will hold all of the auction names. */
	@FXML
	private TableColumn<AuctionCell, String> myNameColumn;
	
	/** The GUI table column that will hold all of the auction dates. */
	@FXML
	private TableColumn<AuctionCell, String> myDateColumn;
	
	/** The GUI table column that will hold all of the auction times. */
	@FXML
	private TableColumn<AuctionCell, String> myTimeColumn;
	
	/** The GUI image box that will hold the logo. */
	@FXML
	private ImageView myLogoImageView;
	
	/** The current user. */
	private NPContact myNPContact;
	
	/** The item inventory button on the main menu GUI. */
	private Button myItemInvBtn;
	
	/** The submit auction request button on the main menu GUI. */
	private Button mySubmitRequestBtn;
	
	/** The previous GUI window. */
	private Stage myParentStage;
	
	/** The current GUI window. */
	private Stage myStage;
	
	/** The Calendar where the auctions are stored. */
	private Calendar myCalendar;
	
	/**
	 * The constructor that is used by JavaFX.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert myCancelAuctionBtn != null : "fx:id=\"myCancelAuctionBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myBackBtn != null : "fx:id=\"myBackBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myUsernameLabel != null : "fx:id=\"myUsernameLabel\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myAuctionTable != null : "fx:id=\"myAuctionTable\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myNameColumn != null : "fx:id=\"myNameColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myDateColumn != null : "fx:id=\"myDateColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myTimeColumn != null : "fx:id=\"myTimeColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myLogoImageView != null : "fx:id=\"myLogoImageView\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		
		myAuctionTable.setPlaceholder(new Label("There are currently no auctions\n associated with your account."));
		
		Image logo = new Image("file:logo2_v3.png");
		myLogoImageView.setImage(logo);
	}

	/**
	 * Initializes all of the fields, works like a constructor.
	 * 
	 * @param theParentStage The previous GUI window.
	 * @param theStage The current GUI window.
	 * @param theCalendar The Calendar where the auctions are stored.
	 * @param theNPContact The current user.
	 * @param theItemInvBtn The item inventory button on the main menu GUI.
	 * @param theSubmitRequestsBtn The submit auction request button on the main menu GUI.
	 */
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact, Button theItemInvBtn, Button theSubmitRequestsBtn) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		myItemInvBtn = theItemInvBtn;
		mySubmitRequestBtn = theSubmitRequestsBtn;
		
		GregorianCalendar today = ((GregorianCalendar) GregorianCalendar.getInstance());
		
		if (myNPContact.getLatestAuction() == null || !myNPContact.getLatestAuction().getDate().after(today))
			myCancelAuctionBtn.setDisable(true);
		
		myUsernameLabel.setText(myNPContact.getUsername() + "'s Auctions: ");
		
		setupTable();
		updateAuctionTable();
		setupCancelAuctionBtn();
		setupBackBtn();
	}
	
	/**
	 * Defines what the cancel auction button should do when it's clicked.
	 */
	private void setupCancelAuctionBtn() {
		myCancelAuctionBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Auction auctionToRemove = myNPContact.getLatestAuction();

				ButtonType yesBtn = new ButtonType("Yes");
				Optional<ButtonType> result = removeConfirmationDialog(auctionToRemove, yesBtn);
				if (result.get() == yesBtn){
					int responseCode = myNPContact.removeMyAuction(myCalendar, auctionToRemove);
					
					if (responseCode == SUCCESSFUL_REMOVAL) {
						myCalendar.getAllAuctions().remove(auctionToRemove);
						updateAuctionTable();
						myItemInvBtn.setDisable(true);
						myCancelAuctionBtn.setDisable(true);
						if (myNPContact.hasAuctionUpcomingOrLastYear())
							mySubmitRequestBtn.setDisable(true);
						else
							mySubmitRequestBtn.setDisable(false);
					}  else if (responseCode == Calendar.AUCTION_LESS_THAN_TWO_DAYS_AWAY) {
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setTitle("Auction Central");
						errorAlert.setHeaderText("Removal Error");
						errorAlert.setContentText("Sorry, but your auction is less than 2 days from the date on which it occurs, and therefore cannot be cancelled.");
						
						errorAlert.showAndWait();
					}
				}
			}
		});
	}
	
	/**
	 * Handles the remove confirmation dialog pop-up box.
	 * 
	 * @return Returns the user's result.
	 */
	private Optional<ButtonType> removeConfirmationDialog(Auction theAuctionToRemove, ButtonType theYesBtn) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Auction Central");
		alert.setHeaderText("Removing Auction");
		alert.setContentText("Are you sure you would like to remove the following auction? You will not be able to undo this action.\n\nAuction Name: " + theAuctionToRemove.getAuctionName()
				+ "\nDate: " + myDateColumn.getCellData(0) + "\nTime: " + myTimeColumn.getCellData(0)  + "\nItems in auction: " + theAuctionToRemove.getItems().size() + "\n ");
		
		ButtonType noBtn = new ButtonType("No");

		alert.getButtonTypes().setAll(theYesBtn, noBtn);

		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}
	
	/**
	 * Defines what the back button should do when it's clicked.
	 */
	private void setupBackBtn() {
		myBackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					myParentStage.show();
					myStage.hide();
			}
		});
	}
	
	/**
	 * Sets various values to assure that the table is properly setup.
	 */
	private void setupTable() {
		myNameColumn.setCellValueFactory(new PropertyValueFactory<AuctionCell, String>("myName"));
		myDateColumn.setCellValueFactory(new PropertyValueFactory<AuctionCell, String>("myDate"));
		myTimeColumn.setCellValueFactory(new PropertyValueFactory<AuctionCell, String>("myTime"));
		myNameColumn.setStyle( "-fx-alignment: CENTER;");
		myDateColumn.setStyle( "-fx-alignment: CENTER;");
		myTimeColumn.setStyle( "-fx-alignment: CENTER;");
	}
	
	/**
	 * This method updates the auction table on this GUI.
	 */
	private void updateAuctionTable() {
		List<Auction> theAuctions =  myNPContact.getMyAuctions();
		
		AuctionCell[] auctionInfo = new AuctionCell[theAuctions.size()];
		
		if (theAuctions.size() == 0) {
			myCancelAuctionBtn.setDisable(true);
			mySubmitRequestBtn.setDisable(false);
		} else {
			for (int i = theAuctions.size() - 1; i >= 0; i--) {
				String auctionName = theAuctions.get(i).getAuctionName();
				GregorianCalendar temp = theAuctions.get(i).getDate();
				String filler = "";
				int month = temp.get(java.util.Calendar.MONTH) + 1;
				int day = temp.get(java.util.Calendar.DAY_OF_MONTH);
				int year = temp.get(java.util.Calendar.YEAR);
				if (day < NEED_PRECEDING_ZERO)
					filler = "0";
				
				int hour = temp.get(java.util.Calendar.HOUR_OF_DAY);
				int AMPMCheck = temp.get(java.util.Calendar.AM_PM);
				String hourFiller = "";
				String AMPM = "AM";
				
				if (AMPMCheck == 1) {
					AMPM = "PM";
					hour -= 12;
				}
				if (hour == 0) {
					hour = 12;
					if (AMPM.equals("PM"))
						AMPM = "AM";
					else
						AMPM = "PM";
				}
				
				if (hour < NEED_PRECEDING_ZERO)
					hourFiller = "0";
				
				auctionInfo[theAuctions.size() - 1 - i] = new AuctionCell(auctionName, filler + day + "/" + month + "/" + year, hourFiller + hour + " " + AMPM);
			}
		}
		myAuctionTable.setItems(FXCollections.observableList(Arrays.asList(auctionInfo)));
	}
	
	/**
	 * Object that is used by the JavaFX TableView to define what each cell should contain.
	 * 
	 * @author Vlad Kaganyuk
	 * @version Dec 5, 2016
	 */
	public class AuctionCell {
		private final SimpleStringProperty myName;
		private final SimpleStringProperty myDate;
		private final SimpleStringProperty myTime;
		
		/**
		 * Initializes all the necessary fields.
		 *  
		 * @param theName The auction's name.
		 * @param theDate The auction's date.
		 * @param theTime The auction's time.
		 */
		public AuctionCell(String theName, String theDate, String theTime) {
			myName = new SimpleStringProperty(theName);
			myDate = new SimpleStringProperty(theDate);
			myTime = new SimpleStringProperty(theTime);
		}
		
		/**
		 * Simple getter for myName.
		 * @return Returns myName.
		 */
		public String getMyName() {
            return myName.get();
        }
 
		/**
		 * Simple getter for myDate.
		 * @return Returns myDate.
		 */
        public String getMyDate() {
            return myDate.get();
        }
 
        /**
		 * Simple getter for myTime.
		 * @return Returns myTime.
		 */
        public String getMyTime() {
            return myTime.get();
        }
	}
}
