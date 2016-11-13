package model;
/**
* This class is used to handle/hold methods and
* fields specifically for AuctionCentral bidders.
*
* @author Vlad Kaganyuk
* @version 12 Nov 2016
*
*/
public class Bidder extends User {
	private Calendar myCalendar;
	
	private static final long serialVersionUID = 7526496795622776147L;

	/**
	* This constructor sets the fields equal to the user-provided values,
	* and sets the User type to 3.
	* @param theUsername The String that contains the User's username.
	* @param theCalendar The Calendar object to call methods on.
    */
	public Bidder(String theUsername, Calendar theCalendar) {
		super(theUsername, theCalendar, 3);
	}

	/**
	* This method allows the user to place a bid on an Item object.
    */
	public void placeBid(Item theItemToBidOn, double thePrice) {
		if (theItemToBidOn.getBidOf(this.myUsername) == null) {
			theItemToBidOn.makeBid(this.myUsername, thePrice);
		} else {
			//return String that says failed to placeBid?
		}
	}
}