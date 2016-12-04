package gui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import dataStorage.SerializeData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Auction;
import model.User;

public class ViewCalendarGUI {
	private static final int DAYS_IN_GENERAL_MONTH = 31;
	private static final double BUTTONS_PER_LINE = 7;
	private static final double NUM_BUTTON_LINES = 6;
	private static final double BUTTON_PADDINGV = 5;
	private static final double BUTTON_PADDINGH = 50;
	public static final String MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", 
			"September", "October", "November", "December"};
	private static SerializeData myData;
	private static Stage myStage;
	private static User myUser;
	
	
	public static void viewCalendar(Stage theStage, SerializeData theData, User theUser) {
		   myData = theData;
	       myStage = theStage;
	       myUser = theUser;
	       setupViewCalendar(theStage);

	       
	}
	
	public static void setupViewCalendar(Stage theStage) {


	    GregorianCalendar currentDate = (GregorianCalendar)GregorianCalendar.getInstance();
	    List<Auction> currentAuctions = myData.getCalendar().getAuctions(currentDate);
	    Text scenetitle = new Text("Month of Upcoming Auctions: (" + currentAuctions.size() + ")\n" +
	    		"      Starting in: " + MONTHS[currentDate.get(GregorianCalendar.MONTH)]);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

	    
	    GridPane calendarGrid = new GridPane();
	    calendarGrid.setPadding(new Insets(BUTTON_PADDINGV));
	    calendarGrid.setHgap(BUTTON_PADDINGH);
	    calendarGrid.setVgap(BUTTON_PADDINGV);
	    calendarGrid.setAlignment(Pos.CENTER);
	    calendarGrid.setPadding(new Insets(25, 25, 25, 25));
	    
	    BorderPane fullPane = new BorderPane();
	    BorderPane.setAlignment(scenetitle, Pos.CENTER);
	    BorderPane.setMargin(scenetitle, new Insets(12,12,12,12));
	    fullPane.setTop(scenetitle);
	    fullPane.setCenter(calendarGrid);
	    
	    Button backbtn = new Button("Back");
	    HBox backhbBtn = new HBox(10);
	    backhbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	    backhbBtn.getChildren().add(backbtn);
	    BorderPane.setMargin(backhbBtn, new Insets(12,12,12,12));
	    fullPane.setBottom(backhbBtn);
	       
	       
	    backbtn.setOnAction(new EventHandler<ActionEvent>() {
	      /**
	       * Goes back to the authenticate scene
	       * 
	       * @param e The button press that will send the user back to authenticate
	       */
	        @Override
	        public void handle(ActionEvent e) {
	     	   StaffGUI.setUpStaffGUI(myStage);
	        }             
	    });
	    
	    String days[] = {"Sunday",  "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	    
	    for (int c = 0; c < BUTTONS_PER_LINE; c++) {
	    	Label dayLabel = new Label(days[c]);
	    	dayLabel.setAlignment(Pos.CENTER);
	    	calendarGrid.add(dayLabel, c, 0);
	    }

	    List<Auction> auctionsOnDay = new ArrayList<Auction>();

	    GregorianCalendar tempDate;
	    int daysWithButtons = 0;
	    for (int c = 0; c < days.length; c++) {
	    	//Equation accounts for the current row and column against the current date 
	    	tempDate = (GregorianCalendar)currentDate.clone();
	    	tempDate.add(GregorianCalendar.DAY_OF_MONTH, (c + 1 - currentDate.get(GregorianCalendar.DAY_OF_WEEK)));
	    	//Counts the number of auctions on the current day
			for (int i = 0; i < currentAuctions.size(); i++) {
				if (tempDate.get(GregorianCalendar.DAY_OF_YEAR) == currentAuctions.get(i).getDate().get(GregorianCalendar.DAY_OF_YEAR)) {
					auctionsOnDay.add(currentAuctions.get(i));

				}
			}
	    	if (currentDate.get(GregorianCalendar.DAY_OF_WEEK) <= c + 1 && auctionsOnDay.size() > 0){
	    		daysWithButtons++;
	    		Button button = new Button();
	    		//on first row
	    		calendarGrid.add(button, c, 1);
	    		addCalendarButtonEvent(button, auctionsOnDay);
	    	} else {
	    		//Gets a label with the day of the month
	    		Label dayLabel = new Label(tempDate.get(GregorianCalendar.DAY_OF_MONTH) + "");
	    		calendarGrid.add(dayLabel, c, 1);
	    	}
	    }
	    
	    for (int r = 2; r < NUM_BUTTON_LINES + 1; r++) {
	    	for (int c = 0; c < BUTTONS_PER_LINE; c++) {
	    		tempDate = (GregorianCalendar)currentDate.clone();
    			//Equation accounts for the current row and column against the current date 
    			tempDate.add(GregorianCalendar.DAY_OF_MONTH, (int)((r - 1) * BUTTONS_PER_LINE + c + 1 - currentDate.get(GregorianCalendar.DAY_OF_WEEK)));
	    		
    	    	//Counts the number of auctions on the current day
    			for (int i = 0; i < currentAuctions.size(); i++) {
    				if (tempDate.get(GregorianCalendar.DAY_OF_YEAR) == currentAuctions.get(i).getDate().get(GregorianCalendar.DAY_OF_YEAR)) {
    					auctionsOnDay.add(currentAuctions.get(i));

    				}
    			}
    			
    			if (daysWithButtons < DAYS_IN_GENERAL_MONTH && auctionsOnDay.size() > 0) {
	    			daysWithButtons++;
	    			Button button = new Button();
	    			addCalendarButtonEvent(button, auctionsOnDay);
	    			calendarGrid.add(button, c, r);
	    		} else {
	    			Label dayLabel = new Label(tempDate.get(GregorianCalendar.DAY_OF_MONTH) + "");
	    			calendarGrid.add(dayLabel, c, r);
	    		}
	    	}
	    }

	       
	       /*Text calendarText = new Text(calendarSB.toString());
	       calendarText.wrappingWidthProperty().bind(scene.widthProperty());
	       calendarPane.setFitToWidth(true);
	       calendarPane.setContent(calendarText);*/

	    Scene scene = new Scene(fullPane, 800, 400);
	    theStage.setScene(scene);
	    theStage.show();
	    
	}
	
	private static void addCalendarButtonEvent(Button theButton, List<Auction> theAuctionsOnDay) {
		
		theButton.setText(theAuctionsOnDay.get(0).getDate().get(GregorianCalendar.DAY_OF_MONTH) + ":" + theAuctionsOnDay.size());
		
		theButton.setOnAction(new EventHandler<ActionEvent>() {
          	 /**
          	  * Sends the user to the gui to add a new user
          	  *
          	  * @param e The button press that will enable the new user gui
          	  */
              @Override
              public void handle(ActionEvent e) {
            	  final Stage auctionView = new Stage();
            	  auctionView.initModality(Modality.APPLICATION_MODAL);
            	  auctionView.initOwner(myStage);
            	  auctionView.setTitle("Auction Central - Auction View");
            	  GridPane grid = new GridPane();
                  grid.setAlignment(Pos.CENTER);
                  grid.setHgap(10);
                  grid.setVgap(10);
                  grid.setPadding(new Insets(25, 25, 25, 25));
                  Scene scene = new Scene(grid, 500, 275);
                  auctionView.setScene(scene);
            	  auctionView.show();
                  for (int i = 0; i < theAuctionsOnDay.size(); i++) {
                	  Auction temp = theAuctionsOnDay.get(i);
                      ScrollPane auctionInfo = new ScrollPane();
                      Text text = new Text(temp.getAuctionName() + "\n" + temp.getDate().getTime() 
                    		  + "\nEstimated Items: " + temp.getEstimatedItems()
                    		  + "\n" + temp.getComments());
                      text.wrappingWidthProperty().bind(scene.widthProperty());
                      auctionInfo.setFitToWidth(true);
                      auctionInfo.setContent(text);
                      grid.add(auctionInfo, 0, i);
                  }
              }


          });
	}
}
