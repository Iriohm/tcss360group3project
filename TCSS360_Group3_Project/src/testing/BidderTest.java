package testing;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import junit.framework.*;
import model.Bidder;
import model.Calendar;
import model.Item;
import model.Auction;

public class BidderTest extends TestCase {

	/** An Item object that will be used to test the Bidder class. */
    private Item myTestItem;
    private Item myTestItem2;
    private Bidder myTestBidder;
    private Auction myAuction;
    private GregorianCalendar myAuctionDate = (GregorianCalendar)GregorianCalendar.getInstance();
    private  Calendar myCalendar = new Calendar();
    /**
     * Initializes the myTestBidder object before every test.
     */
    @Before
    public void setUp() {
    	//(String theID, String theName, String theDescription, double theMinimumBid,
		//	int theQuantity, String theCondition) {
    //	myAuctionDate.add(java.util.Calendar.DATE, +15);
    	//TODO find out how to change the date of auction after added so passes the checks but so i can still test somehow.
    	myAuction = new Auction(myAuctionDate ,"testAuction");
    	myTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myTestItem2 = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	
    	myCalendar.addAuction(myAuction);
    	myTestBidder = new Bidder("bid4lyfe", myCalendar );
    	
    	myAuction.addItem(myTestItem2);
    	
    	myTestBidder.placeBid(myTestItem2, 8.00);
    	
    }

    /**
     * Test method for testCanPass().
     */
    @Test
    public void testCanPass() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	assertTrue(myTestBidder.placeBid(myTestItem, 6.00));

    	//Placing another bid on myTestItem...NOT ALLOWED
    	assertFalse(myTestBidder.placeBid(myTestItem, 7.00));
    }
    
    /**
     * Test method to test that a bid can't be canceled without having
     * a bid placed prior.
     * @author Robert hinds
     */
    @Test
    public void testBidCancelledWithoutBidPlace(){
    	//Canceling a bid without having a prior bid...NOT ALLOWED
    	assertFalse(myTestBidder.cancelBid(myTestItem, myTestBidder));
    }
    
    /**
     * Test method to test that a bid can't be canceled within two days of the auction
     * @author Robert hinds
     */
    @Test
    public void testValidateCancelBidGreaterThanDayLimit(){
    	//TODO work on adding auction so dont have to manual add days in method to check.
    	//Canceling a big within two days of the auction...NOT ALLOWED
    	assertTrue(myTestBidder.validateCancelBidGreaterThanDayLimit(myTestItem2, myTestBidder));
    }
    
}
