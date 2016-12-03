package gui;

import java.util.GregorianCalendar;
import java.util.List;

import dataStorage.SerializeData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Auction;

public class ViewCalendarGUI {
	
	private static final double BUTTONS_PER_LINE = 7;
	private static final double NUM_BUTTON_LINES = 5;
	private static final double BUTTON_PADDINGV   = 5;
	private static final double BUTTON_PADDINGH   = 50;
	private static SerializeData myData;
	
	
	public static void viewCalendar(Stage theStage, SerializeData theData) {
		   myData = theData;
	       setupViewCalendar(theStage);
	       
	}
	
	public static void setupViewCalendar(Stage theStage) {


	    GregorianCalendar currentDate = (GregorianCalendar)GregorianCalendar.getInstance();
	    List<Auction> currentAuctions = myData.getCalendar().getAuctions(currentDate);
	    Text scenetitle = new Text("Month of Upcoming Auctions: (" + currentAuctions.size() + ")");
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
	    
	    String days[] = {"Sunday",  "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	    
	    for (int c = 0; c < BUTTONS_PER_LINE; c++) {
	    	Label dayLabel = new Label(days[c]);
	    	dayLabel.setAlignment(Pos.CENTER);
	    	calendarGrid.add(dayLabel, c, 0);
	    }


	    GregorianCalendar tempDate;
	    
	    for (int c = 0; c < days.length; c++) {
	    	if (currentDate.get(GregorianCalendar.DAY_OF_WEEK) <= c + 1){
	    		Button button = new Button(days[c]);
	    		//on first row
	    		calendarGrid.add(button, c, 1);
	    	} else {
	    		tempDate = (GregorianCalendar)currentDate.clone();
	    		tempDate.add(GregorianCalendar.DAY_OF_MONTH, (c + 1 - currentDate.get(GregorianCalendar.DAY_OF_WEEK)));
	    		//Gets a label with the day of the month
	    		Label dayLabel = new Label(tempDate.get(GregorianCalendar.DAY_OF_MONTH) + "");
	    		calendarGrid.add(dayLabel, c, 1);
	    	}
	    }
	    
	    for (int r = 2; r < NUM_BUTTON_LINES + 1; r++) {
	    	for (int c = 0; c < BUTTONS_PER_LINE; c++) {
	    		Button button = new Button(r + ":" + c);
	    		calendarGrid.add(button, c, r);
	    	}
	    }

	       
	       /*Text calendarText = new Text(calendarSB.toString());
	       calendarText.wrappingWidthProperty().bind(scene.widthProperty());
	       calendarPane.setFitToWidth(true);
	       calendarPane.setContent(calendarText);*/

	    Scene scene = new Scene(fullPane, 600, 400);
	    theStage.setScene(scene);
	    theStage.show();
	    
	}
}
