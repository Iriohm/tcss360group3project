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
	 * The maximum number of upcoming Auctions allowed.
	 * @author "Robert Hinds"
	 */
	private int myMaxAuctionsLimit;
	
	/**
	 * Creates a Calendar with an empty list of Auctions
	 */
	public Calendar() {
		myAuctions = new LinkedList<Auction>();
		myNextItemID = 0;
		myCurrentAuctions = 0;
		myMaxAuctionsLimit = 25;
	}
	/**
	 * Creates a Calendar with the given list of auctions for testing purposes
	 * @param theAuctions The desired list of auctions to test with
	 */
	public Calendar(List<Auction> theAuctions) {
		myAuctions = theAuctions;
		myNextItemID = 0;
		myCurrentAuctions = 0;
		myMaxAuctionsLimit = 25;
	}
	
	/**
	 * @author "Robert Hinds"
	 * @return the myMaxAuctionsLimit
	 */
	public int getMaxAuctionsLimit() {
		return myMaxAuctionsLimit;
	}
	
	/**
	 * Sets current maximum number of upcoming Auctions allowed
	 * if value is validated true.
	 * @param theMaxAuctions Maximum number of Auctions.
	 * @return true if value given does not set the auction limit to zero, below zero and less than the number of upcoming auctions. False otherwise.
	 */
	public boolean setMaxAuctionsLimit(int theMaxAuctions) {
		boolean returnValue = false;	
		if( theMaxAuctions <= 0){
			returnValue = false;
		}
		else if(theMaxAuctions <= this.getUpcomingAuctionsNumber()){
			returnValue = false;
		}
		else{
		this.myMaxAuctionsLimit = theMaxAuctions;
		returnValue = true;
		}
		return returnValue;
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
		if (!dateAtLeastOneWeekInFuture((GregorianCalendar)theDate.clone())) {
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
	
	//TODO change 25 into myMaxAuctionsLimit and test. maybe make the limit number be grabbed from a properties file.
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
	//	System.out.println(aDate.getTime());
		oneMonthInFuture.add(GregorianCalendar.MONTH, 1);
		//System.out.println(oneMonthInFuture.getTime());
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
		System.out.println(aDate.getTime());
		oneWeekInFuture.add(GregorianCalendar.WEEK_OF_YEAR, 1);
		System.out.println(oneWeekInFuture.getTime());
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
	
/*	//this needs a junit test.
	*//**
	 * Gets the auctions for today's date
	 * 
	 * @param theDate The date to start the list of Auctions
	 * @return The list of Auctions on the Date given
	 * @author Robert Hinds
	 *  
	 *//*
	public List<Auction> getTodayAuctions(GregorianCalendar theDate) {
		//sets the start date to look at the day you give it
		GregorianCalendar startDate = (GregorianCalendar)theDate.clone();
		startDate.add(GregorianCalendar.DATE, -1);

		//sets the end date to 1 days after the day you give it
		GregorianCalendar endDate = (GregorianCalendar)theDate.clone();
		endDate.add(GregorianCalendar.DATE, +1);
		endDate.add(GregorianCalendar.MILLISECOND, 1);
		LinkedList<Auction> desiredDay = new LinkedList<Auction>();
		for (int i = 0; i < myAuctions.size(); i++) {
			
			Auction tempAuction = myAuctions.get(i);
			if (tempAuction.getDate().after(startDate) &&
				tempAuction.getDate().before(endDate)) {
				
				
				desiredDay.add(tempAuction);
			}
		}
		
		return desiredDay;
	}*/
	
	/**
	 * Developer only. Needed to be able to test other methods. Adds an Auction to the Calendar.
	 *  Request IS NOT validated before being added
	 * 
	 * @param theAuction The auction to be added to the calendar
	 * @return True if the Auction was added
	 */
	public boolean devOnlyAddAuctionByPassValidation(Auction theAuction) {
			return myAuctions.add(theAuction);
		
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
	
	/**
	 * @author David Nowlin
	 * this get all the auction after two day way
	 * @return the auction after two days
	 */
	public List<Auction> getAuctionsTwoDayhAead() {
		final int DAY_TO_CANCEL_AUCTION = 2;
		GregorianCalendar twoDayForrowed = (GregorianCalendar) GregorianCalendar.getInstance();
		twoDayForrowed.add(GregorianCalendar.DAY_OF_YEAR, DAY_TO_CANCEL_AUCTION);
		return getAuctions(twoDayForrowed);
	}
	
	/**
	 * @author David Nowlin
	 * check in 3
	 * 
	 * this look for a NP actions and remove it. return 0 if safely remove from the list. return -1 if no auction in the past two day.
	 * return -2 if the NP auction can't be found.
	 * 
	 * @param theAuction
	 * @return return 0 if safely remove from the list. return -1 if no auction in the past two day.
	 * return -2 if the NP auction can't be found.
	 */
	public int removeNPAuction(Auction theAuction) {
		List<Auction> nextTwoDayAuction = getAuctionsTwoDayhAead();
		if(nextTwoDayAuction.isEmpty()){
			return -1; // no auction past two days.
		} else if(nextTwoDayAuction.contains(theAuction)) {
			myAuctions.remove(theAuction); 
			return 0; // found and remove the actions
		}
		return -2; // could not find there actions in the system
	}
	
	/**
	 * @author David Nowlin
	 * 
	 * this will remove a item form the none Profit auction.
	 * 
	 * @param theAuction the NP Auction.
	 * @param theItem want item to remove.
	 * @return -1 if the auction list is empty. -2 if the None profit auction is not in the list. return what the remove item and 0 if there it work out.
	 */
	public int removeNPItemAuction(Auction theAuction, Item theItem) {
		List<Auction> nextTwoDayAuction = getAuctionsTwoDayhAead();
		if(nextTwoDayAuction.isEmpty()){
			return -1; // no auction past two days.
		} else if(nextTwoDayAuction.contains(theAuction)) {
			int  removeSaft = nextTwoDayAuction.get(nextTwoDayAuction.indexOf(theAuction)).removeItem(theItem); 
			if(removeSaft != 0) {
				return removeSaft; // return the auction error
			}
			return 0; // found and remove the Item
		}
		return -2; // could not find there actions in the system
	}
}
