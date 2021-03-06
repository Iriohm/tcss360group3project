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
 * @modified 12/6/2016 by Robert Hinds
 *
 */
public class Calendar implements Serializable {
	/**
	 * Based on 31 days of 2 auctions per day
	 */
	public static final int MAX_POSSIBLE_AUCTIONS = 62;
	/**
	 * Auction request successfully added.
	 */
	public static final int AUC_REQ_SUCCESS = 0;
	
	/**
	 * Error code to signifying that the requested auctions's date already has reached the max amount of auctions that can be scheduled per day.
	 * 
	 */
	public static final int AUC_REQ_TWO_PER_DAY_MAX = -1;
	
	/**
	 * Error code to signifying that the system has reached the maximum auction limit currently able to be scheduled.
	 * 
	 */
	public static final int AUC_REQ_MAX_AUC_LIMIT_REACHED = -2;
	
	/**
	 * Error code to signifying that the requested auctions's date is greater than one month.
	 * 
	 */
	public static final int AUC_REQ_DATE_GREATER_THAN_1_MONTH = -3;
	
	/**
	 * Error code to signifying that the requested auctions's date is less than one week away.
	 * 
	 */
	public static final int AUC_REQ_DATE_LESS_THAN_1_WEEK = -4;
	
	/**
	 * Maximum auction limit currently able to be scheduled.
	 */
	public static final int MAX_AUCTION_LIMIT = 25;
	
	/**
	* The minimum amount of days before auction start to be able to successfully cancel item
	*/
	public static final int DAY_TO_CANCEL_ITEM = 2;
	/**
	* The minimum amount of days before auction start to be able to successfully cancel the auction
	*/
	public static final int DAY_TO_CANCEL_AUCTION = 2;
	/**
	* Maximum auction daily limit
	*/
	public static final int MAX_AUCTION_DAY_LIMIT = 2;
	
	/**
	 * Maximum auction limit successfully set.
	 */
	public static final int MAX_AUCTION_LIMIT_SUCCESS = 0;
			
	/**
	 * Error code to signifying that the auction attempting to be removed is being held within the next two days.
	 */
	public static final int AUCTION_LESS_THAN_TWO_DAYS_AWAY = -1;
	
	/**
	 * Code to signify that an auction was successfully removed.
	 */
	private static final int AUCTION_SUCCESSFULLY_REMOVED = 0;
	
	/**
	 * Code to signify that an auction was successfully removed.
	 */
	private static final int ITEM_SUCCESSFULLY_REMOVED = 0;
	
	/**
	 * Error code to signify that the specified auction was not located, and hence was not removed.
	 */
	private static final int AUCTION_NOT_LOCATED = -2;
	
	/**
	 * Error code to signify that value inputed attempted to set the maximum auction limit to zero or less.
	 */
	public static final int MAX_AUCTION_LIMIT_SET_0_OR_LESS = -1;
	
	/**
	 * Error code to signify that value inputed attempted to set the maximum auction limit less than
	 * currently scheduled auction count.
	 */
	public static final int MAX_AUCTION_LIMIT_SET_LESS_CURR_AUCTION_COUNT = -2;
	
	/**
	 * Error code to signify that value inputed attempted to set the maximum auction limit greater than
	 * MAX_POSSIBLE_AUCTIONS.
	 */
	public static final int MAX_AUCTION_LIMIT_SET_MORE_THAN_MAX_POSSIBLE_AUCTIONS = -3;
	
	
	
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
		myMaxAuctionsLimit = MAX_AUCTION_LIMIT;
	}
	/**
	 * Creates a Calendar with the given list of auctions for testing purposes
	 * @param theAuctions The desired list of auctions to test with
	 */
	public Calendar(List<Auction> theAuctions) {
		myAuctions = theAuctions;
		myNextItemID = 0;
		myCurrentAuctions = 0;
		myMaxAuctionsLimit = MAX_AUCTION_LIMIT;
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
	 *
	 *@return 0 if maxiumum number of upcoming auctions is set. -1 if value given does not set the auction limit to zero, below zero.
	 * -2 if value given sets the auction limit less than the number of upcoming auctions.
	 * -3 if value given sets the auction limit greater than the total maximum future auction limit based on max auctions per days and how many days in advance a auction can be scheduled.
	 *
	 */
	public int setMaxAuctionsLimit(int theMaxAuctions) {
		int returnValue = 0;
		if( theMaxAuctions <= 0){
			returnValue = MAX_AUCTION_LIMIT_SET_0_OR_LESS;
		}
		else if(theMaxAuctions < this.getUpcomingAuctionsNumber()){
			returnValue = MAX_AUCTION_LIMIT_SET_LESS_CURR_AUCTION_COUNT;
		}
		else if(theMaxAuctions > MAX_POSSIBLE_AUCTIONS){
			return MAX_AUCTION_LIMIT_SET_MORE_THAN_MAX_POSSIBLE_AUCTIONS;
		}
		else{
		this.myMaxAuctionsLimit = theMaxAuctions;
		returnValue = MAX_AUCTION_LIMIT_SUCCESS;
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
			return AUC_REQ_TWO_PER_DAY_MAX;
		}
		if (!validateAuctionRequestMaxAuctions()) {
			return AUC_REQ_MAX_AUC_LIMIT_REACHED;
		}
		if (!validateAuctionRequestAtMostOneMonthInFuture((GregorianCalendar)theDate.clone())) {
			return AUC_REQ_DATE_GREATER_THAN_1_MONTH;
		}
		if (!dateAtLeastOneWeekInFuture((GregorianCalendar)theDate.clone())) {
			return AUC_REQ_DATE_LESS_THAN_1_WEEK;
		}

		return AUC_REQ_SUCCESS;
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

		if (auctionsOnDay >= MAX_AUCTION_DAY_LIMIT) {
			return false;
		}
		return true;
	}


	/**
	 * Returns true if there are currently less than the max number of auctions scheduled for the future
	 *
	 * @return True if there are less than the max number of auctions
	 */
	public boolean validateAuctionRequestMaxAuctions() {
		if (getUpcomingAuctionsNumber() >= myMaxAuctionsLimit) {
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
//		System.out.println(aDate.getTime());
		oneWeekInFuture.add(GregorianCalendar.WEEK_OF_YEAR, 1);
//		System.out.println(oneWeekInFuture.getTime());
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
	public List<Auction> getAuctionsTwoDayAhead() {
		//final int DAY_TO_CANCEL_AUCTION = 2;
		GregorianCalendar twoDayForward = (GregorianCalendar) GregorianCalendar.getInstance();
		twoDayForward.add(GregorianCalendar.DAY_OF_YEAR, DAY_TO_CANCEL_AUCTION);
		return getAuctions(twoDayForward);
	}

	/**
	 * @author David Nowlin, Vlad Kaganyuk
	 *
	 * Searches for a Non-profit's auction and removes the auction.
	 *
	 * @param theAuction
	 * @return Returns 0 if auction was safely removed. Returns -1 if auction is being held within the next two days.
	 * Returns -2 if the Non-profit contact's auction can't be found.
	 */
	public int removeNPAuction(Auction theAuction) {
		List<Auction> nextTwoDayAuction = getAuctionsTwoDayAhead();
		/*if(nextTwoDayAuction.isEmpty()){
			return AUCTION_LESS_THAN_TWO_DAYS_AWAY; // Auction trying to be removed is being held in two days or less.
		}*/
			String auctionToRemove = theAuction.getAuctionName();
			for (int i = 0; i < nextTwoDayAuction.size(); i++) {
				if (nextTwoDayAuction.get(i).getAuctionName().equals(auctionToRemove)) {
					myAuctions.remove(i);
					return AUCTION_SUCCESSFULLY_REMOVED; // found and remove the actions
				}
			}
		return AUCTION_LESS_THAN_TWO_DAYS_AWAY; // could not find their auction in the next 2 days
	}

	/**
	 * @author David Nowlin
	 *
	 * this will remove a item form the Non-Profit's auction.
	 *
	 * @param theAuction the NP Auction.
	 * @param theItem want item to remove.
	 * @return -1 if the item to be removed belongs to an auction being held within the next two days or less. -2 if the Non-profit's auction is not in the list.
	 * Returns 0 if the item was removed from the auction successfully.
	 */
	public int removeNPItemAuction(Auction theAuction, Item theItem) {
		List<Auction> nextTwoDayAuction = getAuctionsTwoDayAhead();
		if(nextTwoDayAuction.isEmpty()){
			return AUCTION_LESS_THAN_TWO_DAYS_AWAY; // no auction past two days.
		} else if(nextTwoDayAuction.contains(theAuction)) {
			int  removeSaft = nextTwoDayAuction.get(nextTwoDayAuction.indexOf(theAuction)).removeItem(theItem);
			if(removeSaft != 0) {
				return removeSaft; // return the auction error
			}
			return ITEM_SUCCESSFULLY_REMOVED; // found and removed the Item
		}
		return AUCTION_NOT_LOCATED; // could not find the auctions in the system
	}
	
	/**
	 * Adds an Auction to the Calendar. this is for testing only
	 *
	 * @param theAuction The auction to be added to the calendar
	 */
	public void addAuctionForTesting(Auction theAuction) {
		myAuctions.add(theAuction);

	}
}