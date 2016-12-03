package gui;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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

public class AddUser {


	private static List<User> myListUser;
	private static Calendar myCalendar;

	public static void addUser(Stage theStage, List<User> theListUser, Calendar theCalendar) {

		myListUser = theListUser;
		myCalendar = theCalendar;
		setupAddUser(theStage);
	}
	
	private static void setupAddUser(Stage primaryStage) {
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
            	 for (int i = 0; i < myListUser.size(); i++) {
            		 if (userID.equals(myListUser.get(i).getUsername())) {
                         actiontarget.setFill(Color.FIREBRICK);
                         actiontarget.setText("UserID already exists");
                         return;
            		 }
            	 }

            	 User newUser;
            	 if (userType.equals("Staff")) {
            		 newUser = new Staff(userID, myCalendar);
            		 myListUser.add(newUser);
            		 Authenticate.setupAuthenticate(primaryStage);
            	 } else  if (userType.equals("NPContact")) {
            		 newUser = new NPContact(userID, myCalendar);
            		 myListUser.add(newUser);
            		 Authenticate.setupAuthenticate(primaryStage);
            	 } else  if (userType.equals("Bidder")) {
            		 newUser = new Bidder(userID, myCalendar);
            		 myListUser.add(newUser);
            		 Authenticate.setupAuthenticate(primaryStage);
            	 }
            	// myData.writeOutUserList();
             }             
         });
    }

}
