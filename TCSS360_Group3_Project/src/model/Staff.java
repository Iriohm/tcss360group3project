package model;
/**
* This class is used to handle/hold methods and
* fields specifically for AuctionCentral staff members.
*
* @author Vlad Kaganyuk
* @version 12 Nov 2016
* @modified 11/30/2016
*
**/
public class Staff  extends User{
	
	/**
	 * Error code signifying that input to the changeMaxAuctionLimit function was not a number.
	 */
	public static final int MAX_AUCTION_LIMIT_INPUT_NOT_A_NUMBER = -100;
	/**
	 * Return signifying that maximum auction limit was changed successfully.
	 */
	public static final int MAX_AUCTION_LIMIT_CHANGE_SUCCESSFUL = 0;
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

	/**
	 * This method changes the maximum upcoming auction limit.
	 * @author "Robert Hinds"
	 * @param theStaff 
	 * @param theNumberToIncreaseMaxAuctionLimitBy A string that contains the value the maximum auction limit will be set to.
	 *@return 0 if maxiumum number of upcoming auctions is set. -1 if value given does not set the auction limit to zero, below zero. 
	 * -2 if value given sets the auction limit less than the number of upcoming auctions.
	 * -3 if value given sets the auction limit greater than the total maximum future auction limit based on max auctions per days and how many days in advance a auction can be scheduled.
	 * MAX_AUCTION_LIMIT_INPUT_NOT_A_NUMBER if value given is a non integer.
	 */
	public int changeMaxAuctionLimit(Staff theStaff, String theNumberToIncreaseMaxAuctionLimitBy) {
		int returnValue = 0;
		try{
		returnValue = theStaff.getCalendar().setMaxAuctionsLimit(Integer.parseInt(theNumberToIncreaseMaxAuctionLimitBy));
		}catch (NumberFormatException e){
			returnValue = MAX_AUCTION_LIMIT_INPUT_NOT_A_NUMBER;
		}
		 return returnValue;
		
		
	}


}
