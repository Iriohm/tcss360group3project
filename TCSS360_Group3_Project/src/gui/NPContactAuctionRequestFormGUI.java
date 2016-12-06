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

/**
* This class is used to control the Auction Request Submission GUI for NPContact users.
*
* @author Vlad Kaganyuk
* @version 5 Dec 2016
*
*/
public class NPContactAuctionRequestFormGUI implements Initializable {
	
	/** A list of items 1-12. Used for the user's time. */
	private static final List<Integer> NUMBERS_1_THROUGH_12 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
	
	/** The default hour value. */
	private static final int DEFAULT_NUM_VALUE = 1;
	
	/** The default AM/PM value. */
	private static final String DEFAULT_AM_PM_VALUE = "PM";
	
	/** The index where the year is stored. */
	private static final int YEAR_INDEX = 0;
	
	/** The index where the month is stored. */
	private static final int MONTH_INDEX = 1;
	
	/** The index where the day is stored. */
	private static final int DAY_INDEX = 2;
	
	/** Error code to signify there are already two auctions on that day. */
	private static final int TWO_AUCTIONS_ON_SAME_DAY_ERROR = -1;
	
	/** Error code to signify that the chosen date is more than one month into the future. */
	private static final int MORE_THAN_MONTH_INTO_FUTURE_ERROR = -3;
	
	/** Error code to signify that the chosen date is less than a week away. */
	private static final int LESS_THAN_WEEK_IN_FUTURE_ERROR = -4;
	
	/** The submit button on this GUI. */
	@FXML
	private Button mySubmitBtn;
	
	/** The GUI text field that will hold the auction's name. */
	@FXML
	private TextField myAuctionName;
	
	/** The GUI text field that will hold the auction's hour. */
	@FXML
	private ChoiceBox<Integer> myNumChoice;
	
	/** The GUI option box that will hold the auctions AM/PM value. */
	@FXML
	private ChoiceBox<String> myAMPMChoice;
	
	/** The back button on this GUI. */
	@FXML
	private Button myBackBtn;
	
	/** The GUI DatePicker that will be used to determine the auction date. */
	@FXML
	private DatePicker myDatePicker;
	
	/** The GUI label that will show "Logged in as: " and the user's username. */
	@FXML
	private Label myUsernameLabel;
	
	/** The GUI image box that will hold the logo. */
	@FXML
	private ImageView myLogoImageView;
	
	/** The Calendar where the auctions are stored. */
	private Calendar myCalendar;
	
	/** The item inventory button on the NPContact GUI. */
	private Button myItemInvBtn;
	
	/** The submit auction request button on the NPContact GUI. */
	private Button myAuctionRequestBtn;
	
	/** The current user */
	private NPContact myNPContact;
	
	/** The previous GUI window. */
	private Stage myParentStage;
	
	/** The current GUI window. */
	private Stage myStage;

	/**
	 * The constructor that is used by JavaFX.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		
		final Callback<DatePicker, DateCell> dayCellFactory = createDayCell(weekAway, monthAway);
		myDatePicker.setDayCellFactory(dayCellFactory);
		
	}
	
	/**
	 * Initializes all of the fields, works like a constructor.
	 * 
	 * @param theParentStage The previous GUI window.
	 * @param theStage The current GUI window.
	 * @param theCalendar The Calendar where the auctions are stored.
	 * @param theNPContact The current user.
	 * @param theRequestBtn The submit auction request button.
	 * @param theItemInvBtn The item inventory button.
	 */
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact, Button theRequestBtn, Button theItemInvBtn) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		myItemInvBtn = theItemInvBtn;
		myAuctionRequestBtn = theRequestBtn;
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		
		setupSubmitBtn();
		
		setupBackBtn();
	}
	
	/**
	 * Defines what the submit button should do when it's clicked.
	 */
	private void setupSubmitBtn() {
		mySubmitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GregorianCalendar auctionDateGregCalendar = new GregorianCalendar();
				String auctionName = myAuctionName.getText();
				if (auctionName.length() == 0) {
					showErrorMessage("Please enter an auction name.");
					return;
				}
				
				String auctionDate = testAuctionDate();
				if (auctionDate == null) 
					return;
				
				setupGregCalendar(auctionDate, auctionDateGregCalendar);
				int responseCode = myCalendar.validateAuctionRequest(auctionDateGregCalendar);
				if (validateResult(responseCode) == null)
					return;
				
				addAuction(auctionName, auctionDateGregCalendar);
				myParentStage.show();
			}
		});
	}
	
	/**
	 * Calls the addAuction methods and opens a success pop-up box.
	 * @param auctionName The auction's name.
	 * @param auctionDateGregCalendar The auction's GregorianCalendar.
	 */
	private void addAuction(String auctionName, GregorianCalendar auctionDateGregCalendar) {
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
	}
	
	/**
	 * Sets up the GregorianCalendar associated with the auction request.
	 * 
	 * @param auctionDate The user's date, in String format.
	 * @param auctionDateGregCalendar The auction's GregorianCalendar.
	 */
	private void setupGregCalendar(String auctionDate, GregorianCalendar auctionDateGregCalendar) {
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
	}
	
	/**
	 * Decides whether an error occurred, and opens a pop-up for the corresponding error.
	 * 
	 * @param responseCode The response code from validateAuctionRequest();
	 * @return Returns a value to notify the caller if an error occurred or not.
	 */
	private String validateResult(int responseCode) {
		switch(responseCode) {
		case TWO_AUCTIONS_ON_SAME_DAY_ERROR:
			showErrorMessage("There are already two auctions scheduled on this day, please choose another.\n");
			return null;
		case MORE_THAN_MONTH_INTO_FUTURE_ERROR:
			showErrorMessage("Please select an earlier time (or date) for your auction, such that it's no more than exactly one month into the future.");
			return null;
		case LESS_THAN_WEEK_IN_FUTURE_ERROR:
			showErrorMessage("Please select a time for your auction such that your date is at least exactly one week into the future.");
			return null;
		}
		return "Successful";
	}
	
	/**
	 * Checks if the user has submitted a valid date.
	 * 
	 * @return The user's valid date, or null.
	 */
	private String testAuctionDate() {
		String auctionDate;
		try {
			auctionDate = myDatePicker.getValue().toString();
		} catch (NullPointerException e) {
			showErrorMessage("There was an error with your proposed auction date. Please select a valid date and try again.");
			return null;
		}
		return auctionDate;
	}
	
	/**
	 * Defines what the submit button should do when it's clicked.
	 */
	private void setupBackBtn() {
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
	
	/**
	 * Sets up the functionality that disables days outside the valid range.
	 * 
	 * @param weekAway The date one week away from now.
	 * @param monthAway The date one month away from now.
	 * @return Returns a valid Callback<DatePicker, DateCell> object.
	 */
	private Callback<DatePicker, DateCell> createDayCell(LocalDate weekAway, LocalDate monthAway) {
		Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
            		    super.updateItem(item, empty);

            		    if (item.isBefore(weekAway) || item.isAfter(monthAway)) {
            		            setDisable(true);
            		            setStyle("-fx-background-color: #ffc0cb;");
            		    }
            		}
                };
            }
		};
		return dayCellFactory;
	}
	
	/**
	 * Opens an error pop-up box.
	 * 
	 * @param errorMessage The message that the error pop-up will contain.
	 */
	private void showErrorMessage(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Auction Central");
		alert.setHeaderText("Input Error");
		alert.setContentText(errorMessage);
		
		alert.showAndWait();
	}
}
