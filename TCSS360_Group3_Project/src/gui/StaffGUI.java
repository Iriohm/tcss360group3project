package gui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Calendar;
import model.User;

public class StaffGUI {
	
	private static List<User> myListUser;
	
	private static Calendar myCalendar;
	
	public static void startStaffGUI(Stage theStage, List<User> theUsers, Calendar theCalendar) {
		myListUser = theUsers;
		myCalendar = theCalendar;
		setUpStaffGUI(theStage);
	}
	

	public static void setUpStaffGUI(Stage primaryStage) {
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Text scenetitle = new Text("Welcome Auction Central Staff");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label changeMaxAuction = new Label("Change the number of max auctions");
        grid.add(changeMaxAuction, 0, 5);
        
        ObservableList<Integer> options = 
 			    FXCollections.observableArrayList();
        for (int i = 0; i <= Calendar.MAX_POSSIBLE_AUCTIONS; i++) {
        	options.add(i);
        }
       
 	    final ComboBox<Integer> userTypes = new ComboBox<Integer>(options);
 	    grid.add(userTypes, 1, 4);
        


	}
}
