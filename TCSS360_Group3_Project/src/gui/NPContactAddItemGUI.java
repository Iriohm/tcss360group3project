package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.NPContactItemInventoryGUI.ItemCell;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

/**
* This class is used to control the Add Item GUI for NPContact users.
*
* @author Vlad Kaganyuk
* @version 5 Dec 2016
*
*/
public class NPContactAddItemGUI implements Initializable {
	
	/** Different size options allowed for an item. */
	private static final String[] SIZE_OPTIONS = {"Small", "Medium", "Large"};

	/** Error code to signify there was an error with the item quantity. */
	private static final int QUANTITY_ERROR = -1;
	
	/** Error code to signify there was an error with the item's minimum bid. */
	private static final double MIN_BID_ERROR = -1.0;
	
	/** The submit button on this GUI. */
	@FXML
	private Button mySubmitBtn;
	
	/** The back button on this GUI. */
	@FXML
	private Button myBackBtn;
	
	/** The GUI text field that will hold the item's name. */
	@FXML
	private TextField myNameBox;
	
	/** The GUI text field that will hold the item's condition. */
	@FXML
	private TextField myConditionBox;
	
	/** The GUI text field that will hold the item's minimum bid.*/
	@FXML
	private TextField myMinBidBox;
	
	/** The GUI text field that will hold the item's quantity. */
	@FXML
	private TextField myQuantityBox;
	
	/** The GUI option box that will hold the item's size. */
	@FXML
	private ChoiceBox<String> mySizeChoiceBox;
	
	/** The GUI text field that will hold the item's description. */
	@FXML
	private TextField myDescriptionBox;
	
	/** The GUI label that will show "Logged in as: " and the user's username. */
	@FXML
	private Label myUsernameLabel;
	
	/** The GUI image box that will hold the logo. */
	@FXML
	private ImageView myLogoImageView;
	
	/** The GUI label that will state which auction the user is adding an item to. */
	@FXML
	private Text myHeaderText;
	
	/** The current user, a NPContact. */
	private NPContact myNPContact;
	
	/** The previous GUI window. */
	private Stage myParentStage;
	
	/** The current GUI window. */
	private Stage myStage;
	
	/** The auction that items will be added to. */
	private Auction myAuction;
	
	/** The Calendar where the auctions are stored. */
	private Calendar myCalendar;
	
	/** The GUI table that will hold all of the item info. */
	@FXML
	private TableView<NPContactItemInventoryGUI.ItemCell> myItemTableView;
	
	/** The remove item button on this GUI. */
	private Button myRemoveItemBtn;
	
	/** The option box on the GUI, to select the item to delete. This box gets updated after an event. */
	@FXML
	private ChoiceBox<String> myItemChoice;
	
	/**
	 * The constructor that is used by JavaFX.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitBtn != null : "fx:id=\"mySubmitBtn\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myBackBtn != null : "fx:id=\"myBackBtn\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myNameBox != null : "fx:id=\"myNameBox\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myConditionBox != null : "fx:id=\"myConditionBox\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myMinBidBox != null : "fx:id=\"myMinBidBox\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myQuantityBox != null : "fx:id=\"myQuantityBox\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert mySizeChoiceBox != null : "fx:id=\"mySizeChoiceBox\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myDescriptionBox != null : "fx:id=\"myDescriptionBox\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myUsernameLabel != null : "fx:id=\"myUsernameLabel\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myLogoImageView != null : "fx:id=\"myLogoImageView\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		assert myHeaderText != null : "fx:id=\"myHeaderText\" was not injected: check your FXML file 'NPContactAddItemGUI.fxml'.";
		
		Image logo = new Image("file:logo2_v3.png");
		myLogoImageView.setImage(logo);
	}
	
	/**
	 * Initializes all of the fields, works like a constructor.
	 * 
	 * @param theParentStage The previous GUI window.
	 * @param theStage The current GUI window.
	 * @param theCalendar The Calendar where the auctions are stored.
	 * @param theAuction The auction where items will be added.
	 * @param theNPContact The current user.
	 * @param theTableView The GUI table that will hold all of the item info.
	 * @param theRemoveBtn The remove item button on this GUI.
	 * @param theItemChoice The option box on the GUI, to select the item to delete.
	 */
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, Auction theAuction, NPContact theNPContact, 
			TableView<NPContactItemInventoryGUI.ItemCell> theTableView, Button theRemoveBtn, ChoiceBox<String> theItemChoice) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		myAuction = theAuction;
		myItemTableView = theTableView;
		myRemoveItemBtn = theRemoveBtn;
		myItemChoice = theItemChoice;
		
		mySizeChoiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(SIZE_OPTIONS)));
		mySizeChoiceBox.setValue(SIZE_OPTIONS[0]);
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		myHeaderText.setText("Adding Item to Auction: " + myAuction.getAuctionName());
		
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
				String itemName = myNameBox.getText();	
				String condition = myConditionBox.getText();
				String size = mySizeChoiceBox.getValue();
				String description = myDescriptionBox.getText();	
				if (itemName.equals("") || condition.equals("") || size.equals("") || description.equals("") || myMinBidBox.getText().equals("") || myQuantityBox.getText().equals("")) {
					showErrorMessage("Please be sure to fill out all of the fields.");
					return;
				}
				
				double minBid = testMinBidValue();
				if (minBid == MIN_BID_ERROR)
					return;
				
				int quantity = testQuantityValue();
				if (quantity == QUANTITY_ERROR)
					return;
				
				addItem(itemName, condition, size, description, minBid, quantity);
			}
		});
	}
	
	/**
	 * Defines what the back button should do when it's clicked.
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
	 * Adds the item to the correct auction, and opens a successful message pop-up box.
	 * 
	 * @param itemName The name of the item.
	 * @param condition The item's condition.
	 * @param size The item's size.
	 * @param description The item's description.
	 * @param minBid The item's minimum bid.
	 * @param quantity The item's quantity.
	 */
	private void addItem(String theItemName, String theCondition, String theSize, String theDescription, double theMinBid, int theQuantity) {
		Item itemToAdd = new Item(myCalendar.getNextItemID() + "", theItemName, theDescription, theSize, theMinBid, theQuantity, theCondition);
		
		myNPContact.addItem(myAuction, itemToAdd);
		myAuction.addItem(itemToAdd);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Auction Central");
		alert.setHeaderText("Successful addition");
		
		myStage.hide();
		alert.setContentText("Successfully added your item. Your item should now appear in the Item Inventory.");

		alert.showAndWait();
		
		updateItemsAfterAdding();
		myParentStage.show();
	}
	
	/**
	 * Tests the quantity value that the user provided.
	 * 
	 * @return Returns the valid quantity, or an error code.
	 */
	private int testQuantityValue() {
		int quantity =  0;
		try {
			quantity = Integer.parseInt(myQuantityBox.getText());
		} catch (NumberFormatException e) {
			showErrorMessage("Please make sure that the item quantity is a valid number.");
			return QUANTITY_ERROR;
		}
		
		if (quantity <= 0) {
			showErrorMessage("Please make sure that the item quantity is greater than 0.");
			return QUANTITY_ERROR;
		}
		return quantity;
	}
	
	/**
	 * Tests the minimum bid value that the user provided.
	 * 
	 * @return Returns the valid minBid, or an error code.
	 */
	private double testMinBidValue() {
		double minBid = 0;
		try {
			minBid = Double.parseDouble(myMinBidBox.getText());
		} catch (NumberFormatException e) {
			showErrorMessage("Please make sure that your minimum bid amount is a valid number. NOTE: Do not include the $ sign with your amount.");
			return MIN_BID_ERROR;
		}
		
		if (minBid <= 0.0) {
			showErrorMessage("Please make sure that your minimum bid amount is greater than $0.00");
			return MIN_BID_ERROR;
		}
		return minBid;
	}
	
	/**
	 * This method updates the item table on the ItemInventoryGUI.
	 */
	private void updateItemsAfterAdding() {
		List<Item> itemsInAuction = myAuction.getItems();
		ItemCell[] itemInfo = new ItemCell[itemsInAuction.size()];
		String[] itemIDs = new String[itemInfo.length];
		
		for (int i = 0; i < itemsInAuction.size(); i++) {
			Item currentItem = itemsInAuction.get(i);
			itemIDs[i] = currentItem.getID();
			itemInfo[i] = new NPContactItemInventoryGUI.ItemCell(currentItem.getID(), currentItem.getName(), currentItem.getMinBid(), currentItem.getQuantity(), currentItem.getCondition(), currentItem.getSize(), currentItem.getDescription());
		}
		myItemTableView.setItems(FXCollections.observableList(Arrays.asList(itemInfo)));
		
		if (itemIDs.length >= 1) {
			myItemChoice.setDisable(false);
			myItemChoice.setItems(FXCollections.observableArrayList(Arrays.asList(itemIDs)));
			myItemChoice.setValue(itemIDs[0]);
			myRemoveItemBtn.setDisable(false);
		} else {
			myItemChoice.setItems(FXCollections.observableArrayList(Arrays.asList(itemIDs)));
			myRemoveItemBtn.setDisable(true);
			myItemChoice.setDisable(true);
		}

	}
	
	/**
	 * Opens an error pop-up box.
	 * 
	 * @param theErrorMessage The message that the error pop-up will contain.
	 */
	private void showErrorMessage(String theErrorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Auction Central");
		alert.setHeaderText("Input Error");
		alert.setContentText(theErrorMessage);
		
		alert.showAndWait();
	}
}
