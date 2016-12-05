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
import javafx.stage.Stage;
import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class NPContactAddItemGUI implements Initializable {

	@FXML
	private Button mySubmitBtn;
	
	@FXML
	private Button myBackBtn;
	
	@FXML
	private TextField myNameBox;
	
	@FXML
	private TextField myConditionBox;
	
	@FXML
	private TextField myMinBidBox;
	
	@FXML
	private TextField myQuantityBox;
	
	@FXML
	private TextField myDescriptionBox;
	
	@FXML
	private Label myUsernameLabel;
	
	@FXML
	private ImageView myLogoImageView;
	
	private NPContact myNPContact;
	
	private Stage myParentStage;
	
	private Stage myStage;
	
	private Auction myAuction;
	
	private Calendar myCalendar;
	
	private TableView<NPContactItemInventoryGUI.ItemCell> myItemTableView;
	
	private Button myRemoveItemBtn;
	
	private ChoiceBox<String> myItemChoice;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitBtn != null : "fx:id=\"mySubmitBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myBackBtn != null : "fx:id=\"myBackBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myNameBox != null : "fx:id=\"myNameBox\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myConditionBox != null : "fx:id=\"myConditionBox\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myMinBidBox != null : "fx:id=\"myMinBidBox\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myQuantityBox != null : "fx:id=\"myQuantityBox\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myDescriptionBox != null : "fx:id=\"myDescriptionBox\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myUsernameLabel != null : "fx:id=\"myDescriptionBox\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myLogoImageView != null : "fx:id=\"myDescriptionBox\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";

		Image logo = new Image("file:logo2_v3.png");
		myLogoImageView.setImage(logo);
	}
	
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact, 
			TableView<NPContactItemInventoryGUI.ItemCell> theTableView, Button theRemoveBtn, ChoiceBox<String> theItemChoice) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		myAuction = myNPContact.getLatestAuction();
		myItemTableView = theTableView;
		myRemoveItemBtn = theRemoveBtn;
		myItemChoice = theItemChoice;
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		
		mySubmitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String itemName = myNameBox.getText();	
				String condition = myConditionBox.getText();	
				//String size = mySizeBox.getText();
				double minBid = Double.parseDouble(myMinBidBox.getText());
				int quantity =  Integer.parseInt(myQuantityBox.getText());
				String description = myDescriptionBox.getText();
				
				Item itemToAdd = new Item(myCalendar.getNextItemID() + "", itemName, description, "size", minBid, quantity, condition);
				
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
			itemInfo[i] = new NPContactItemInventoryGUI.ItemCell(currentItem.getID(), currentItem.getName(), currentItem.getMinBid(), currentItem.getQuantity(), currentItem.getCondition(), currentItem.getDescription());
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

}
