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


/**
* This class contains the GUI for staff members to view the upcoming month of auctions.
* A button will only exist on my calendar if there is an Auction on that date. Clicking 
* on said button will bring up auction info.
*
* @author Justin Washburn
* @version 30 Nov 2016
*
*/
public class ViewCalendarGUI {
	
	/**
	 * Days in a month, exists for displaying a desired amount of days on a Calendar
	 */
	private static final int DAYS_IN_GENERAL_MONTH = 31;
	
	/**
	 * Buttons per line on the grid
	 */
	private static final double BUTTONS_PER_LINE = 7;
	
	/**
	 * Number of rows on the grid
	 */
	private static final double NUM_BUTTON_LINES = 6;
	
	/**
	 * Vertical button padding on the grid
	 */
	private static final double BUTTON_PADDINGV = 5;
	
	/**
	 * horizontal button padding on the grid
	 */
	private static final double BUTTON_PADDINGH = 50;
	
	/**
	 * Array containing every month
	 */
	public static final String MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", 
			"September", "October", "November", "December"};
	
	/**
	 * Calendar and user list
	 */
	private static SerializeData myData;
	
	/**
	 * Current open window
	 */
	private static Stage myStage;
	
	/**
	 * Current user
	 */
	private static User myUser;
	
	/**
	 * Sets fields and calls the Calendar GUI builder
	 * @param theStage Current open window
	 * @param theData Calendar and user list
	 * @param theUser Current user
	 */
	public static void viewCalendar(Stage theStage, SerializeData theData, User theUser) {
		   myData = theData;
	       myStage = theStage;
	       myUser = theUser;
	       setupViewCalendar(theStage);

	       
	}
	
	/**
	 * Builds the Calendar viewer GUI
	 * @param theStage The current open window
	 */
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
	       
	      /**
	       * Goes back one scene
	       * 
	       */
	    backbtn.setOnAction(new EventHandler<ActionEvent>() {
	      /**
	       * Goes back one scene
	       * 
	       * @param e The button press that will send the user back one scene
	       */
	        @Override
	        public void handle(ActionEvent e) {
	     	   StaffGUI.setUpStaffGUI(myStage);
	        }             
	    });
	    
	    String days[] = {"Sunday",  "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	    //Add day labels
	    for (int c = 0; c < BUTTONS_PER_LINE; c++) {
	    	Label dayLabel = new Label(days[c]);
	    	dayLabel.setAlignment(Pos.CENTER);
	    	calendarGrid.add(dayLabel, c, 0);
	    }

	   
	    //Adds first row of calendar buttons and labels
	    GregorianCalendar tempDate;
	    int daysWithButtons = 0;
	    for (int c = 0; c < days.length; c++) {
	    	List<Auction> auctionsOnDay = new ArrayList<Auction>();
	    	//Equation accounts for the current row and column against the current date 
	    	tempDate = (GregorianCalendar)currentDate.clone();
	    	tempDate.add(GregorianCalendar.DAY_OF_MONTH, (c + 1 - currentDate.get(GregorianCalendar.DAY_OF_WEEK)));
	    	//Counts the number of auctions on the current day
			auctionsOnDay.clear();
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
	    		if (currentDate.get(GregorianCalendar.DAY_OF_WEEK) <= c + 1) {
	    			 daysWithButtons++;
	    			 Label dayLabel = new Label(tempDate.get(GregorianCalendar.DAY_OF_MONTH) + "");
	    			 calendarGrid.add(dayLabel, c, 1);
	    		} else {
	    			//Gets a label with the day of the month
	    			//    If you want the day shown: Label dayLabel = new Label(tempDate.get(GregorianCalendar.DAY_OF_MONTH) + "");
	    			Label dayLabel = new Label("");
	    			calendarGrid.add(dayLabel, c, 1);
	    		}
	    	}
	    }
	    //Adds the remaining buttons to the calendar
	    for (int r = 2; r < NUM_BUTTON_LINES + 1; r++) {
	    	for (int c = 0; c < BUTTONS_PER_LINE; c++) {
	    		List<Auction> auctionsOnDay = new ArrayList<Auction>();
	    		tempDate = (GregorianCalendar)currentDate.clone();
    			//Equation accounts for the current row and column against the current date 
    			tempDate.add(GregorianCalendar.DAY_OF_MONTH, (int)((r - 1) * BUTTONS_PER_LINE + c + 1 - currentDate.get(GregorianCalendar.DAY_OF_WEEK)));
	    		
    	    	//Counts the number of auctions on the current day
    			auctionsOnDay.clear();
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
	    			if (daysWithButtons < DAYS_IN_GENERAL_MONTH) {
	    					Label dayLabel = new Label(tempDate.get(GregorianCalendar.DAY_OF_MONTH) + "");
	    					daysWithButtons++;
	    					calendarGrid.add(dayLabel, c, r);
	    			} else {
	    				Label dayLabel = new Label("");
    					calendarGrid.add(dayLabel, c, r);
	    			}
	    			
	    		}
	    	}
	    }

	    Scene scene = new Scene(fullPane, 800, 400);
	    theStage.setScene(scene);
	    theStage.show();
	    
	}
	/**
	 * Sets up the button text and button action event if it has at least one auction on a given day
	 * @param theButton The button to modify
	 * @param theAuctionsOnDay The list of auctions associated with the given button
	 */
	private static void addCalendarButtonEvent(Button theButton, List<Auction> theAuctionsOnDay) {
		
		theButton.setText(theAuctionsOnDay.get(0).getDate().get(GregorianCalendar.DAY_OF_MONTH) + ":" + theAuctionsOnDay.size());
		
		theButton.setOnAction(new EventHandler<ActionEvent>() {
          	 /**
          	  * Adds a pop up stage with auction info for the given day
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
            	  Text scenetitle = new Text(theAuctionsOnDay.get(0).getDate().get(GregorianCalendar.DAY_OF_MONTH) + "/" 
                		  + (theAuctionsOnDay.get(0).getDate().get(GregorianCalendar.MONTH) + 1) + "/" 
                		  + theAuctionsOnDay.get(0).getDate().get(GregorianCalendar.YEAR));
                  scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                  grid.add(scenetitle, 0, 0, 2, 1);
                  for (int i = 0; i < theAuctionsOnDay.size(); i++) {

                	  Auction temp = theAuctionsOnDay.get(i);
/*                	  String ampm;
                	  if (temp.getDate().get(GregorianCalendar.AM_PM) == GregorianCalendar.AM) {
                		  ampm = "AM";
                	  } else {
                		  ampm = "PM";
                	  }*/
                      ScrollPane auctionInfo = new ScrollPane();
                      Text text = new Text(temp.getAuctionName() +
                    		  "\n" + temp.getDate().getTime() /*temp.getDate().get(GregorianCalendar.DAY_OF_MONTH) + "/" 
                    		  + (temp.getDate().get(GregorianCalendar.MONTH) + 1) + "/" 
                    		  + temp.getDate().get(GregorianCalendar.YEAR) + "  "
                    		  +  (temp.getDate().get(GregorianCalendar.HOUR)) + " "
                    		  + ampm*/
                    		  
                    		  + "\nEstimated Items: " +  temp.getItems().size()            //temp.getEstimatedItems()
                    		  + "\n" + temp.getComments());
                      text.wrappingWidthProperty().bind(scene.widthProperty());
                      auctionInfo.setFitToWidth(true);
                      auctionInfo.setContent(text);
                      grid.add(auctionInfo, 0, i + 1);
                  }
              }


          });
	}
}
