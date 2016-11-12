package model;
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
	private Calendar myCalendar;
	
	private static final long serialVersionUID = 7526433795622776147L;

	/**
	* This constructor sets the fields equal to the user-provided values,
	* and sets the User type to 2.
	* @param theUsername The String that contains the User's username.
	* @param theCalendar The Calendar object to call methods on.
    */
	public NPContact(String theUsername, Calendar theCalendar) {
		super(theUsername, theCalendar, 2);
	}
}
