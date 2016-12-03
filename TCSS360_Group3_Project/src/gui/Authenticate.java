package gui;




import java.util.ArrayList;
import java.util.List;

import dataStorage.SerializeData;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Bidder;
import model.Calendar;
import model.NPContact;
import model.Staff;
import model.User;


/**
* This class is used to begin the AuctionCentral GUi and authenticate the users credentials.
*
* @author Justin Washburn
* @version 30 Nov 2016
*
*/
public class Authenticate extends Application {


			
	private static Stage myStage;
	private static SerializeData myData;

	public static void beginUI(String[] args, SerializeData theData) {
		myData = theData;
		
		launch(args);
		
		
		List<User> myListUser = myData.getUsers();
		System.out.println("\nWhich user are you?");
		System.out.printf("%-15s%-20s%-10s\n", "Index", "Username", "Type of User");

		for (int i = 0; i < myListUser.size(); i++) {
			System.out.printf("%-15s%-20s%-10s\n", (i+1) + ")", myListUser.get(i).getUsername(),myListUser.get(i).getClass().toString());
		}
		
	}
    @Override
    public void start(Stage primaryStage) {
    	myStage = primaryStage;
        primaryStage.setTitle("Auction Central");
        
        /**
         * Write out data on window close
         */
        myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                myData.writeOutCalendar();
                myData.writeOutUserList();
            }
        });        
        setupAuthenticate(myStage);
    }
    
    public static void setupAuthenticate(Stage theStage) {
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        theStage.setScene(scene);
        theStage.show();
        
        Text scenetitle = new Text("Welcome to Auction Central");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User ID:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);


        Button loginbtn = new Button("Login");
        HBox loginhbBtn = new HBox(10);
        loginhbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        loginhbBtn.getChildren().add(loginbtn);
        grid.add(loginhbBtn, 1, 4);
        
        
        Button newUserbtn = new Button("New User");
        HBox newUserhbBtn = new HBox(10);
        newUserhbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        newUserhbBtn.getChildren().add(newUserbtn);
        grid.add(newUserhbBtn, 1, 5);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        newUserbtn.setOnAction(new EventHandler<ActionEvent>() {
       	 /**
       	  * Sends the user to the gui to add a new user
       	  * 
       	  * @param e The button press that will enable the new user gui
       	  */
           @Override
           public void handle(ActionEvent e) {
        	   //Starts by getting rid of the old buttons
         	  /* grid.getChildren().remove(newUserhbBtn);
         	   grid.getChildren().remove(loginhbBtn);
         	   grid.getChildren().remove(userTextField);
         	   grid.getChildren().remove(actiontarget);*/
         	   //scenetitle.setText("Hello, New User!");
        	  // userName.setText("Desired user ID");
        	   AddUser.addUser(theStage, myData);
        	   
        	   
        	   
   
        	
           }
           
           
       });

        
        
        loginbtn.setOnAction(new EventHandler<ActionEvent>() {
        	 /**
        	  * Checks the info given by the user against the list of known users and 
        	  * either makes them try again or directs them to the appropriate gui
        	  * 
        	  * @param e The button press that will enable the login attempt
        	  */
            @Override
            public void handle(ActionEvent e) {
        		List<User> theListUser = myData.getUsers();
            	actiontarget.setText("");
            	String userInput = userTextField.getText();
            	for (int i = 0; i < theListUser.size(); i++) {
            		if (userInput.equals(theListUser.get(i).getUsername())) {
            			if (theListUser.get(i).getClass() == Staff.class) {
            				StaffGUI.startStaffGUI(myStage, theListUser.get(i), myData);
            				myStage.setTitle("Auction Central - " + userInput);
            			} else if (theListUser.get(i).getClass() == NPContact.class) {
            				//ToDO call NPUI
            				myStage.setTitle("Auction Central - " + userInput);
            			} else if (theListUser.get(i).getClass() == Bidder.class) {
            				myStage.setTitle("Auction Central - " + userInput);
            				//ToDO call BidderUI
            			}
            			
            			return;
            		}
            	}
            	//if not a valid login name
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Invalid Login");
            }
        });
    }
        
 
}
