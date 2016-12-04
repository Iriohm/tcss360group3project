package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dataStorage.SerializeData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Calendar;
import model.User;

public class NPContactGUI implements Initializable {
	
	/** The "Submit auction request" button. */
	@FXML
	private Button mySubmitAuctionRequestBtn;
	
	/** The "Submit auction request" button. */
	@FXML
	private Button myAddItemBtn;
	
	/** The "Item inventory" button. */
	@FXML
	private Button myItemInvBtn;
	
	/** The "Log out" button. Pressing this should return the user to the login screen. */
	@FXML
	private Button myLogoutBtn;
	
	private List<User> myUserList;
	
	private Calendar myCalendar;
	
	private Stage myStage;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitAuctionRequestBtn != null : "fx:id=\"mySubmitAuctionRequestBtn\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myAddItemBtn != null : "fx:id=\"myAddItemBtn\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myItemInvBtn != null : "fx:id=\"myItemInvBtn\" was not injected: check your FXML file 'Bidder.fxml'.";
		assert myLogoutBtn != null : "fx:id=\"myLogoutBtn\" was not injected: check your FXML file 'Bidder.fxml'.";
	}
	
	//My constructor...
	public void sendData(Stage theStage, List<User> theUserList, Calendar theCalendar) {
		myStage = theStage;
		myUserList = theUserList;
		myCalendar = theCalendar;
		initalizeBtns();
	}
	
	private void initalizeBtns() {
		mySubmitAuctionRequestBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(myStage);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("This is a Dialog"));
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
			}
		});
		
		myLogoutBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//((Node)(event.getSource())).getScene().hide();
			}
		});

	}
}
