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
	private ChoiceBox<Integer> myItemChoice;
	
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
	private TableColumn<ItemCell, String> myDescriptionColumn;
	
	private Calendar myCalendar;
	
	private NPContact myNPContact;
	
	private Stage myParentStage;
	
	private Stage myStage;
	
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
		assert myDescriptionColumn != null : "fx:id=\"myDescriptionColumn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";

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
		myDescriptionColumn.setCellValueFactory(new PropertyValueFactory<ItemCell, String>("myDescription"));
		
		updateItemsInTable();
		
		//myItemTableView.setItems(FXCollections.observableList(itemsInAuction));
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		
		myAddItemBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Auction yo = myNPContact.getLatestAuction();
				yo.addItem(new Item("2", "Yo-yo", "Brand-new", "Small", 10.00, 1, "New"));
				
				System.out.println(yo.getItems().get(0));
				updateItemsInTable();
				return;
				
				/**final Stage itemInventoryStage = new Stage();
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactItemInventoryGUI.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactItemInventoryGUI ctrlItemInventoryGUI = fxmlLoader.<NPContactItemInventoryGUI>getController();

					//ctrlItemInventoryGUI.initVariables(myStage, auctionRequestStage, myCalendar, myNPContact);

					Scene scene = new Scene(root);
					itemInventoryStage.setScene(scene);
					myStage.hide();
					itemInventoryStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
				**/
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
	
	private void updateItemsInTable() {
		List<Item> itemsInAuction = myNPContact.getLatestAuction().getItems();
		ItemCell[] itemInfo = new ItemCell[itemsInAuction.size()];
		
		for (int i = 0; i < itemsInAuction.size(); i++) {
			Item currentItem = itemsInAuction.get(i);
			itemInfo[i] = new ItemCell(currentItem.getID(), currentItem.getName(), currentItem.getMinBid(), currentItem.getQuantity(), currentItem.getCondition(), currentItem.getDescription());
		}
		myItemTableView.setItems(FXCollections.observableList(Arrays.asList(itemInfo)));
	}
	
	public class ItemCell {
		private final SimpleStringProperty myID;
		private final SimpleStringProperty myName;
		private final SimpleStringProperty myMinBid;
		private final SimpleStringProperty myQuantity;
		private final SimpleStringProperty myCondition;
		private final SimpleStringProperty myDescription;
		
		public ItemCell(String theID, String theName, double theMinBid, int theQuantity, String theCondition, String theDescription) {
			myID = new SimpleStringProperty(theID);
			myName = new SimpleStringProperty(theName);
			myMinBid = new SimpleStringProperty("$" + Double.toString(theMinBid));
			myQuantity = new SimpleStringProperty(Integer.toString(theQuantity));
			myCondition = new SimpleStringProperty(theCondition);
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
        
        public String getMyDescription() {
            return myDescription.get();
        }
	}

}
