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
	 * The maximum number future auctions allowed
	 * By Business Rule: Max 2 auctions per day.
	 * 	  Business Rule: Max of 31 days allowed to schedule auctions.
	 * @author "Robert Hinds"
	 */
	private int myTotalMaxFutureAuctions;
	
	/**
	 * Creates a Calendar with an empty list of Auctions
	 */
	public Calendar() {
		myAuctions = new LinkedList<Auction>();
		myNextItemID = 0;
		myCurrentAuctions = 0;
		myMaxAuctionsLimit = 25;
		myTotalMaxFutureAuctions = 62;
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
		myTotalMaxFutureAuctions = 62;
	}
	
	/**
	 * @author "Robert Hinds"
	 * @return the MaxAuctionsLimit
	 */
	public int getMaxAuctionsLimit() {
		return myMaxAuctionsLimit;
	}
	
	/**
	 * @author "Robert Hinds"
	 * @return the TotalMaxFutureAuctions
	 */
	public int getTotalMaxFutureAuctions() {
		return myTotalMaxFutureAuctions;
	}
	
	/**
	 * Sets current maximum number of upcoming Auctions allowed
	 * if value is validated true.
	 * @param theMaxAuctions Maximum number of Auctions.
	 * 
	 *@return 0 if maxiumum number of upcoming auctions is set. -1 if value given does not set the auction limit to zero, below zero. 
	 * -2 if value given sets the auction limit less than the number of upcoming auctions.
	 * -3 if value given sets the auction limit greater than the total maximum future auction limit based on max auctions per days and how many days in advance a auction can be scheduled.
	 *
	 */
	public int setMaxAuctionsLimit(int theMaxAuctions) {
		int returnValue = 0;	
		if( theMaxAuctions <= 0){
			returnValue = -1;
		}
		else if(theMaxAuctions <= this.getUpcomingAuctionsNumber()){
			returnValue = -2;
		}
		else if(theMaxAuctions > this.getTotalMaxFutureAuctions()){
			return -3;
		}
		else{
		this.myMaxAuctionsLimit = theMaxAuctions;
		returnValue = 0;
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
	 * Returns zero if the given date does not conflict with any of Auction Central's 
	 * rules about auction scheduling. Automatically runs every test related to the actual
	 * date of the Auction. Does not run tests related to the NPContact information.
	 * 
	 * @param theDate The desired date for an auction request
	 * @return 0 if the Auction can be added -1 if request date already has reached maximum auction per day limit 
	 * -2 if auction request will exceed maximum auction limit
	 * -3 if auction date requested is greater than 1 month in advance
	 * -4 if auction date requested is less than 1 week in advance
	 * 
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
	 * check in 3
	 * 
	 * this look for a NP actions and remove it. return 0 if safely remove from the list. return -1 if no auction in the past two day.
	 * return -2 if the NP auction can't be found.
	 * 
	 * @param theAuction
	 * @return return 0 if safely remove from the list. return -1 if no auction in the past two day.
	 * return -2 if the NP auction can't be found.
	 */
	public int removeNPAuction(Auction theAuction) {//, String theNpUserName, String theAuctionName) {
		GregorianCalendar twoDayForrowed = (GregorianCalendar) GregorianCalendar.getInstance();
		twoDayForrowed.add(GregorianCalendar.DAY_OF_YEAR, 2);
		List<Auction> nextTwoDayAuction = getAuctions(twoDayForrowed);
		System.out.println(theAuction.getAuctionName());
		System.out.println(nextTwoDayAuction.size());
		System.out.println(myAuctions.size());
		if(nextTwoDayAuction.isEmpty()){
			return -1; // no auction past two days.
		} else if(nextTwoDayAuction.contains(theAuction)) {
			myAuctions.remove(theAuction); 
			return 0; // found and remove the actions
		}
		return -2; // could not find there actions in the system
	}
}
