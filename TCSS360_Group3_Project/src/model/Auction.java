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
	 * Creates the Auction and leaves the list of items empty.
	 * 
	 * @param 
	 */
	public Auction(GregorianCalendar theDate, String theAuctionName) {
		myDate = theDate;
		myAuctionName = theAuctionName;
		myItems = new LinkedList<Item>();
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
		if (validateItemAdd(theItem)) {
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
	
	/**
	 * Gets the date associated with the Auction
	 * 
	 * @return The date associated with the Auction
	 */
	public GregorianCalendar getDate() {
		return myDate;
	}
	
	/**
	 * Gets the name of the Auction
	 * 
	 * @return The name of the Auction
	 */
	public String getAuctionName() {
		return myAuctionName;
	}
	
}
