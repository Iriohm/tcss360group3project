package model;
/**
* This class is used to handle/hold methods and
* fields specifically for AuctionCentral bidders.
*
* @author Vlad Kaganyuk
* @version 8 Nov 2016
*
*/
public class Bidder extends User {
	private Calendar myCalendar;

	/**
	* This constructor sets the fields equal to the user-provided values.
	* @param theCalendar The Calendar object to call methods on.
    */
	public Bidder(Calendar theCalendar) {
		super(theCalendar);
	}

	/**
	* This method allows the user to place a bid on an Item object.
    */
	public void placeBid() {

	}
}