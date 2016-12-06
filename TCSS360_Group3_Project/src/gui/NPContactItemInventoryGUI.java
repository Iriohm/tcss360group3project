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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Auction;
import model.Bidder;
import model.Calendar;
import model.Item;
import model.NPContact;

/**
* This class is used to control the Item Inventory GUI for NPContact users.
*
* @author Vlad Kaganyuk
* @version 5 Dec 2016
*
*/
public class NPContactItemInventoryGUI implements Initializable {

	/** The back button on this GUI. */
	@FXML
	private Button myBackBtn;

	/** The add item button on this GUI. */
	@FXML
	private Button myAddItemBtn;

	/** The remove item button on this GUI. */
	@FXML
	private Button myRemoveItemBtn;

	/** The GUI option box that will hold the ID of the item to delete. */
	@FXML
	private ChoiceBox<String> myItemChoice;

	/** The GUI label that will show "Logged in as: " and the user's username. */
	@FXML
	private Label myUsernameLabel;

	/** The GUI table that will hold all of the item info. */
	@FXML
	private TableView<ItemCell> myItemTableView;

	/** The GUI table column that will hold all of the item IDs. */
	@FXML
	private TableColumn<ItemCell, String> myIDColumn;

	/** The GUI table column that will hold all of the item names. */
	@FXML
	private TableColumn<ItemCell, String> myNameColumn;

	/** The GUI table column that will hold all of the item minimum bids. */
	@FXML
	private TableColumn<ItemCell, String> myMinBidColumn;

	/** The GUI table column that will hold all of the item quantities. */
	@FXML
	private TableColumn<ItemCell, String> myQuantityColumn;

	/** The GUI table column that will hold all of the item conditions. */
	@FXML
	private TableColumn<ItemCell, String> myConditionColumn;

	/** The GUI table column that will hold all of the item sizes. */
	@FXML
	private TableColumn<ItemCell, String> mySizeColumn;

	/** The GUI table column that will hold all of the item descriptions. */
	@FXML
	private TableColumn<ItemCell, String> myDescriptionColumn;

	@FXML
	private Text myHeaderText;

	/** The GUI image box that will hold the logo. */
	@FXML
	private ImageView myLogoImageView;

	/** The Calendar where the auctions are stored. */
	private Calendar myCalendar;

	/** The current user, a NPContact. */
	private NPContact myNPContact;

	/** The previous GUI window. */
	private Stage myParentStage;

	/** The current GUI window. */
	private Stage myStage;

	/** The Auction whose items are currently being displayed. */
	private Auction myAuction;

	/**
	 * The constructor that is used by JavaFX.
	 *
	 * {@inheritDoc}
	 */
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

	/**
	 * Initializes all of the fields, works like a constructor.
	 *
	 * @param theParentStage The previous GUI window.
	 * @param theStage The current GUI window.
	 * @param theCalendar The Calendar where the auctions are stored.
	 * @param theNPContact The current user.
	 */
	public void initVariables(Stage theParentStage, Stage theStage, Calendar theCalendar, NPContact theNPContact) {
		myParentStage = theParentStage;
		myStage = theStage;
		myCalendar = theCalendar;
		myNPContact = theNPContact;

		tableSetup();
		String auctionName = myNPContact.getLatestAuction().getAuctionName();
		List<Auction> allAuctions = myCalendar.getAllAuctions();
		for (Auction currentAuction : allAuctions) {
			if (currentAuction.getAuctionName().equals(auctionName)) {
				myAuction = currentAuction;
				break;
			}
		}
		updateItemsInTable();

		myHeaderText.setText("Item Inventory for Auction: " + myAuction.getAuctionName());
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());

		setupAddItemBtn();
		setupRemoveItemBtn();
		setupBackBtn();
	}

	/**
	 * Defines what the add item button should do when it's clicked.
	 */
	private void setupAddItemBtn() {
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
	}

	/**
	 * Defines what the remove item button should do when it's clicked.
	 */
	private void setupRemoveItemBtn() {
		myRemoveItemBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				List<Item> itemsInAuction = myAuction.getItems();
				int itemToRemoveIndex = myItemChoice.getSelectionModel().getSelectedIndex();
				Item itemToRemove = itemsInAuction.get(itemToRemoveIndex);

				// Ensures the user won't be able to remove this item after the two-day cutoff period.
				// Added by Iriohm
				if	(!Bidder.hasAuctionPassedTwoDayCutoffPoint(itemToRemove)) {
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
						myAuction.removeItem(itemToRemove);
						updateItemsInTable();
					}
				} else {
					showErrorPopup();
				}

			}
		});
	}
	
	private void showErrorPopup() {
		Alert alertError = new Alert(AlertType.ERROR);
		alertError.setTitle("Auction Central");
		alertError.setHeaderText("Removal Error");
		alertError.setContentText("I'm sorry, but you cannot remove an item from an auction less than two days away.");
		alertError.showAndWait();
	}

	/**
	 * Defines what the back button should do when it's clicked.
	 */
	private void setupBackBtn() {
		myBackBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				myParentStage.show();
				myStage.hide();
			}
		});
	}

	/**
	 * Sets various values to assure that the table is properly setup.
	 */
	private void tableSetup() {
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
	}

	/**
	 * This method updates the item table on this GUI.
	 */
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

	/**
	 * Object that is used by the JavaFX TableView to define what each cell should contain.
	 *
	 * @author Vlad Kaganyuk
	 * @version Dec 5, 2016
	 */
	public static class ItemCell {
		private final SimpleStringProperty myID;
		private final SimpleStringProperty myName;
		private final SimpleStringProperty myMinBid;
		private final SimpleStringProperty myQuantity;
		private final SimpleStringProperty myCondition;
		private final SimpleStringProperty mySize;
		private final SimpleStringProperty myDescription;

		/**
		 * Initializes all the necessary fields.
		 *
		 * @param theID The item's ID.
		 * @param theName The item's name.
		 * @param theMinBid The item's minimum bid.
		 * @param theQuantity The item's quantity.
		 * @param theCondition The item's condition.
		 * @param theSize The item's size.
		 * @param theDescription The item's description.
		 */
		public ItemCell(String theID, String theName, double theMinBid, int theQuantity, String theCondition, String theSize, String theDescription) {
			myID = new SimpleStringProperty(theID);
			myName = new SimpleStringProperty(theName);
			myMinBid = new SimpleStringProperty("$" + String.format( "%.2f", theMinBid ));
			myQuantity = new SimpleStringProperty(Integer.toString(theQuantity));
			myCondition = new SimpleStringProperty(theCondition);
			mySize = new SimpleStringProperty(theSize);
			myDescription = new SimpleStringProperty(theDescription);
		}

		/**
		 * Simple getter for myID.
		 * @return Returns myID.
		 */
		public String getMyID() {
            return myID.get();
        }

		/**
		 * Simple getter for myName.
		 * @return Returns myName.
		 */
        public String getMyName() {
            return myName.get();
        }

        /**
		 * Simple getter for myMinBid.
		 * @return Returns myMinBid.
		 */
        public String getMyMinBid() {
            return myMinBid.get();
        }

        /**
		 * Simple getter for myQuantity.
		 * @return Returns myQuantity.
		 */
        public String getMyQuantity() {
            return myQuantity.get();
        }

        /**
		 * Simple getter for myCondition.
		 * @return Returns myCondition.
		 */
        public String getMyCondition() {
            return myCondition.get();
        }

        /**
		 * Simple getter for mySize.
		 * @return Returns mySize.
		 */
        public String getMySize() {
            return mySize.get();
        }

        /**
		 * Simple getter for myDescription.
		 * @return Returns myDescription.
		 */
        public String getMyDescription() {
            return myDescription.get();
        }
	}

}
