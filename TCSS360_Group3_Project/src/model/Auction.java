package model;

import java.io.Serializable;
import java.util.Date;
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
	private Date myDate;
	
	/**
	 * The name of the auction
	 */
	private String myAuctionName;
	
	
	/**
	 * Creates the Auction and leaves the list of items empty.
	 * 
	 * @param 
	 */
	public Auction(Date theDate, String theAuctionName) {
		myDate = theDate;
		myAuctionName = theAuctionName;
		myItems = new LinkedList<Item>();
	}
	
	
	/**
	 * 
	 */
	public List<Item> getItems() {
		return myItems;
	}
	
	
	/**
	 * 
	 */
	public boolean addItem(Item theItem) {
		return false;
	}
	
	
}
