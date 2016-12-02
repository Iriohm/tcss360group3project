package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.html.ListView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BidderGUI implements Initializable {

	@FXML
	private Button myLogOutButton;
//	
//	@FXML
//	private Text myUserNameDisplay;
//	
	@FXML
	private Text myFeedbackText;
//	
//	@FXML
//	private Button myPlaceBidButton;
//	
//	@FXML
//	private Button myCancelBidButton;
//	
//	@FXML
//	private TextField myPlaceBidField;
//	
//	@FXML
//	private ComboBox myAuctionDropDown;
//	
//	@FXML
//	private ListView myItemDetails;
//	
//	@FXML
//	private ListView myItemList;
	
	
	
	@Override
	public void initialize(URL theURL, ResourceBundle theResources) {
		assert myLogOutButton != null : "fx:id=\"myLogOutButton\" was not injected: check your FXML file 'Bidder.fxml'.";
//		assert myUserNameDisplay != null : "fx:id=\"myUserNameDisplay\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myFeedbackText != null : "fx:id=\"myFeedbackText\" was not injected: check your FXML file 'Bidder.fxml'.";
//		assert myPlaceBidButton != null : "fx:id=\"myPlaceBidButton\" was not injected: check your FXML file 'Bidder.fxml'.";
//		assert myCancelBidButton != null : "fx:id=\"myCancelBidButton\" was not injected: check your FXML file 'Bidder.fxml'.";
//		assert myPlaceBidField != null : "fx:id=\"myPlaceBidField\" was not injected: check your FXML file 'Bidder.fxml'.";
//		assert myAuctionDropDown != null : "fx:id=\"myAuctionDropDown\" was not injected: check your FXML file 'Bidder.fxml'.";
//		assert myItemDetails != null : "fx:id=\"myItemDetails\" was not injected: check your FXML file 'Bidder.fxml'.";
//		assert myItemList != null : "fx:id=\"myItemList\" was not injected: check your FXML file 'Bidder.fxml'.";
		
		myLogOutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				myFeedbackText.setText("Success!");
				
			}
			
			
		});
		
	}
	
	

}
