
package testingDeliverable1;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import junit.framework.*;
import model.Auction;
import model.Calendar;
import model.Item;
import model.Staff;
/**
 * Test Methods to ensure that the Staff user class's logic is correct.
 * @author "Robert Hinds"
 * @version 11/30/2016
 *
 */
public class StaffTest extends TestCase{

	private Staff myTestStaff;
	private Calendar testCalendar;
	private GregorianCalendar aDate;
	private Auction testAuction;
	private Item testItem1;
	private Item testItem2;
	/**
     * Initializes the myTestStaff object before every test.
     */
    @Before
    public void setUp() {
		testCalendar = new Calendar();
		aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		for(int i = 0; i < testCalendar.getMaxAuctionsLimit();i++){
		aDate.add(GregorianCalendar.DATE, i);
		
		testAuction = new Auction(aDate, "test");
		testCalendar.devOnlyAddAuctionByPassValidation(testAuction);
		}
		testItem1 = new Item(testCalendar.getNextItemID() + "", "Football", "A football, wow!", "Small", 10.01, 2, "good");
		testItem2 = new Item(testCalendar.getNextItemID() + "", "Handball", "A handball, wow!", "small", 10.51, 1, "good");
    	myTestStaff = new Staff("Jackers", testCalendar);
    }
    
    /**
     * Test method for increasing the max auction count by zero
     * 
     * @author "Robert Hinds"
     */
    @Test
    //Maximum future auctions increased by zero... ALLOWED
    public void testChangeMaxAuctionLimitByZero() {
    	String myNumberToIncreaseMaxAuctionLimitTo = Integer.toString(myTestStaff.getCalendar().getMaxAuctionsLimit());
    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToIncreaseMaxAuctionLimitTo), 0);

    }
    
    /**
     * Test method for increasing the max auction count below zero
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set below zero... NOT ALLOWED
    public void testChangeMaxAuctionLimitBelowZero() {
    	
    	String myNumberToChangeMaxAuctionLimitTo = "-1";
		assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitTo),-1);
    }
    
    /**
     * Test method for increasing the max auction count to zero
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to zero... NOT ALLOWED
    public void testChangeMaxAuctionLimitToZero() {
    	String myNumberToChangeMaxAuctionLimitBy = "0";
    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitBy),-1);
    }
    
    /**
     * Test method for decreasing maximum auction limit below the number of active auctions.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to below current active auctions... NOT ALLOWED
    public void testChangeMaxAuctionLimitToBelowCurrentActiveAuctionCount() {
    	String myNumberToChangeMaxAuctionLimitBy = Integer.toString(myTestStaff.getCalendar().getUpcomingAuctionsNumber()-1);

    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitBy),-2);
    }
    
    /**
     * Test method for increasing the maximum auction limit by 1.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to one more than current max auction limit value... ALLOWED
    public void testChangeMaxAuctionLimitByOneAboveCurrentNumber() {
    	String myNumberToChangeMaxAuctionLimitBy = Integer.toString(myTestStaff.getCalendar().getMaxAuctionsLimit()+1);

    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitBy),0);
    }
    
    /**
     * Test method for increasing the maximum auction limit by 10.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to 10 more than current max auction limit value... ALLOWED
    public void testChangeMaxAuctionLimitByTenAboveCurrentNumber() {
    	String myNumberToChangeMaxAuctionLimitBy = Integer.toString(myTestStaff.getCalendar().getMaxAuctionsLimit()+10);

    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitBy),0);
    }
    
    /**
     * Test method for decreased to some value greater than both zero and the number of current active auctions.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to 10 more than current max auction limit value then find active auction count which 
  //can't be more than previous max auction limit. Find the difference which at the most will be ten. Divide this result by 2, giving
  //a value of at least 5. Subtract this newly calculated value from the current max auction limit. Set max auction limit to this new result.
  // which will be at least 5 less than the previous max but still greater than the  current active auction count... ALLOWED
    public void testChangeMaxAuctionDecreaseLimitByRange() {
    	String myNumberToChangeMaxAuctionLimitBy = Integer.toString(myTestStaff.getCalendar().getMaxAuctionsLimit()+10);
    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitBy),0);
    	
    	String myCurrentMaxAuctionLimitAmount = Integer.toString(myTestStaff.getCalendar().getMaxAuctionsLimit() - (myTestStaff.getCalendar().getMaxAuctionsLimit() - myTestStaff.getCalendar().getUpcomingAuctionsNumber())/2);
    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myCurrentMaxAuctionLimitAmount),0);
    }
    
    
    /**
     * Test method for attemping to change the maximum auction limit to a non integer.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions input given as a non integer...NOT ALLOWED
    public void testChangeMaxAuctionLimitByANonInteger() {
    	String myNumberToChangeMaxAuctionLimitBy = "Twenty";

    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitBy),-100);
    }
    
    /**
     * Test method for increasing the maximum auction limit to a number greater than
     * the maximum future auction count.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to one more than max future auction allowed value... NOT ALLOWED
    public void testChangeMaxAuctionLimitByTenAboveCurrentNumbert() {
    	String myNumberToChangeMaxAuctionLimitBy = Integer.toString(Calendar.MAX_POSSIBLE_AUCTIONS + 1);

    	assertEquals(myTestStaff.changeMaxAuctionLimit(myTestStaff, myNumberToChangeMaxAuctionLimitBy), -3);
    }
}
