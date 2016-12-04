package gui;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Auction;
import model.Calendar;
import model.NPContact;

public class NPContactAuctionRequestFormGUI implements Initializable {
	@FXML
	private Button mySubmitBtn;
	
	@FXML
	private TextField myAuctionName;
	
	@FXML
	private TextField myAuctionDate;
	
	@FXML
	private TextField myAuctionTime;
	
	private Calendar myCalendar;
	
	private NPContact myNPContact;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitBtn != null : "fx:id=\"mySubmitBtn\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myAuctionName != null : "fx:id=\"myAuctionName\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myAuctionDate != null : "fx:id=\"myAuctionDate\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";
		assert myAuctionTime != null : "fx:id=\"myAuctionTime\" was not injected: check your FXML file 'NPContactAuctionRequestFormGUI.fxml'.";

	}
	
	public void initVariables(Calendar theCalendar, NPContact theNPContact) {
		myCalendar = theCalendar;
		myNPContact = theNPContact;
		
		mySubmitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GregorianCalendar auctionDateGregCalendar = new GregorianCalendar();
				
				String auctionName = myAuctionName.getText();
				String auctionDate = myAuctionDate.getText();
				String auctionTime = myAuctionTime.getText();
				
				//test valid date
				//test valid time.
				String[] date = auctionDate.split("/");
				String[] time = auctionTime.split(" ");
				
				int hour = 0;
				try {
					hour = Integer.parseInt(time[0]);
				} catch (NumberFormatException e) {
					System.out.println("We encountered an error with your time input, and must restart auction date input.\n");
					//alert
					return;
				}

				if (time[1].equals("PM")) {
					hour += 12;
				}
				
				int year = Integer.parseInt(date[2]);
				int month = Integer.parseInt(date[1]);
				int day = Integer.parseInt(date[0]);
				System.out.println(year + " " + month + " " + day);
				auctionDateGregCalendar.set(year, month, day, hour, 0, 0);
				//I have no idea why
				auctionDateGregCalendar.add(GregorianCalendar.MONTH, -1);
				
				Auction auctionRequest = new Auction(auctionDateGregCalendar, auctionName);
				myCalendar.addAuction(auctionRequest);
				myNPContact.addAuction(auctionRequest);
			}
		});
	}

}
