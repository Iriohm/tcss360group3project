package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.NPContactItemInventoryGUI.ItemCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class NPContactViewAuctionsGUI implements Initializable {
	
	private static final int NEED_PRECEDING_ZERO = 10;
	
	private static final int SUCCESSFUL_REMOVAL = 0;
	
	private static final int ERROR_LOCATING_AUCTION = -2;

	@FXML
	private Button myCancelAuctionBtn;
	
	@FXML
	private Button myBackBtn;
	
	@FXML
	private Label myUsernameLabel;
	
	@FXML
	private TableView<AuctionCell> myAuctionTable;
	
	@FXML
	private TableColumn<AuctionCell, String> myNameColumn;
	
	@FXML
	private TableColumn<AuctionCell, String> myDateColumn;
	
	@FXML
	private TableColumn<AuctionCell, String> myTimeColumn;
	
	@FXML
	private ImageView myLogoImageView;
	
	private NPContact myNPContact;
	
	private Button myItemInvBtn;
	
	private Button mySubmitRequestBtn;
	
	private Stage myParentStage;
	
	private Stage myStage;
	
	private Calendar myCalendar;
	
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

	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact, Button theItemInvBtn, Button theSubmitRequestsBtn) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		myItemInvBtn = theItemInvBtn;
		mySubmitRequestBtn = theSubmitRequestsBtn;
		
		if (!myNPContact.hasAuctionUpcomingOrLastYear()) {
			myCancelAuctionBtn.setDisable(true);
		}
		
		myUsernameLabel.setText(myNPContact.getUsername() + "'s Auctions: ");
		
		myNameColumn.setCellValueFactory(new PropertyValueFactory<AuctionCell, String>("myName"));
		myDateColumn.setCellValueFactory(new PropertyValueFactory<AuctionCell, String>("myDate"));
		myTimeColumn.setCellValueFactory(new PropertyValueFactory<AuctionCell, String>("myTime"));
		myNameColumn.setStyle( "-fx-alignment: CENTER;");
		myDateColumn.setStyle( "-fx-alignment: CENTER;");
		myTimeColumn.setStyle( "-fx-alignment: CENTER;");
		
		updateAuctionTable();
		
		myCancelAuctionBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Auction auctionToRemove = myNPContact.getLatestAuction();
				/**
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Auction Central");
				alert.setHeaderText("Removing Auction");
				alert.setContentText("Are you sure you would like to remove the following auction? You will not be able to undo this action.\n\nAuction Name: " + auctionToRemove.getAuctionName()
						+ "\nDate: " + "\nTime: " + "\n ");
				
				ButtonType yesBtn = new ButtonType("Yes");
				ButtonType noBtn = new ButtonType("No");

				alert.getButtonTypes().setAll(yesBtn, noBtn);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == yesBtn){
					myNPContact.removeMyAuction(myCalendar, auctionToRemove);
					updateAuctionTable();
				}
				*/
				int responseCode = myNPContact.removeMyAuction(myCalendar, auctionToRemove);
				if (responseCode == SUCCESSFUL_REMOVAL) {
					updateAuctionTable();
					myItemInvBtn.setDisable(true);
				} else if (responseCode == ERROR_LOCATING_AUCTION) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Auction Central");
					alert.setHeaderText("Removal Error");
					alert.setContentText("Sorry, but your auction is less than 2 days from the date on which it occurs, and therefore cannot be cancelled.");
					
					alert.showAndWait();
				}
			}
		});
		
		myBackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					myParentStage.show();
					myStage.hide();
			}
		});
	}
	
	private void updateAuctionTable() {
		List<Auction> theAuctions =  myNPContact.getMyAuctions();
		
		AuctionCell[] auctionInfo = new AuctionCell[theAuctions.size()];
		
		if (theAuctions.size() == 0 || !myNPContact.hasAuctionUpcomingOrLastYear()) {
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
				if (hour < NEED_PRECEDING_ZERO)
					hourFiller = "0";
				
				auctionInfo[theAuctions.size() - 1 - i] = new AuctionCell(auctionName, month + "/" + filler + day + "/" + year, hourFiller + hour + " " + AMPM);
			}
		}
		myAuctionTable.setItems(FXCollections.observableList(Arrays.asList(auctionInfo)));
	}
	
	public class AuctionCell {
		private final SimpleStringProperty myName;
		private final SimpleStringProperty myDate;
		private final SimpleStringProperty myTime;
		
		public AuctionCell(String theName, String theDate, String theTime) {
			myName = new SimpleStringProperty(theName);
			myDate = new SimpleStringProperty(theDate);
			myTime = new SimpleStringProperty(theTime);
		}
		
		public String getMyName() {
            return myName.get();
        }
 
        public String getMyDate() {
            return myDate.get();
        }
 
        public String getMyTime() {
            return myTime.get();
        }
	}
}
