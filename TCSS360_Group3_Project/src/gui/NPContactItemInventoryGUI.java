package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class NPContactItemInventoryGUI implements Initializable {

	@FXML
	private Button myBackBtn;
	
	@FXML
	private Button myAddItemBtn;
	
	@FXML
	private Button myRemoveItemBtn;
	
	@FXML
	private ChoiceBox<String> myItemChoice;
	
	@FXML
	private Label myUsernameLabel;
	
	@FXML
	private TableView<ItemCell> myItemTableView;
	
	@FXML
	private TableColumn<ItemCell, String> myIDColumn;
	
	@FXML
	private TableColumn<ItemCell, String> myNameColumn;
	
	@FXML
	private TableColumn<ItemCell, String> myMinBidColumn;
	
	@FXML
	private TableColumn<ItemCell, String> myQuantityColumn;
	
	@FXML
	private TableColumn<ItemCell, String> myConditionColumn;
	
	@FXML
	private TableColumn<ItemCell, String> mySizeColumn;
	
	@FXML
	private TableColumn<ItemCell, String> myDescriptionColumn;
	
	@FXML
	private Text myHeaderText;
	
	@FXML
	private ImageView myLogoImageView;
	
	private Calendar myCalendar;
	
	private NPContact myNPContact;
	
	private Stage myParentStage;
	
	private Stage myStage;
	
	private Auction myAuction;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert myBackBtn != null : "fx:id=\"myBackBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myAddItemBtn != null : "fx:id=\"myAddItemBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myRemoveItemBtn != null : "fx:id=\"myRemoveItemBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myItemChoice != null : "fx:id=\"myItemChoice\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myItemTableView != null : "fx:id=\"myItemTableView\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myIDColumn != null : "fx:id=\"myIDColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myNameColumn != null : "fx:id=\"myNameColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myMinBidColumn != null : "fx:id=\"myMinBidColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myQuantityColumn != null : "fx:id=\"myQuantityColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myConditionColumn != null : "fx:id=\"myConditionColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert mySizeColumn != null : "fx:id=\"mySizeColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myDescriptionColumn != null : "fx:id=\"myDescriptionColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myHeaderText != null : "fx:id=\"myHeaderText\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myLogoImageView != null : "fx:id=\"myLogoImageView\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";

		myItemTableView.setPlaceholder(new Label("There are currently no items in this auction."));
		Image logo = new Image("file:logo2_v3.png");
		myLogoImageView.setImage(logo);
	}
	
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		
		myIDColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("myID"));
		myNameColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("myName"));
		myMinBidColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("myMinBid"));
		myQuantityColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("myQuantity"));
		myConditionColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("myCondition"));
		mySizeColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("mySize"));
		myDescriptionColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("myDescription"));
		myIDColumn.setStyle( "-fx-alignment: CENTER;");
		myNameColumn.setStyle( "-fx-alignment: CENTER;");
		myMinBidColumn.setStyle( "-fx-alignment: CENTER;");
		myQuantityColumn.setStyle( "-fx-alignment: CENTER;");
		myConditionColumn.setStyle( "-fx-alignment: CENTER;");
		mySizeColumn.setStyle( "-fx-alignment: CENTER;");
		myDescriptionColumn.setStyle( "-fx-alignment: CENTER;");
		
		String auctionName = myNPContact.getLatestAuction().getAuctionName();
		List<Auction> allAuctions = myCalendar.getAllAuctions();
		for (Auction currentAuction : allAuctions) {
			if (currentAuction.getAuctionName().equals(auctionName)) {
				myAuction = currentAuction;
				break;
			}
		}
		
		updateItemsInTable();
		
		myHeaderText.setText("Item Inventory for Auction: " + myNPContact.getLatestAuction().getAuctionName());
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		
		myAddItemBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage addItemStage = new Stage();
				addItemStage.setTitle("Auction Central");
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactAddItemGUI.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactAddItemGUI ctrlAddItemGUI = fxmlLoader.<NPContactAddItemGUI>getController();

					ctrlAddItemGUI.initVariables(myStage, addItemStage, myCalendar, myAuction, myNPContact, myItemTableView, myRemoveItemBtn, myItemChoice);

					Scene scene = new Scene(root);
					addItemStage.setScene(scene);
					addItemStage.setResizable(false);
					myStage.hide();
					addItemStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
			}
		});
		
		myRemoveItemBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Auction theAuction = myNPContact.getLatestAuction();
				List<Item> itemsInAuction = myNPContact.getLatestAuction().getItems();
				int itemToRemoveIndex = myItemChoice.getSelectionModel().getSelectedIndex();
				Item itemToRemove = itemsInAuction.get(itemToRemoveIndex);
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Auction Central");
				alert.setHeaderText("Removing Item");
				alert.setContentText("Are you sure you would like to remove the following item? You will not be able to undo this action.\n\nItem Name: " + itemToRemove.getName()
						+ "\nCondition: " + itemToRemove.getCondition() + "\nMinimum Bid: $" + String.format( "%.2f", itemToRemove.getMinBid() ) + "\nQuantity: " + itemToRemove.getQuantity()
						+ "\nSize: " + itemToRemove.getSize() + "\nDescription: " + itemToRemove.getDescription() + "\n ");
				
				ButtonType yesBtn = new ButtonType("Yes");
				ButtonType noBtn = new ButtonType("No");

				alert.getButtonTypes().setAll(yesBtn, noBtn);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == yesBtn){
					theAuction.removeItem(itemToRemove);
					updateItemsInTable();
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
	
	private void updateItemsInTable() {
		List<Item> itemsInAuction = myAuction.getItems();
		ItemCell[] itemInfo = new ItemCell[itemsInAuction.size()];
		String[] itemIDs = new String[itemInfo.length];
		
		for (int i = 0; i < itemsInAuction.size(); i++) {
			Item currentItem = itemsInAuction.get(i);
			itemIDs[i] = currentItem.getID();
			itemInfo[i] = new ItemCell(currentItem.getID(), currentItem.getName(), currentItem.getMinBid(), currentItem.getQuantity(), currentItem.getCondition(), currentItem.getSize(), currentItem.getDescription());
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
	
	public static class ItemCell {
		private final SimpleStringProperty myID;
		private final SimpleStringProperty myName;
		private final SimpleStringProperty myMinBid;
		private final SimpleStringProperty myQuantity;
		private final SimpleStringProperty myCondition;
		private final SimpleStringProperty mySize;
		private final SimpleStringProperty myDescription;
		
		public ItemCell(String theID, String theName, double theMinBid, int theQuantity, String theCondition, String theSize, String theDescription) {
			myID = new SimpleStringProperty(theID);
			myName = new SimpleStringProperty(theName);
			myMinBid = new SimpleStringProperty("$" + String.format( "%.2f", theMinBid ));
			myQuantity = new SimpleStringProperty(Integer.toString(theQuantity));
			myCondition = new SimpleStringProperty(theCondition);
			mySize = new SimpleStringProperty(theSize);
			myDescription = new SimpleStringProperty(theDescription);
		}
		
		public String getMyID() {
            return myID.get();
        }
 
        public String getMyName() {
            return myName.get();
        }
 
        public String getMyMinBid() {
            return myMinBid.get();
        }
        
        public String getMyQuantity() {
            return myQuantity.get();
        }
        
        public String getMyCondition() {
            return myCondition.get();
        }
        
        public String getMySize() {
            return mySize.get();
        }
        
        public String getMyDescription() {
            return myDescription.get();
        }
	}

}
