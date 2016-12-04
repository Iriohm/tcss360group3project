package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Auction;
import model.Calendar;
import model.NPContact;

public class NPContactAuctionRequestFormGUI implements Initializable {
	
	private static final List<Integer> NUMBERS_1_THROUGH_12 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
	
	private static final int DEFAULT_NUM_VALUE = 1;
	
	private static final String DEFAULT_AM_PM_VALUE = "AM";
	
	private static final int YEAR_INDEX = 0;
	
	private static final int MONTH_INDEX = 1;
	
	private static final int DAY_INDEX = 2;
	
	@FXML
	private Button mySubmitBtn;
	
	@FXML
	private TextField myAuctionName;
	
	@FXML
	private ChoiceBox<Integer> myNumChoice;
	
	@FXML
	private ChoiceBox<String> myAMPMChoice;
	
	@FXML
	private Button myBackBtn;
	
	@FXML
	private DatePicker myDatePicker;
	
	@FXML
	private Label myUsernameLabel;
	
	private Calendar myCalendar;
	
	private NPContact myNPContact;
	
	private Stage myParentStage;
	
	private Stage myStage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitBtn != null : "fx:id=\"mySubmitBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myAuctionName != null : "fx:id=\"myAuctionName\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myNumChoice != null : "fx:id=\"myNumChoice\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myAMPMChoice != null : "fx:id=\"myAMPMChoice\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myDatePicker != null : "fx:id=\"myDatePicker\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myBackBtn != null : "fx:id=\"myBackBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myUsernameLabel != null : "fx:id=\"myUsernameLabel\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		
		myNumChoice.setValue(DEFAULT_NUM_VALUE);
		myNumChoice.setItems(FXCollections.observableList(NUMBERS_1_THROUGH_12));
		
		myAMPMChoice.setValue(DEFAULT_AM_PM_VALUE);
		myAMPMChoice.setItems(FXCollections.observableArrayList("AM", "PM"));
	}
	
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		
		mySubmitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GregorianCalendar auctionDateGregCalendar = new GregorianCalendar();
				
				String auctionName = myAuctionName.getText();
				String auctionDate = myDatePicker.getValue().toString();
				int auctionHour = myNumChoice.getValue();
				String auctionAMPM = myAMPMChoice.getValue();
				
				String[] date = auctionDate.split("-");
				
				if (auctionAMPM.equals("PM")) {
					auctionHour += 12;
				}
				
				int year = Integer.parseInt(date[YEAR_INDEX]);
				int month = Integer.parseInt(date[MONTH_INDEX]);
				int day = Integer.parseInt(date[DAY_INDEX]);
				auctionDateGregCalendar.set(year, month, day, auctionHour, 0, 0);
				auctionDateGregCalendar.add(GregorianCalendar.MONTH, -1);
				
				Auction auctionRequest = new Auction(auctionDateGregCalendar, auctionName);
				myCalendar.addAuction(auctionRequest);
				myNPContact.addAuction(auctionRequest);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Auction Central");
				alert.setHeaderText("Successful submission");
				
				myStage.hide();
				alert.setContentText("Successfully submitted your auction request. You can see your auctions by pressing the \"View my auctions\" button on the main menu.");

				alert.showAndWait();
				
				myParentStage.show();
			}
		});
		
		myBackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Auction Central");
				alert.setHeaderText("Unsaved Information");
				alert.setContentText("Are you sure you would like to go back?\nYour input has not be saved.");
				
				ButtonType yesBtn = new ButtonType("Yes");
				ButtonType noBtn = new ButtonType("No");

				alert.getButtonTypes().setAll(yesBtn, noBtn);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == yesBtn){
					myParentStage.show();
					myStage.hide();
				}
			}
		});
	}

}
