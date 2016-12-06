package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
* This class is used to handle/hold methods and
* fields specifically for AuctionCentral non-profit
* contact persons.
*
* @author Vlad Kaganyuk
* @version 12 Nov 2016
*
**/
public class NPContact extends User {
	
	private List<Auction> myAuctions;
	
	private static final long serialVersionUID = 7526433795622776147L;

	/**
	* This constructor sets the fields equal to the user-provided values,
	* and sets the User type to 2.
	* @param theUsername The String that contains the User's username.
	* @param theCalendar The Calendar object to call methods on.
    */
	public NPContact(String theUsername, Calendar theCalendar) {
		super(theUsername, theCalendar, 2);
		myAuctions = new ArrayList<Auction>();
	}
	
	/**
	 * Adds the given item to the given auction, and disallows adding duplicate items.
	 * @param theAuctionToAddTo The Auction to add the Item to.
	 * @param theItemToAdd The Item object to add to the Auction.
	 * @return Returns whether or not the add was successful.
	 */
	public boolean addItem(Auction theAuctionToAddTo, Item theItemToAdd) {
		String itemToAddID = theItemToAdd.getID();
		List<Item> currentItems = theAuctionToAddTo.getItems();
		for (int i = 0; i < currentItems.size(); i++) {
			String currID = currentItems.get(i).getID();
			if (currID.equals(itemToAddID)) {
				return false;
			}
		}
		theAuctionToAddTo.addItem(theItemToAdd);
		return true;
	}
	
	/**
	 * Adds an auction to the myCalendar object.
	 * @param theAuction An auction that the user wants to add to the Calendar.
	 */
	public void addAuction(Auction theAuction) {
		myAuctions.add(theAuction);
	}
	
	/**
	 * this dose all the cheek that have to be done to add the auction to the non-profit and the calendar
	 * @param theAuction want auction that you want to add.
	 * @param theCalendar that has all auction.
	 * @return true if it succeed add auction. false if it failed to add the auction.
	 */
	public boolean addAuction(Auction theAuction, Calendar theCalendar) {
		if(hasAuctionUpcomingOrLastYear()) {
			return false;
		}else if (theCalendar.addAuction(theAuction)) {
			addAuction(theAuction);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the non-profit had an auction less than a year ago and if they have an upcoming auction.
	 */
	public boolean hasAuctionUpcomingOrLastYear() {
		GregorianCalendar oneYearAgo = (GregorianCalendar)GregorianCalendar.getInstance();
		oneYearAgo.add(GregorianCalendar.YEAR, -1);
//		oneYearAgo.add(GregorianCalendar.DAY_OF_YEAR, -365);
		GregorianCalendar oneYearfuture = (GregorianCalendar)GregorianCalendar.getInstance();
		oneYearfuture.add(GregorianCalendar.YEAR, 1);
		oneYearfuture.add(GregorianCalendar.DAY_OF_YEAR, -1);
		for (int i = 0; i < myAuctions.size(); i++) {
			//check if had an auction less than a year ago or have an upcoming auction already.
			if (myAuctions.get(i).getDate().after(oneYearAgo) && myAuctions.get(i).getDate().before(oneYearfuture)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the most recently added auction.
	 * @return Returns the latest Auction object.
	 */
	public Auction getLatestAuction() {
		if (myAuctions.size() == 0) {
			return null;
		}
		return myAuctions.get(myAuctions.size() - 1);
	}
	
	/**
	 * 
	 * @author David Nowlin
	 * check in 3
	 * 
	 * it look through the np actions and the calendar to remove the auction.
	 * 
	 * @param theCalendar main calendar
	 * @param theAction look for actions
	 * @return return -3 if the Np has no Auctions. return -4 if it not that Np Auction.
	 *  if problem with the calendar removeNPAuctions return that values. return 0 if done successfully.
	 */
	public int removeMyAuction(Calendar theCalendar, Auction theAuction)	{
		int noList = -3;
		if(myAuctions.isEmpty()){
			return noList; // the actions not in the list
		}else if (myAuctions.contains(theAuction)) {
			int removeSaft = theCalendar.removeNPAuction(myAuctions.get(myAuctions.indexOf(theAuction)));
			if(removeSaft != 0) {
				return removeSaft;
			}
			myAuctions.remove(theAuction);
			return 0;
		}
		
		return -4;
	}
	
	/**
	 * @author David Nowlin
	 * this will remove the none profit item form the list
	 * 
	 * @param theCalendar
	 * @param theAction
	 * @param theItem
	 * @return what you get form the removeNPItemAuction. return -3 if the auction is in the auction list. 
	 * return the -4 if the auction is not in the list.
	 */
	public int removeMyItem(Calendar theCalendar, Auction theAction, Item theItem)	{
		int noList = -3;
		if(myAuctions.isEmpty()){
			return noList; // the actions not in the list
		}else if (myAuctions.contains(theAction)) {
			int removeSaft = theCalendar.removeNPItemAuction((myAuctions.get(myAuctions.indexOf(theAction))), theItem);
			if(removeSaft != 0) {
				return removeSaft;
			}
			myAuctions.get(myAuctions.indexOf(theAction)).removeItem(theItem);
			return 0;
		}
		
		return -4;
	}
	
	/**
	 * Gets a list of this user's past and present auctions.
	 * @return A list of Auction objects belonging to this user.
	 */
	public List<Auction> getMyAuctions() {
		return myAuctions;
	}
}
