package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * The Calendar class takes care of keeping track of Auction requests and holds all of the Aucitons.
 * 
 * @author Justin Washburn
 * @version 11/10/2016
 *
 */
public class Calendar implements Serializable {

	/**
	 * Default global needed by Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The list of ALL of the aucitons
	 */
	List<Auction> myAuctions;
	
	/**
	 * Creates a Calendar with an empty list of Auctions
	 */
	public Calendar() {
		myAuctions = new LinkedList<Auction>();
	}
	
	/**
	 * Creates a Calendar with the given list of Auctions
	 * 
	 * @param theAuctions The list of auctions to be added to the Calendar
	 */
	public Calendar(List<Auction> theAuctions) {
		myAuctions = theAuctions;
	}
	
	/**
	 * Returns true if the given date does not conflict with any of Auction Central's 
	 * rules about auction scheduling
	 * 
	 * @param theDate The desired date for an auction request
	 * @return True if the Auction was added
	 */
	public boolean validateAuctionRequest(Date theDate) {
		return false;
	}
	
	/**
	 * Adds an Auction to the Calendar. Request must be validated before being added
	 * 
	 * @param theAuction The auction to be added to the calendar
	 * @return True if the Auction was added
	 */
	public boolean addAuction(Auction theAuction) {
		myAuctions.add(theAuction);
		return false;
	}
	
	/**
	 * Gets the auctions from a period starting at the Date given and ending one month after
	 * 
	 * @param theDate The date to start the list of Auctions
	 * @return The list of Auctions starting at the Date given and ending one month after
	 */
	public static List<Auction> getAuctions(Date theDate) {
		return null;
	}
}
