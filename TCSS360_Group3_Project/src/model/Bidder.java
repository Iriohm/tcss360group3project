package model;

import java.util.GregorianCalendar;
import java.util.List;

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
	 * @author "Robert Hinds", "Iriohm"
	 * @return true if bid was removed, false otherwise.
	 */
	public boolean cancelBid(Item theItemToCancelBidOn, Bidder theBidder) {
		// Heavily altered to implement new test, which in turn implements
		// new Item functionality (pointer to parent Auction).
		if	(hasAuctionPassedTwoDayCutoffPoint(theItemToCancelBidOn)) {
			return false;

		} else {
			return theItemToCancelBidOn.getBidChain().removeBid(myUsername);

		}

	}


	/**
	 * Tests to see whether the Auction for the given Item is within the next two days. Bids can only
	 * be canceled on Items if their Auctions are at or more than two days away.
	 *
	 * @author Iriohm
	 * @return Returns true if the Auction the Item belongs to has passed its two-day cutoff point (no bid cancellation beyond then).
	 */
	public static boolean hasAuctionPassedTwoDayCutoffPoint(Item theItem) {
		if	(theItem.getAuction() == null) {
			System.err.println("Error: Bidder.isWithinTwoDaysOfAuction() encountered an Item with a null myAuction!");

			return false;

		}

		long lTwoDaysFromNow = new GregorianCalendar().getTimeInMillis() + (2 * 24 * 60 * 60 * 1000);
		long lAuctionDate = theItem.getAuction().getDate().getTimeInMillis();

		return (lTwoDaysFromNow > lAuctionDate);

	}


	/**
	 * This method validates that the auction is greater than or equal to cancellation deadline
	 * @author "Robert Hinds"
	 * @return true if the auction date of the item bid being cancelled is  greater than cancellation deadline, false otherwise.
	 */
	public boolean validateCancelBidGreaterThanDayLimit(Item theItemToCancelBidOn, Bidder theBidder) {

		int i = 0, j = 0;
		int sizeOfListOfAuctionItems = 0;
		int dayLimitToCancel = 2;
		long differenceInMillis = 0;
		long diffenceInDays = 0;

		GregorianCalendar theDate = (GregorianCalendar) GregorianCalendar.getInstance();
		boolean foundItem = false;
		boolean returnValue = false;
		//TODO refactor this area if time allows.
		List<Auction> theAuctionList = theBidder.getCalendar().getAuctions(theDate);
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
				//converts dates into milliseconds then converts them to days.
				differenceInMillis =  theAuctionList.get(i).getDate().getTimeInMillis() - theDate.getTimeInMillis() ;
			//	double temp =  differenceInMillis / (24 * 60 * 60 * 1000);

				diffenceInDays = differenceInMillis / (24 * 60 * 60 * 1000);

				if ( diffenceInDays >= dayLimitToCancel) {
					returnValue = true;
				}
				else{
					returnValue = false;
				}
			}
			i++;
		}
		return returnValue;
	}

}