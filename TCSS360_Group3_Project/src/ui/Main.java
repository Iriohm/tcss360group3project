package ui;

import java.util.GregorianCalendar;

import model.Calendar;

/**
* This class is used to begin the AuctionCentral environment.
*
* @author Vlad Kaganyuk
* @version 8 Nov 2016
*
*/
public class Main {
	private Calendar myCalendar;

	/**
	* This method loads Calendar info, authenticates a User,
	* and then starts a UI Controller/Manager.
    */
	public static void main(String[] args) {
		Authenticate.beginUI(args);
		//readsInASerializedCalendarFromFile
		//authenticate();
		//starts specific UI Controller.
	//UI.beginUI(); // this start the UI then gose to the right UI depend on user type

		
//		UI.beginUI(); // this start the UI then gose to the right UI depend on user type?
		
//		NPContact x = new NPContact("jsmith", new Calendar());
//		UINPContact.beginNPContactUI(x, x.getCalendar());
		
//		Calendar exampleCalendar = new Calendar();
//		Auction koolAuction = new Auction(new GregorianCalendar(2016, 11, 22, 13, 0), "PizzaPalace");
//		NPContact x = new NPContact("jsmith", exampleCalendar);
//		x.addAuction(koolAuction);
//		UINPContact.beginNPContactUI(x, x.getCalendar());

		//NPContact x = new NPContact("jsmith", new Calendar());
		//UINPContact.beginNPContactUI(x, x.getCalendar());
//		UI.beginUI();

		//doing a test on the UIBidder.
//		Item football = new Item("1", "football", "the best football", "Small", 20.0, 1, "Very Fine");
//		Item baseball = new Item("2", "baseball", "the best baseball", "Small", 20.0, 1, "Very Fine");
//		Auction testAuction = new Auction((GregorianCalendar)GregorianCalendar.getInstance(), "testAcution");
//		testAuction.addItem(football);
//		testAuction.addItem(baseball);
//		Calendar testCalendar = new Calendar();
//		testCalendar.addAuction(testAuction);
//		UIBidder.beginBidderUI(new Bidder("davidTest", testCalendar), testCalendar);
//		UIStaff.beginStaffUI(new Staff("Robert",  testCalendar),testCalendar);
	//	UINPContact.beginNPContactUI(new NPContact("Jack",  testCalendar),testCalendar);
	}

}