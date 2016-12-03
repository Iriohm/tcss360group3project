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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Calendar;
import model.User;

public class StaffGUI {
	
	private static User myUser;
	
	private static SerializeData myData;
	
	private static Calendar myCalendar;
	
	public static void startStaffGUI(Stage theStage, User theUser, SerializeData theData) {
		myUser = theUser;
		myData = theData;
		myCalendar = myData.getCalendar();
		
		if (myCalendar == null) {
			myCalendar = new Calendar();
		}
		setUpStaffGUI(theStage);
	}
	

	public static void setUpStaffGUI(Stage primaryStage) {
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Text scenetitle = new Text("Welcome Auction Central Staff, Current Upcoming Auctions: " + myCalendar.getUpcomingAuctionsNumber());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label changeMaxAuction = new Label("Max auctions possible");
        grid.add(changeMaxAuction, 0, 4);
        
        ObservableList<Integer> options = 
 			    FXCollections.observableArrayList();
        for (int i = 1; i <= Calendar.MAX_POSSIBLE_AUCTIONS; i++) {
        	options.add(i);
        }
       
 	    final ComboBox<Integer> maxAuctionsBox = new ComboBox<Integer>(options);
 	    maxAuctionsBox.setValue(myCalendar.getMaxAuctionsLimit());

 	    HBox maxAuctionsbx = new HBox(10);
 	    maxAuctionsbx.setAlignment(Pos.BOTTOM_RIGHT);
 	    maxAuctionsbx.getChildren().add(maxAuctionsBox);
 	    grid.add(maxAuctionsbx, 1, 4);
 	    
        
 	    
 	   Button submitMaxAuctions = new Button("Update");
       HBox submitMaxAuctionshbBtn = new HBox(10);
       submitMaxAuctionshbBtn.setAlignment(Pos.BOTTOM_RIGHT);
       submitMaxAuctionshbBtn.getChildren().add(submitMaxAuctions);
       grid.add(submitMaxAuctionshbBtn, 1, 5);
       
       final Text actiontarget = new Text();
       grid.add(actiontarget, 1, 6);
       
       submitMaxAuctions.setOnAction(new EventHandler<ActionEvent>() {
      	 /**
      	  * Attempts to set the new number of max auctions
      	  * 
      	  * @param e The button press that will try to submit the new number
      	  */
          @Override
          public void handle(ActionEvent e) {
        	  int newMaxAuctions = maxAuctionsBox.getValue();
        	  if (myCalendar.setMaxAuctionsLimit(newMaxAuctions) == 0) {
        		  actiontarget.setFill(Color.GREEN);
                  actiontarget.setText("Succesfully updated");
                  //Serialize?
                  
                
        	  } else {
        		  actiontarget.setFill(Color.FIREBRICK);
                  actiontarget.setText("You already have " + myCalendar.getUpcomingAuctionsNumber() + " schduled");
        	  }
          }
 
      });

	}
}
