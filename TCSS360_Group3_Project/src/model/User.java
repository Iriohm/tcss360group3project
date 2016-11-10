package model;
import java.io.Serializable;

/**
* This class is used to handle/hold general methods and
* fields for AuctionCentral users.
*
* @author Vlad Kaganyuk
* @version 8 Nov 2015
*
*/
public class User implements Serializable {
	//String myUsername;
	//String myPassword;
	private Calendar myCalendar;
	private static final long serialVersionUID = 7526472295622776147L;

	/**
	* This constructor sets the fields equal to the user-provided values.
	* @param theCalendar The Calendar object to call methods on.
    */
	public User(Calendar theCalendar) {
		myCalendar = theCalendar;
	}

	/**
	 * This method returns a String representation of myCalendar.
	 * @return Returns a String which represents the myCalendar object;
     */
	public String displayCalendar() {
		return myCalendar.toString();
	}
}