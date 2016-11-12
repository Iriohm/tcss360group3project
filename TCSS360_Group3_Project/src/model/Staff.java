package model;
/**
* This class is used to handle/hold methods and
* fields specifically for AuctionCentral staff members.
*
* @author Vlad Kaganyuk
* @version 12 Nov 2016
*
**/
public class Staff extends User{
	private Calendar myCalendar;

	private static final long serialVersionUID = 7526472274122776147L;
	
	/**
	* This constructor sets the fields equal to the user-provided values,
	* and sets the User type to 1.
	* @param theUsername The String that contains the User's username.
	* @param theCalendar The Calendar object to call methods on.
    */
	public Staff(String theUsername, Calendar theCalendar) {
		super(theUsername, theCalendar, 1);
	}
}
