package model;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * The Calendar class takes care of keeping track of Auction requests and holds all of the Auctions.
 * 
 * @author Justin Washburn
 * @version 11/10/2016
 *
 */
public class Calendar implements Serializable {

	
	
	/**
	 * Default global needed by Serializable
	 */
	private static final long serialVersionUID = 970230924874743054L;

	/**
	 * The list of ALL of the Auctions
	 */
	private List<Auction> myAuctions;
	
	/**
	 * The value that will be assigned to the next item.
	 */
	private int myNextItemID;
	
	/**
	 * The number of upcoming Auctions
	 */
	private int myCurrentAuctions;
	
	/**
	 * Creates a Calendar with an empty list of Auctions
	 */
	public Calendar() {
		myAuctions = new LinkedList<Auction>();
		myNextItemID = 0;
		myCurrentAuctions = 0;
	}
	/**
	 * Creates a Calendar with the given list of auctions for testing purposes
	 * @param theAuctions The desired list of auctions to test with
	 */
	public Calendar(List<Auction> theAuctions) {
		myAuctions = theAuctions;
		myNextItemID = 0;
		myCurrentAuctions = 0;
		
	}
	
	/**
	 * Gives the number of Auctions coming after the current time
	 * 
	 * @return The number of upcoming Auctions
	 */
	public int getUpcomingAuctionsNumber() {
		GregorianCalendar currentDate = (GregorianCalendar)GregorianCalendar.getInstance();
		myCurrentAuctions = 0;
		for (int i = 0; i < myAuctions.size(); i++) {
			if (myAuctions.get(i).getDate().after(currentDate)) {
				myCurrentAuctions++;
			}
		}
		return myCurrentAuctions;
	}
	
	
	/**
	 * Returns true if the given date does not conflict with any of Auction Central's 
	 * rules about auction scheduling. Automatically runs every test related to the actual
	 * date of the Auction. Does not run tests related to the NPContact information.
	 * 
	 * @param theDate The desired date for an auction request
	 * @return True if the Auction can be added
	 */
	public int validateAuctionRequest(GregorianCalendar theDate) {
		if (!validateAuctionRequestTwoPerDay((GregorianCalendar)theDate.clone())) {
			return -1;
		}
		if (!validateAuctionRequestMax25Auctions()) {
			return -2;
		}
		if (!validateAuctionRequestAtMostOneMonthInFuture((GregorianCalendar)theDate.clone())) {
			return -3;
		}
		if (dateAtLeastOneWeekInFuture((GregorianCalendar)theDate.clone())) {
			return -4;
		}
		
		return 0;
	}
	
	/**
	 * Checks whether there are already 2 Auctions on the desired date and returns false if so.
	 * 
	 * @param theDate The desired date for an auction request
	 * @return True if there are less than 2 Auctions
	 */
	public boolean validateAuctionRequestTwoPerDay(GregorianCalendar theDate) {
		int auctionsOnDay = 0;
		int year = theDate.get(GregorianCalendar.YEAR);
		int day = theDate.get(GregorianCalendar.DAY_OF_YEAR);
		for (int i = 0; i < myAuctions.size(); i++) {

			if ((year == myAuctions.get(i).getDate().get(GregorianCalendar.YEAR))
				&& (day == myAuctions.get(i).getDate().get(GregorianCalendar.DAY_OF_YEAR))) {
				auctionsOnDay++;	
			}
		}
		
		if (auctionsOnDay >= 2) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if there are currently less than 25 auctions scheduled for the future
	 * 
	 * @return True if there are less than 25 Auctions
	 */
	public boolean validateAuctionRequestMax25Auctions() {
		if (getUpcomingAuctionsNumber() >= 25) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validates that the date is equal to or less than one month in the future
	 * @param aDate The Desired date of the Auction
	 * @return True if if the date is equal to or less than one month in the future
	 */
	public boolean validateAuctionRequestAtMostOneMonthInFuture(GregorianCalendar aDate) {
		GregorianCalendar oneMonthInFuture = (GregorianCalendar)GregorianCalendar.getInstance();
		oneMonthInFuture.add(GregorianCalendar.MONTH, 1);
		if (aDate.after(oneMonthInFuture)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validates that the date is equal to or greater than one week in the future
	 * @param aDate The Desired date of the Auction
	 * @return True if if the date is equal to or greater than one week in the future
	 */
	public boolean dateAtLeastOneWeekInFuture(GregorianCalendar aDate) {
		GregorianCalendar oneWeekInFuture = (GregorianCalendar)GregorianCalendar.getInstance();
		oneWeekInFuture.add(GregorianCalendar.WEEK_OF_YEAR, 1);
		if (aDate.before(oneWeekInFuture)) {
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * Adds an Auction to the Calendar. Request must be validated before being added
	 * 
	 * @param theAuction The auction to be added to the calendar
	 * @return True if the Auction was added
	 */
	public boolean addAuction(Auction theAuction) {
		if (validateAuctionRequest(theAuction.getDate()) == 0) {
			myAuctions.add(theAuction);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Gets the auctions from a period starting at the Date given and ending one month (31 days) after
	 * 
	 * @param theDate The date to start the list of Auctions
	 * @return The list of Auctions starting at the Date given and ending one month after
	 */
	public List<Auction> getAuctions(GregorianCalendar theDate) {
		//sets the start date to look at the day you give it
		GregorianCalendar startDate = (GregorianCalendar)theDate.clone();
		startDate.add(GregorianCalendar.MILLISECOND, -1);

		//sets the end date to 31 days after the day you give it
		GregorianCalendar endDate = (GregorianCalendar)theDate.clone();
		endDate.add(GregorianCalendar.DAY_OF_YEAR, 31);
		endDate.add(GregorianCalendar.MILLISECOND, 1);
		LinkedList<Auction> desiredMonth = new LinkedList<Auction>();
		for (int i = 0; i < myAuctions.size(); i++) {
			
			Auction tempAuction = myAuctions.get(i);
			if (tempAuction.getDate().after(startDate) &&
				tempAuction.getDate().before(endDate)) {
				
				
				desiredMonth.add(tempAuction);
			}
		}
		
		return desiredMonth;
	}
	/**
	 * Returns a list of all of the Auctions ever added to the Calendar
	 * 
	 * @return The list of all Auctions in the Calendar
	 */
	public List<Auction> getAllAuctions() {
		return myAuctions;
	}
	/**
	 * Gets the next unique ItemID to be used when creating an Item
	 * 
	 * @return The next unique ItemID
	 */
	public int getNextItemID() {
		myNextItemID++;
		return myNextItemID;
	}
}
