package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dataStorage.SerializeData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	private Button myViewAuctionsBtn;
	
	/** The "Item inventory" button. */
	@FXML
	private Button myItemInvBtn;
	
	/** The "Log out" button. Pressing this should return the user to the login screen. */
	@FXML
	private Button myLogoutBtn;
	
	private List<User> myUserList;
	
	private Calendar myCalendar;
	
	private Stage myStage;
	
	private Stage myParentStage;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitAuctionRequestBtn != null : "fx:id=\"mySubmitAuctionRequestBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myViewAuctionsBtn != null : "fx:id=\"myViewAuctionsBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myItemInvBtn != null : "fx:id=\"myItemInvBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myLogoutBtn != null : "fx:id=\"myLogoutBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
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
				final Stage auctionRequestStage = new Stage();
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactAuctionRequestForm.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactAuctionRequestFormGUI ctrlAuctionRequestFormGUI = fxmlLoader.<NPContactAuctionRequestFormGUI>getController();

					//ctrlAuctionRequestFormGUI.sendData(myStage, myData.getUsers(), myData.getCalendar());

					Scene scene = new Scene(root);
					auctionRequestStage.setScene(scene);
					auctionRequestStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
			}
		});
		
		myViewAuctionsBtn.setOnAction(new EventHandler<ActionEvent>() {
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
				Authenticate.setupAuthenticate(myStage);
			}
		});

	}
	private class NPContactAuctionRequestFormGUI implements Initializable{
		@FXML
		private Button mySubmitBtn;
		
		@FXML
		private TextField myAuctionName;
		
		@FXML
		private TextField myAuctionDate;
		
		@FXML
		private TextField myAuctionTime;
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			assert mySubmitBtn != null : "fx:id=\"mySubmitBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
			
		}
	}
}
