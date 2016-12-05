package gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import dataStorage.SerializeData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.*;


/**
 * The Controller component of the Bidder GUI.
 *
 * @author Iriohm
 * @version Dec 2016
 */
public class BidderGUI implements Initializable {

	/** The "Log Out" button. Pressing this should return the user to the login screen. */
	@FXML
	private Button myLogOutButton;

	/** The text for indicating which user is logged in, including but not limited to their username. */
	@FXML
	private Text myUserNameDisplay;

	/** The text for displaying various feedback to the user, such as why they were unable to place a bid. */
	@FXML
	private Text myFeedbackText;

	/** The "Place Bid" button, for placing bids on the currently selected item. */
	@FXML
	private Button myPlaceBidButton;

	/** The "Cancel Bid" button, for canceling bids on the currently selected item. */
	@FXML
	private Button myCancelBidButton;

	/** A text field for entering some monetary value, so the user can indicate how much they'd like to bid. */
	@FXML
	private TextField myBidAmountField;

	/** A drop-down list for displaying all available Auctions, so the user can select one to view. */
	@FXML
	private ComboBox<String> myAuctionDropDown;

	/** An area for displaying details on the currently selected Item. */
	@FXML
	private Text myItemDetails;

	/** A list for displaying basic information on all Items within a selected Auction. */
	@FXML
	private ListView<String> myItemsList;


	/** The Bidder currently using this GUI. */
	private Bidder myBidder;

	/** The Calendar this GUI is pulling from. */
	private Calendar myCalendar;

	/** A holding area for Auctions from the Calendar, so they don't have to be reacquired multiple times. */
	private List<Auction> myCurrentAuctionList;

	/** The Auction currently being viewed. */
	private Auction myCurrentAuction;

	/** A holding area for Items from the current Auction, so they don't have to be reacquired multiple times. */
	private List<Item> myCurrentItemList;

	/** The Item currently being viewed. */
	private Item myCurrentItem;

	/** The Stage currently being viewed. */
	private Stage myStage;

	/** A simple formatter for dates. */
	private Format myDateFormatter = new SimpleDateFormat("dd/MM/yyyy");


	/**
	 * One of three methods needed to fully initialize this Controller.
	 * Call this one, then setBidder(), then initAll().
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL theURL, ResourceBundle theResources) {
		assert myLogOutButton != null : "fx:id=\"myLogOutButton\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myUserNameDisplay != null : "fx:id=\"myUserNameDisplay\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myFeedbackText != null : "fx:id=\"myFeedbackText\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myPlaceBidButton != null : "fx:id=\"myPlaceBidButton\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myCancelBidButton != null : "fx:id=\"myCancelBidButton\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myBidAmountField != null : "fx:id=\"myPlaceBidField\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myAuctionDropDown != null : "fx:id=\"myAuctionDropDown\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myItemDetails != null : "fx:id=\"myItemDetails\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myItemsList != null : "fx:id=\"myItemList\" was not injected: check your FXML file 'Bidder.fxml'.";

		myCalendar = new Calendar();

	}


	/**
	 * Sets the Bidder using this GUI. Also sets the Calendar and Auction Lists based on that Bidder.
	 * This method must be called before initAll().
	 *
	 * @param theBidder The Bidder to be using the GUI.
	 */
	public void setBidder(Bidder theBidder) {
		myBidder = theBidder;
		myCalendar = myBidder.getCalendar();
		myCurrentAuctionList = myCalendar.getAuctions(new GregorianCalendar());

	}


	/**
	 * Sets the Bidder using this GUI. Also sets the Calendar and Auction Lists based on the given SerializeData.
	 * This method must be called before initAll().
	 *
	 * @param theBidder The Bidder to be using the GUI.
	 */
	public void feedData(Bidder theBidder, SerializeData theData) {
		myBidder = theBidder;
		myCalendar = theData.getCalendar();
		myCurrentAuctionList = myCalendar.getAuctions(new GregorianCalendar());

	}


	/**
	 * This method must be called in addition to initialize() to fully initialize the Controller.
	 * It must also be called only after setBidder(), or things will break.
	 */
	public void initAll(Stage theStage) {
		myStage = theStage;
		myUserNameDisplay.setText("Logged in as " + myBidder.getUsername());
		initLogOutButton();
		initBidAmountField();
		initPlaceBidButton();
		initCancelBidButton();
		initAuctionDropDown();
		initItemList();

	}


	/**
	 * Initializes the Log Out Button.
	 */
	private void initLogOutButton() {
		myLogOutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				 Authenticate.setupAuthenticate(myStage);

			}


		});

	}


	/**
	 * Initializes the Bid Amount TextField.
	 */
	private void initBidAmountField() {
		//http://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
		myBidAmountField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

	}


	/**
	 * Initializes the Place Bid Button.
	 */
	private void initPlaceBidButton() {
		myPlaceBidButton.setDisable(true);
		//http://code.makery.ch/blog/javafx-dialogs-official/
		Alert alertConfirmBid = new Alert(AlertType.CONFIRMATION);
		alertConfirmBid.setTitle("Confirm Bid");

		DecimalFormat dfMoney = new DecimalFormat("0.00");

		myPlaceBidButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if	(!myBidAmountField.getText().isEmpty()) {
					String sMoney = myBidAmountField.getText();

					double dInput = 0.00;
					try {
						dInput = dfMoney.getNumberInstance(Locale.US).parse(sMoney).doubleValue();
					} catch (ParseException e) {
						e.printStackTrace();
					}

					// Maxi-business-rule Testing Time!!! ^_^
					if	(biddingTests(dInput)) {
						alertConfirmBid.setHeaderText("You are about to bid $" + dfMoney.format(dInput)
								+ " on item \"" + myCurrentItem.getName() + "\" from the auction \"" + myCurrentAuction.getAuctionName()
								+ "\", closing " + myDateFormatter.format(myCurrentAuction.getDate().getTime())
								+ ". \nThis bid may be canceled at any point at least two days before the auction takes place. "
								+ "\nBids made or existing after this point are final. \n\n Continue with the bid?");

						Optional<ButtonType> osResult = alertConfirmBid.showAndWait();

						if	(osResult.get() == ButtonType.OK) {
							myBidder.placeBid(myCurrentItem, dInput);

							Alert alertSuccess = new Alert(AlertType.INFORMATION);
							alertSuccess.setTitle("Success!");
							alertSuccess.setHeaderText("Bid made successfully!");

							updateItemsInList();
							updateItemDescription();

						}

					}

				}

			}


		});

	}


	/**
	 * A subsidiary method to initPlaceBidButton(), so it doesn't have to be so large.
	 * Runs through various tests to ensure the Bidder can make a bid on the current Item,
	 * displaying various explanatory errors if they cannot.
	 *
	 * @param theInput The amount being bid.
	 * @return Returns true if the Bidder can bid on the Item. Otherwise returns false.
	 */
	private boolean biddingTests(double theInput) {
		Alert alertError = new Alert(AlertType.ERROR);

		if	(myCurrentItem.isBeingBidOnBy(myBidder.getUsername())) {
			 alertError.setTitle("Duplicate Bid");
			 alertError.setHeaderText("So sorry. You can't bid on an item you've already bid on. "
					 + "\n Try cancelling the old bid first?");
			 alertError.showAndWait();
			 return false;

		} else if (theInput < 0) {
			 alertError.setTitle("Negative Bid Amount");
			 alertError.setHeaderText("You can't bid a negative amount, silly.");
			 alertError.showAndWait();
			 return false;

		} else if (theInput == 0.0) {
			 alertError.setTitle("Bid of Zero");
			 alertError.setHeaderText("You can't bid zero. Please bid some higher amount.");
			 alertError.showAndWait();
			 return false;

		} else if (theInput < myCurrentItem.getMinBid()) {
			 alertError.setTitle("Bid Less than Minimum");
			 alertError.setHeaderText("You must bid at least the minimum amount.");
			 alertError.showAndWait();
			 return false;

		}

		return true;

	}


	/**
	 * Initializes the Cancel Bid Button.
	 */
	private void initCancelBidButton() {
		myCancelBidButton.setDisable(true);

		myCancelBidButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if	(Bidder.hasAuctionPassedTwoDayCutoffPoint(myCurrentItem)) {
					Alert alertError = new Alert(AlertType.ERROR);
					alertError.setTitle("Auction Too Soon");
					alertError.setHeaderText("A bid cannot be canceled if the auction is within two days. "
								+ "\nWe apologize for this inconvenience.");
					alertError.showAndWait();

				} else {
					Alert alertConfirmCancel = new Alert(AlertType.CONFIRMATION);
					alertConfirmCancel.setTitle("Cancel Bid?");
					alertConfirmCancel.setHeaderText("You are trying to cancel your bid on item \""
								+	myCurrentItem.getName() + "\". Continue?" );

					Optional<ButtonType> osResult = alertConfirmCancel.showAndWait();

					if	(osResult.get() == ButtonType.OK) {
						myBidder.cancelBid(myCurrentItem);
						updateItemsInList();
						updateItemDescription();

						Alert alertSuccess = new Alert(AlertType.INFORMATION);
						alertSuccess.setTitle("Bid Canceled");
						alertSuccess.setHeaderText("Your bid was canceled.");
						alertSuccess.showAndWait();

					}

				}

			}


		});

	}

	/**
	 * Initializes the Auction Drop-Down Menu.
	 */
	//To do: Sort auctions by date.
	private void initAuctionDropDown() {
		ObservableList<String> olAuctions = FXCollections.observableArrayList();

		StringBuilder sbInfo;
		Formatter formatInfo;

		for	(Auction auctFocus : myCurrentAuctionList) {
			sbInfo = new StringBuilder();
			formatInfo = new Formatter(sbInfo, Locale.US);

			formatInfo.format("%-24s%10s", auctFocus.getAuctionName(), myDateFormatter.format(auctFocus.getDate().getTime()));

			olAuctions.add(sbInfo.toString());

		}

		myAuctionDropDown.setItems(olAuctions);

		// http://stackoverflow.com/questions/16437872/fxml-combobox-get-the-selected-value-into-javafx
		myAuctionDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> theObservable, String theOldValue, String theNewValue) {
				myCurrentAuction = myCurrentAuctionList.get(myAuctionDropDown.getSelectionModel().getSelectedIndex());

				updateItemsInList();

			}

	});

	}


	/**
	 * Initializes the Item List.
	 */
	private void initItemList() {
		// http://stackoverflow.com/questions/9722418/how-to-handle-listview-item-clicked-action

		myItemsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent theEvent) {
				int iIndex = myItemsList.getSelectionModel().getSelectedIndex();
				if	(iIndex >= 0) {
					myCurrentItem = myCurrentItemList.get(iIndex);
					updateItemDescription();

				}

			}

		});

	}



	/**
	 * Updates the Item description with info from the currently selected Item.
	 */
	private void updateItemDescription() {
		StringBuilder sbItemInfo = new StringBuilder();
		DecimalFormat dfMinBid = new DecimalFormat("0.00");

			sbItemInfo.append(myCurrentItem.getName());

			sbItemInfo.append("\nItem ID: ");
			sbItemInfo.append(myCurrentItem.getID());

			sbItemInfo.append("\nCondition: ");
			sbItemInfo.append(myCurrentItem.getCondition());

			sbItemInfo.append("\nQuantity: ");
			sbItemInfo.append(myCurrentItem.getCondition());

			sbItemInfo.append("\nSize: ");
			sbItemInfo.append(myCurrentItem.getSize());

			sbItemInfo.append("\n\n");
			sbItemInfo.append(myCurrentItem.getDescription());

			sbItemInfo.append("\n\nMinimum Bid: $");
			sbItemInfo.append(dfMinBid.format(myCurrentItem.getMinBid()));

		if	(myCurrentItem.isBeingBidOnBy(myBidder.getUsername())) {
			sbItemInfo.append("\nYour Bid: $");
			sbItemInfo.append(dfMinBid.format(myCurrentItem.getBidOf(myBidder.getUsername()).getBidAmount()));
			myCancelBidButton.setDisable(false);

		} else {
			sbItemInfo.append("\nYou have made no bids on this item.");
			myCancelBidButton.setDisable(true);

		}

		myItemDetails.setText(sbItemInfo.toString());

		myPlaceBidButton.setDisable(false);

	}


	/**
	 * Updates the Item List with the contents of a new Auction.
	 */
	private void updateItemsInList() {
		ObservableList<String> olItems = FXCollections.observableArrayList();

		StringBuilder sbInfo;
		Formatter formatInfo;

		myCurrentItemList = myCurrentAuction.getItems();

		for	(Item itemFocus : myCurrentItemList) {
			sbInfo = new StringBuilder();
			formatInfo = new Formatter(sbInfo, Locale.US);

			// If the Bidder has made a bid on this item, say so.
			if	(itemFocus.isBeingBidOnBy(myBidder.getUsername())) {
				formatInfo.format("%-29s%-10s%14.2f%14.2f", itemFocus.getName(),
						itemFocus.getCondition(), itemFocus.getMinBid(),
						itemFocus.getBidOf(myBidder.getUsername()).getBidAmount());

			} else {
				formatInfo.format("%-29s%-10s%14.2f%14s", itemFocus.getName(),
						itemFocus.getCondition(), itemFocus.getMinBid(), "N/A");

			}

			olItems.add(sbInfo.toString());

		}

		myItemsList.setItems(olItems);

	}


}
