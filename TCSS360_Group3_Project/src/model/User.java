package model;
import java.io.Serializable;

/**
* This class is used to handle/hold general methods and
* fields for AuctionCentral users.
*
* @author Vlad Kaganyuk
* @version 12 Nov 2015
*
*/
public class User implements Serializable {
	String myUsername;
	//String myPassword;
	private Calendar myCalendar;
	private int myType;
	
	private static final long serialVersionUID = 7526472295622776147L;

	/**
	* This constructor sets the fields equal to the user-provided values.
	* @param theCalendar The Calendar object to call methods on.
    */
	public User(String theUsername, Calendar theCalendar, int theType) {
		myUsername = theUsername;
		myCalendar = theCalendar;
		myType = theType;
	}

	/**
	 * This method returns a String representation of myCalendar.
	 * @return Returns a String which represents the myCalendar object;
     */
	public String displayCalendar() {
		return myCalendar.toString();
	}
	
	/**
	 * This method returns an int so the type of User can be determined.
	 * Key: 
	 *	1 = Staff
	 *	2 = NPContact
	 *	3 = Bidder
	 * @return Returns an int that serves as a unique User type identifier.
     */
	public int getType() {
		return myType;
	}
	
	/**
	 * This method returns the username of this User.
     */
	public String getUsername() {
		return myUsername;
	}
}