package gui;

import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class GuiLoader extends Application {
	private Calendar myCalendar;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Bidlord's Magical Auction GUI");

		prepareTest();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Bidder.fxml"));
			Parent root = (Parent)fxmlLoader.load();
			BidderGUI ctrlBidderGUI = fxmlLoader.<BidderGUI>getController();

			// Insert master Calendar here.
			ctrlBidderGUI.setBidder(new Bidder("Iriohm", myCalendar));

			ctrlBidderGUI.initAll();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}


	private void prepareTest() {
		myCalendar = new Calendar();
		Auction a1 = new Auction(new GregorianCalendar(2016, 11, 10), "Lady's Auction");
		Auction a2 = new Auction(new GregorianCalendar(2016, 11, 15), "Gentleman's Auction");

		a1.addItem(new Item("000", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine"));
		a1.addItem(new Item("001", "Monocle", "A rather nice monocle.", "Small", 3.0, 1, "Very Fine"));
		a1.addItem(new Item("002", "Posh Suit", "A posh suit.", "Small", 6.0, 1, "Very Fine"));
		a2.addItem(new Item("003", "Beautiful Dress", "A beautiful dress.", "Small", 7.0, 1, "Very Fine"));
		a2.addItem(new Item("004", "Pretty Hat", "An extremely pretty hat.", "Small", 1.0, 1, "Very Fine"));
		a2.addItem(new Item("005", "Nice Jewelry", "Some nice jewelry.", "Small", 9.0, 1, "Very Fine"));

		myCalendar.addAuction(a1);
		myCalendar.addAuction(a2);

	}

}
