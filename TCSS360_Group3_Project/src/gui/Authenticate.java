package gui;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.application.Application;
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
* This class is used to begin the AuctionCentral environment.
*
* @author Justin Washburn
* @author Oracle tutorial http://docs.oracle.com/javafx/2/get_started/form.htm
* @version 30 Nov 2016
*
*/
public class Authenticate extends Application {

	public static final String CALENDAR_SER = "testCalendar.ser";
			
	private static  ArrayList<User> myListUser;
	private static Calendar myCalendar;
	private static Stage myStage;

	public static void beginUI(String[] args) {
		readUser();
		readCalendar();
		launch(args);
		
		
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
        	   AddUser.addUser(theStage, myListUser, myCalendar);
        	   
        	   
        	   
   
        	
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
            	actiontarget.setText("");
            	String userInput = userTextField.getText();
            	for (int i = 0; i < myListUser.size(); i++) {
            		if (userInput.equals(myListUser.get(i).getUsername())) {
            			if (myListUser.get(i).getClass() == Staff.class) {
            				StaffGUI.startStaffGUI(myStage, myListUser, myCalendar);
            			} else if (myListUser.get(i).getClass() == NPContact.class) {
            				//ToDO call NPUI
            			} else if (myListUser.get(i).getClass() == Bidder.class) {
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
        
    
    /**
     * Reads in the list of users from the .ser file
     */
    private static void readUser() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream("testUserList.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myListUser = (ArrayList<User>)in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Calender class not found");
	         c.printStackTrace();
	         return;
	      }
	}
    /**
     * Reads in the Calendar from the .ser file
     */
	private static void readCalendar() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream(CALENDAR_SER);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myCalendar = (Calendar) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Calender class not found");
	         c.printStackTrace();
	         return;
	      }
	}
}
