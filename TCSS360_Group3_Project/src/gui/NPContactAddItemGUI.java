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

public class NPContactAddItemGUI implements Initializable {
	
	/**
	 * Different size options allowed for an item.
	 */
	private static final String[] SIZE_OPTIONS = {"Small", "Medium", "Large"};

	/**
	 * The submit button on this GUI.
	 */
	@FXML
	private Button mySubmitBtn;
	
	/**
	 * The back button on this GUI.
	 */
	@FXML
	private Button myBackBtn;
	
	/**
	 * The GUI text field that will hold the item's name.
	 */
	@FXML
	private TextField myNameBox;
	
	/**
	 * The GUI text field that will hold the item's condition.
	 */
	@FXML
	private TextField myConditionBox;
	
	/**
	 * The GUI text field that will hold the item's minimum bid.
	 */
	@FXML
	private TextField myMinBidBox;
	
	/**
	 * The GUI text field that will hold the item's quantity.
	 */
	@FXML
	private TextField myQuantityBox;
	
	/**
	 * The GUI option box that will hold the item's size.
	 */
	@FXML
	private ChoiceBox<String> mySizeChoiceBox;
	
	/**
	 * The GUI text field that will hold the item's description.
	 */
	@FXML
	private TextField myDescriptionBox;
	
	/**
	 * The GUI label that will show "Logged in as: " and the user's username.
	 */
	@FXML
	private Label myUsernameLabel;
	
	/**
	 * The GUI image box that will hold the logo.
	 */
	@FXML
	private ImageView myLogoImageView;
	
	/**
	 * The GUI label that will state which auction the user is adding an item to.
	 */
	@FXML
	private Text myHeaderText;
	
	/**
	 * The current user, a NPContact.
	 */
	private NPContact myNPContact;
	
	/**
	 * The previous GUI page.
	 */
	private Stage myParentStage;
	
	/**
	 * The current GUI page.
	 */
	private Stage myStage;
	
	/**
	 * The auction that items will be added to.
	 */
	private Auction myAuction;
	
	/**
	 * The Calendar .
	 */
	private Calendar myCalendar;
	
	private TableView<NPContactItemInventoryGUI.ItemCell> myItemTableView;
	
	private Button myRemoveItemBtn;
	
	private ChoiceBox<String> myItemChoice;
	
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
				
				double minBid = 0;
				try {
					minBid = Double.parseDouble(myMinBidBox.getText());
				} catch (NumberFormatException e) {
					showErrorMessage("Please make sure that your minimum bid amount is a valid number. NOTE: Do not include the $ sign with your amount.");
					return;
				}
				
				if (minBid <= 0.0) {
					showErrorMessage("Please make sure that your minimum bid amount is greater than $0.00");
					return;
				}
				
				int quantity =  0;
				try {
					quantity = Integer.parseInt(myQuantityBox.getText());
				} catch (NumberFormatException e) {
					showErrorMessage("Please make sure that the item quantity is a valid number.");
					return;
				}
				
				if (quantity <= 0) {
					showErrorMessage("Please make sure that the item quantity is greater than 0.");
					return;
				}
				
				Item itemToAdd = new Item(myCalendar.getNextItemID() + "", itemName, description, size, minBid, quantity, condition);
				
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
	
	private void showErrorMessage(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Auction Central");
		alert.setHeaderText("Input Error");
		alert.setContentText(errorMessage);
		
		alert.showAndWait();
	}
}
