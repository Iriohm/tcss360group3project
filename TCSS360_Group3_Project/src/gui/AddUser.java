package gui;


import java.util.List;

import dataStorage.SerializeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Bidder;
import model.Calendar;
import model.NPContact;
import model.Staff;
import model.User;


/**
* This class contains the GUI for adding a user to AuctionCentral
*
* @author Justin Washburn
* @version 30 Nov 2016
*
*/
public class AddUser {

	/**
	 * The data for the calendar and list of users
	 */
	private static SerializeData myData;

	/**
	 * Starts up the object and sets its fields
	 * 
	 * @param theStage The current open window
	 * @param theData The data for the calendar and list of users
	 */
	public static void addUser(Stage theStage, SerializeData theData) {

		myData = theData;
		setupAddUser(theStage);
	}
	
	/**
	 * Builds the GUI and adds it to the given stage. Also creates button events
	 * @param primaryStage
	 */
	private static void setupAddUser(Stage primaryStage) {
		List<User> theListUser = myData.getUsers();
		Calendar theCalendar = myData.getCalendar();
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Text scenetitle = new Text("Hello, new user");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Desired user ID:");
        grid.add(userName, 0, 1);

		
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
        
 	    ObservableList<String> options = 
 			    FXCollections.observableArrayList(
 			        "Staff",
 			        "NPContact",
 			        "Bidder"
 			    );
 	   final ComboBox<String> userTypes = new ComboBox<String>(options);
 	   grid.add(userTypes, 1, 4);
 	  
 	   Label userType = new Label("User Type:");
 	   grid.add(userType, 0, 4);
 	  
       Button createNewUserbtn = new Button("Create User");
       HBox createNewUserhbBtn = new HBox(10);
       createNewUserhbBtn.setAlignment(Pos.BOTTOM_RIGHT);
       createNewUserhbBtn.getChildren().add(createNewUserbtn);
       grid.add(createNewUserhbBtn, 1, 5);
       
       Button backbtn = new Button("Back");
       HBox backhbBtn = new HBox(10);
       backhbBtn.setAlignment(Pos.BOTTOM_RIGHT);
       backhbBtn.getChildren().add(backbtn);
       grid.add(backhbBtn, 0, 5);
       
       final Text actiontarget = new Text();
       grid.add(actiontarget, 1, 6);
       
       /**
        * The back button returns you one screen
        */
       backbtn.setOnAction(new EventHandler<ActionEvent>() {
       	 /**
       	  * Goes back to the authenticate scene
       	  * 
       	  * @param e The button press that will send the user back to authenticate
       	  */
           @Override
           public void handle(ActionEvent e) {
        	   Authenticate.setupAuthenticate(primaryStage);
           }             
       });
       
       
       
       
       /**
        * Adds the user to the list of users as long as it is valid
        */
       createNewUserbtn.setOnAction(new EventHandler<ActionEvent>() {
         	 /**
         	  * Checks the user input against the current list of users and creates
         	  * a new user if it is not a duplicate
         	  * 
         	  * @param e The button press that will enable the new user gui
         	  */
             @Override
             public void handle(ActionEvent e) {
            	 String userID = userTextField.getText();
            	 String userType = userTypes.getValue();
            	 if (userType == null) {
            		  actiontarget.setFill(Color.FIREBRICK);
                      actiontarget.setText("Select user type");
                      return;
            	 }
            	 for (int i = 0; i < theListUser.size(); i++) {
            		 if (userID.equals(theListUser.get(i).getUsername())) {
                         actiontarget.setFill(Color.FIREBRICK);
                         actiontarget.setText("UserID already exists");
                         return;
            		 }
            	 }
            	 if (userID.equals("")) {
            		 actiontarget.setFill(Color.FIREBRICK);
                     actiontarget.setText("Enter UserID");
                     return;
            	 }
            	 //Create the correct type of user and add it to the user list
            	 User newUser;
            	 if (userType.equals("Staff")) {
            		 newUser = new Staff(userID, theCalendar);
            		 theListUser.add(newUser);
            		 Authenticate.setupAuthenticate(primaryStage);
            	 } else  if (userType.equals("NPContact")) {
            		 newUser = new NPContact(userID, theCalendar);
            		 theListUser.add(newUser);
            		 Authenticate.setupAuthenticate(primaryStage);
            	 } else  if (userType.equals("Bidder")) {
            		 newUser = new Bidder(userID, theCalendar);
            		 theListUser.add(newUser);
            		 Authenticate.setupAuthenticate(primaryStage);
            	 }
            	// myData.writeOutUserList();
             }             
         });
    }

}
