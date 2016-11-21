package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * This class is used to handle/hold methods and fields specifically for
 * AuctionCentral bidders.
 *
 * @author Vlad Kaganyuk
 * @version 12 Nov 2016
 *
 */
public class Bidder extends User {
	// Slight alteration. Removed myUsername variable. Instead inherited from
	// User superclass.
	// - M. Scott
	private static final long serialVersionUID = 7526496795622776147L;

	/**
	 * This constructor sets the fields equal to the user-provided values, and
	 * sets the User type to 3.
	 * 
	 * @param theUsername
	 *            The String that contains the User's username.
	 * @param theCalendar
	 *            The Calendar object to call methods on.
	 */
	public Bidder(String theUsername, Calendar theCalendar) {
		super(theUsername, theCalendar, 3);
		myUsername = theUsername;
	}

	/**
	 * This method allows the user to place a bid on an Item object.
	 */
	public boolean placeBid(Item theItemToBidOn, double thePrice) {
		if (theItemToBidOn.getBidOf(this.myUsername) == null) {
			theItemToBidOn.makeBid(this.myUsername, thePrice);
			return true;
		}
		return false;
	}

	/**
	 * This method allows the user to cancel a bid on an Item object.
	 * @author "Robert Hinds"
	 */
	public boolean cancelBid(Item theItemToCancelBidOn, Bidder theBidder) {
		// TODO Auto-generated method stub

		
		if(theItemToCancelBidOn.isBeingBidOnBy(theBidder.myUsername) == false){
			return false;
		}
		else if((validateCancelBidGreaterThanDayLimit(theItemToCancelBidOn, theBidder)) == false){
			return false;
		}
		else{
			//TODO validation to cancel bid
			//TODO call remove bid method from item class.
		}
		return true;
	}
	/**
	 * This method validates that the auction is greater than or equal to cancellation deadline
	 * @author "Robert Hinds"
	 * @return true if the auction date of the item bid being cancelled is  greater than or equal to cancellation deadline, false otherwise. 
	 */
	public boolean validateCancelBidGreaterThanDayLimit(Item theItemToCancelBidOn, Bidder theBidder) {

		int i = 0, j = 0;
		int sizeOfListOfAuctionItems = 0;
		int dayLimitToCancel = 2;
		long differenceInMillis = 0;
		long diffenceInDays = 0;
		
		GregorianCalendar theDate = (GregorianCalendar) GregorianCalendar.getInstance();
		//TODO remove add day.
		//theDate.add(java.util.Calendar.DATE, +15);
		boolean foundItem = false;
		boolean returnValue = false;
		List<Auction> theAuctionList = theBidder.getCalendar().getTodayAuctions(theDate);
		
		while (i < theAuctionList.size() && foundItem == false) {
			sizeOfListOfAuctionItems = theAuctionList.get(i).getItems().size();
			for (j = 0; j < sizeOfListOfAuctionItems; j++) {
				if (theAuctionList.get(i).getItems().get(j).getID() == theItemToCancelBidOn.getID()) {
					foundItem = true;
					break;
				}
			}
			if (foundItem == true) {
				//this check kinda iffy.
				//theDate.add(java.util.Calendar.DATE, 4);
				
				//converts dates into milliseconds then converts them to days.
				differenceInMillis = theDate.getTimeInMillis() - theAuctionList.get(i).getDate().getTimeInMillis();
				diffenceInDays = differenceInMillis / (24 * 60 * 60 * 1000);
				
				if ( diffenceInDays >= dayLimitToCancel) {
					returnValue = true;
				}
				else{
					returnValue = false;
				}
			}

		}
		return returnValue;
	}

}