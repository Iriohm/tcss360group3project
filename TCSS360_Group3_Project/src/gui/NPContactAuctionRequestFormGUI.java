package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
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
	
	@FXML
	private ImageView myLogoImageView;
	
	private Calendar myCalendar;
	
	private Button myItemInvBtn;
	
	private Button myAuctionRequestBtn;
	
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
		assert myLogoImageView != null : "fx:id=\"myLogoImageView\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";

		myNumChoice.setValue(DEFAULT_NUM_VALUE);
		myNumChoice.setItems(FXCollections.observableList(NUMBERS_1_THROUGH_12));
		
		myAMPMChoice.setValue(DEFAULT_AM_PM_VALUE);
		myAMPMChoice.setItems(FXCollections.observableArrayList("AM", "PM"));
		
		Image logo = new Image("file:logo2_v3.png");
		myLogoImageView.setImage(logo);
		
		LocalDate rightNow = LocalDate.now();
		LocalDate weekAway = rightNow.plusWeeks(1);
		LocalDate monthAway = rightNow.plusMonths(1);
		
		myDatePicker.setValue(weekAway);
		
		 final Callback<DatePicker, DateCell> dayCellFactory = 
		            new Callback<DatePicker, DateCell>() {
		                @Override
		                public DateCell call(final DatePicker datePicker) {
		                    return new DateCell() {
		                        @Override
		                        public void updateItem(LocalDate item, boolean empty) {
		                		    super.updateItem(item, empty);
		                		    
		                		    LocalDate x = myDatePicker.getValue();

		                		    if (item.isBefore(weekAway) || item.isAfter(monthAway)) {
		                		            setDisable(true);
		                		            setStyle("-fx-background-color: #ffc0cb;");
		                		    }
		                		}
		                };
		            }
		        };
		        myDatePicker.setDayCellFactory(dayCellFactory);
		
	}
	
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact, Button theRequestBtn, Button theItemInvBtn) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		myItemInvBtn = theItemInvBtn;
		myAuctionRequestBtn = theRequestBtn;
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		
		mySubmitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GregorianCalendar auctionDateGregCalendar = new GregorianCalendar();
				
				String auctionName = myAuctionName.getText();
				String auctionDate = "MM-DD-YYYY";
				
				if (auctionName.length() == 0) {
					showErrorMessage("Please enter an auction name.");
					return;
				}
				
				try {
					auctionDate = myDatePicker.getValue().toString();
				} catch (NullPointerException e) {
					showErrorMessage("There was an error with your proposed auction date. Please select a valid date and try again.");
					return;
				}
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
				
				int responseCode = myCalendar.validateAuctionRequest(auctionDateGregCalendar);
				
				switch(responseCode) {
				case -1:
					showErrorMessage("There are already two auctions scheduled on this day, please choose another.\n");
					return;
				case -3:
					showErrorMessage("Please select an earlier time (or date) for your auction, such that it's no more than exactly one month into the future.");
					return;
				case -4:
					showErrorMessage("Please select a time for your auction such that your date is at least exactly one week into the future.");
					return;
				}
				
				Auction auctionRequest = new Auction(auctionDateGregCalendar, auctionName);
				myCalendar.addAuction(auctionRequest);
				myNPContact.addAuction(auctionRequest);
				
				myItemInvBtn.setDisable(false);
				myAuctionRequestBtn.setDisable(true);
				
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
	
	private void showErrorMessage(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Auction Central");
		alert.setHeaderText("Input Error");
		alert.setContentText(errorMessage);
		
		alert.showAndWait();
	}

	public class DisabledRange {

	    private final LocalDate initialDate;
	    private final LocalDate endDate;

	    public DisabledRange(LocalDate initialDate, LocalDate endDate){
	        this.initialDate=initialDate;
	        this.endDate = endDate;
	    }

	    public LocalDate getInitialDate() { return initialDate; }
	    public LocalDate getEndDate() { return endDate; }

	}
}
