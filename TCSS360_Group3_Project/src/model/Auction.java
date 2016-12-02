package model;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * The Auction Class handles adding Items to Auctions and stores the list of Items
 * 
 * @author Justin Washburn
 * @version 11/10/2016
 *
 */
public class Auction implements Serializable {

	/**
	 * Default global needed by Serializable
	 */
	private static final long serialVersionUID = 7183405560696200845L;
	
	/**
	 * List of Items that an Auction currently has
	 */
	private List<Item> myItems;
	
	/**
	 * The Date the Auction is being held on
	 */
	private GregorianCalendar myDate;
	
	/**
	 * The name of the auction
	 */
	private String myAuctionName;
	
	/**
	 * Estimated number of items in the auction
	 */
	private int myEstimatedItems;
	
	/**
	 * Comments about the auction
	 */
	private String myComments;
	
	
	/**
	 * Creates the Auction and leaves the list of items empty.
	 * 
	 * @param 
	 */
	public Auction(GregorianCalendar theDate, String theAuctionName) {
		myDate = (GregorianCalendar)theDate.clone();
		myAuctionName = theAuctionName;
		myItems = new LinkedList<Item>();
		myEstimatedItems = -1;
		myComments = "";
	}
	/**
	 * Sets the estimated items to the given value if it is greater than 0, otherwise returns false
	 * @param theEstimate The estimated number of items to be in the Auction
	 * @return Whether on not the Estimated Items was updated
	 */
	
	public boolean setEstimatedItems(int theEstimate) {
		if (theEstimate <= 0) {
			return false;
		}
		myEstimatedItems = theEstimate;
		return true;
	}
	
	/**
	 * Gets the estimated number of items in the Auction or -1 if not given
	 * @return The estimated number of items in the Auction or -1 if not given
	 */
	public int getEstimatedItems() {
		return myEstimatedItems;
	}
	/**
	 * Sets the Comments for the Auction
	 * @param theComments The desired Comments to be added to the Auction
	 */
	
	public void setComments(String theComments) {
		myComments = theComments;
	}
	/**
	 * Gets the Comments for the Auction
	 * @return The Comments
	 */
	public String getComments() {
		return myComments;
	}
	
	
	/**
	 * Gets the list of all items in the Auction
	 * 
	 * @return The list of items in the Auction
	 */
	public List<Item> getItems() {
		return myItems;
	}
	
	
	/**
	 * Adds the item to the list of items in the Auction if it passes the validation test
	 * 
	 * @param theItem The Item to be added
	 * @return True if successfully added
	 */
	public boolean addItem(Item theItem) {
		if (validateItemAdd(theItem) && validateItemAddAuctionDate()) {
			myItems.add(theItem);
			return true;
		}
		return false;
	}
	/**
	 * Returns whether or not the Auction already has that Item based on the ID number
	 * 
	 * @param theItem The item to check the Auction's item against
	 * @return True if the Item can be added
	 */
	public boolean validateItemAdd(Item theItem) {
		for (int i = 0; i < myItems.size(); i++) {
			if (theItem.getID().equals(myItems.get(i).getID())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validateItemAddAuctionDate() {
		if (((GregorianCalendar)GregorianCalendar.getInstance()).after(myDate)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the date associated with the Auction
	 * 
	 * @return The date associated with the Auction
	 */
	public GregorianCalendar getDate() {
		return (GregorianCalendar)myDate.clone();
	}
	
	/**
	 * Gets the name of the Auction
	 * 
	 * @return The name of the Auction
	 */
	public String getAuctionName() {
		return myAuctionName;
	}
	
	/**
	 * @author David Nowlin
	 * this is to remove a item to form the Auction.
	 * 
	 * @param theItem want to remove.
	 * @return this return a -5 if the list is empty. return -6 if the Item is not in the list.
	 * return a 0 if the list has a item and remove it.
	 */
	public int removeItem(Item theItem) {
		if (myItems.isEmpty()) {
			return -5; // no item in the list.
		}else if(myItems.contains(theItem)) {
			myItems.remove(theItem);
			return 0;//was able to remove item.
		}
		return -6;// if my item is not in the list.
	}
}
