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
    private Item myTestItem3;
    private Item myTestItem4;
    private Item myTestItem5;
    private Item myTestItem6;
    private Item myTestItem7;
    private Item myTestItem8;
    private Item myTestItem9;
    private Item myTestItem10;
    private Item myTestItem11;

    private Bidder myTestBidder;
    private Auction myAuctionOnTodayDate;
    private Auction myAuctionOnTomorrowDate;
    private Auction myAuctionOnDateTwoDaysInFuture;
    private Auction myAuctionOnDateTenDaysInFuture;
    private GregorianCalendar myAuctionDateToday = (GregorianCalendar)GregorianCalendar.getInstance();
    private GregorianCalendar myAuctionDateTomorrow = (GregorianCalendar)GregorianCalendar.getInstance();
    private GregorianCalendar myAuctionDateTwoDaysInFuture = (GregorianCalendar)GregorianCalendar.getInstance();
    private GregorianCalendar myAuctionDateTenDaysInFuture = (GregorianCalendar)GregorianCalendar.getInstance();
    private  Calendar myCalendar = new Calendar();
    /**
     * Initializes the myTestBidder object before every test.
     */
    @Before
    public void setUp() {

    	//Setup of test auction on today's date.
    	myAuctionDateToday.add(java.util.Calendar.MINUTE, +1);
    	myAuctionOnTodayDate = new Auction(myAuctionDateToday ,"testAuctionOnTodayDate");
    	myTestItem = new Item("ID: 1", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myTestItem2 = new Item("ID: 2", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myCalendar.devOnlyAddAuctionByPassValidation(myAuctionOnTodayDate);
    	myTestBidder = new Bidder("bid4lyfe", myCalendar );
    	myAuctionOnTodayDate.addItem(myTestItem2);
     	myTestBidder.placeBid(myTestItem2, 8.00);

     	//Setup of test auction on tomorrow's date.
     	myAuctionDateTomorrow.add(java.util.Calendar.DATE, +1);
     	myAuctionDateTwoDaysInFuture.add(java.util.Calendar.MINUTE, +1);
     	myAuctionOnTomorrowDate = new Auction( myAuctionDateTomorrow ,"testAuctionOnTomorrowDate");
    	myTestItem3 = new Item("ID:100", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myTestItem4 = new Item("ID:200", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myCalendar.devOnlyAddAuctionByPassValidation(myAuctionOnTomorrowDate);
    	myTestBidder = new Bidder("bid4lyfe", myCalendar );
    	myAuctionOnTomorrowDate.addItem(myTestItem4);
     	myTestBidder.placeBid(myTestItem4, 8.00);


     	//Setup of test auction two days into the future of today's date.
     	myAuctionDateTwoDaysInFuture.add(java.util.Calendar.DATE, +2);
     	myAuctionDateTwoDaysInFuture.add(java.util.Calendar.MINUTE, +1);
     	myAuctionOnDateTwoDaysInFuture = new Auction( myAuctionDateTwoDaysInFuture ,"testAuctionOnDateTwoDaysInFuture");
    	myTestItem5 = new Item("101", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myTestItem6 = new Item("202", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myTestItem7 = new Item("303", "Voltron", "Brand-new Voltron", "Small", 35.00, 1, "New in box");
    	myCalendar.devOnlyAddAuctionByPassValidation(myAuctionOnDateTwoDaysInFuture);
    	myTestBidder = new Bidder("bid4lyfe", myCalendar );
    	myAuctionOnDateTwoDaysInFuture.addItem(myTestItem6);
     	myTestBidder.placeBid(myTestItem6, 8.00);
     	myAuctionOnDateTwoDaysInFuture.addItem(myTestItem7);
     	myTestBidder.placeBid(myTestItem7, 35.00);

    	//Setup of test auction ten days into the future of today's date.
     	myAuctionDateTenDaysInFuture.add(java.util.Calendar.DATE, +10);
     	myAuctionDateTenDaysInFuture.add(java.util.Calendar.MINUTE, +1);
     	myAuctionOnDateTenDaysInFuture = new Auction( myAuctionDateTenDaysInFuture ,"testAuctionOnDateTenDaysInFuture");
    	myTestItem8 = new Item("808", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myTestItem9 = new Item("909", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	myTestItem10 = new Item("1010", "Voltron", "Brand-new Voltron", "Small", 35.00, 1, "New in box");
    	myTestItem11 = new Item("1111", "Voltron2", "Brand-new Voltron", "Small", 35.00, 1, "New in box");
    	myCalendar.devOnlyAddAuctionByPassValidation(myAuctionOnDateTenDaysInFuture);
    	myTestBidder = new Bidder("bid4lyfe", myCalendar );
    	myAuctionOnDateTenDaysInFuture.addItem(myTestItem8);
     	myTestBidder.placeBid(myTestItem8, 8.00);

     	myAuctionOnDateTenDaysInFuture.addItem(myTestItem10);
     	myTestBidder.placeBid(myTestItem10, 35.00);
     	myAuctionOnDateTenDaysInFuture.addItem(myTestItem11);
     	myTestBidder.placeBid(myTestItem11, 36.00);

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
     * Test method to test that a bid can't be cancelled exactly on the day of an auction.
     * @author Robert hinds
     */
    @Test
    public void testValidateCancelBidGreaterThanDayLimitBidCanNotbeCancelledDayofAuctionStartDate(){
    	//Cancelling of a bid on the day of auction...NOT ALLOWED
    	assertFalse(myTestBidder.validateCancelBidGreaterThanDayLimit(myTestItem2, myTestBidder));
    }

    /**
     * Test method to test that a bid can't be cancelled within exactly one day of an auction's
     * start date.
     * @author Robert hinds
     */
    @Test
    public void testValidateCancelBidGreaterThanDayLimitBidCanNotbeCancelledWithinOneDayofAuctionStartDate(){
    	//Cancelling of a bid within one day of auction start date...NOT ALLOWED
    	assertFalse(myTestBidder.validateCancelBidGreaterThanDayLimit(myTestItem4, myTestBidder));
    }

    /**
     * Test method to test that a bid can be cancelled within exactly two days of an auction's
     * start date.
     * @author Robert hinds
     */
    @Test
    public void testBidCanbeCancelledWithinTwoDaysofAuctionStartDate(){
    	//Canceling of a bid within exactly two days of auction start date... ALLOWED
    	assertTrue(myTestBidder.validateCancelBidGreaterThanDayLimit(myTestItem7, myTestBidder));
    }

    /**
     * Test method to test that a bid can be cancelled within exactly ten days of an auction's
     * start date.
     * @author Robert hinds
     */
    @Test
    public void testBidCanbeCancelledWithinTenDaysofAuctionStartDate(){
    	//Canceling of a bid within two days of auction start date...ALLOWED
    	assertTrue(myTestBidder.validateCancelBidGreaterThanDayLimit(myTestItem10, myTestBidder));
    }

    /**
     * Test method to test that a bid can't be canceled without having
     * a bid placed prior.
     * @author Robert hinds
     */
    @Test
    public void testCanelBidCancelledWithoutBidPlace(){
    	//Cancelling a bid without having a prior bid...NOT ALLOWED
    	assertFalse(myTestBidder.cancelBid(myTestItem9, myTestBidder));
    }

    /**
     * Test method to test that a bid can be canceled if a prior bid exists
     * by the given bidder on the given item.
     * @author Robert hinds
     */
    @Test
    public void testCancelBid(){
    	//Cancelling the correct bid... ALLOWED
    	assertTrue(myTestBidder.cancelBid(myTestItem10, myTestBidder));

    	//Cancel bid. Bid not canceled...NOT ALLOWED
    	assertFalse(myTestBidder.cancelBid(myTestItem11, myTestBidder));

    }

    /**
     * Test method to test that previously cancelled does not remove other
     * bids made by the bidder.
     * @author Robert hinds
     */
    @Test
    public void testCancelBidToShowCancelBidDidNotRemoveOtherBids(){
    	//

    	//Cancelling the correct bid did not cancel different bid in same auction...Allowed
    	assertEquals(myTestBidder.getUsername(), myTestItem8.getBidOf(myTestBidder.getUsername()).getBidder());

    	//Cancelling the correct bid did not cancel different bid in another auction...Allowed
    	assertEquals(myTestBidder.getUsername(), myTestItem2.getBidOf(myTestBidder.getUsername()).getBidder());
    }


    /**
     * Test method to ensure hasAuctionPassedTwoDayCutoffPoint() returns true if the given Item's auction
     * is within two days of today's date.
     *
     * @author Iriohm
     */
    @Test
    public void testHasAuctionPassedTwoDayCutoffPointWithinTwoDays() {
    	GregorianCalendar gcDate = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcDate.add(5, 1);
		Auction auction = new Auction(gcDate, "Lady's Auction");
		Item item = new Item("000", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");
		auction.addItem(item);

		assertTrue(Bidder.hasAuctionPassedTwoDayCutoffPoint(item));

    }


    /**
     * Test method to ensure hasAuctionPassedTwoDayCutoffPoint() returns true if the given Item's auction
     * is today.
     *
     * @author Iriohm
     */
    @Test
    public void testHasAuctionPassedTwoDayCutoffPointIsToday() {
    	GregorianCalendar gcDate = (GregorianCalendar) GregorianCalendar.getInstance();
		Auction auction = new Auction(gcDate, "Lady's Auction");
		Item item = new Item("000", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");
		auction.addItem(item);

		assertTrue(Bidder.hasAuctionPassedTwoDayCutoffPoint(item));

    }


    /**
     * Test method to ensure hasAuctionPassedTwoDayCutoffPoint() returns false if the given Item's auction
     * has not passed, and is exactly two days away.
     *
     * @author Iriohm
     */
    @Test
    public void testHasAuctionPassedTwoDayCutoffPointSitsOnTwoDays() {
    	GregorianCalendar gcDate = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcDate.add(java.util.Calendar.DAY_OF_MONTH, 2);
		Auction auction = new Auction(gcDate, "Lady's Auction");
		Item item = new Item("000", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");
		auction.addItem(item);

		assertFalse(Bidder.hasAuctionPassedTwoDayCutoffPoint(item));

    }


    /**
     * Test method to ensure hasAuctionPassedTwoDayCutoffPoint() returns false if the given Item's auction
     * has not passed, and is more than two days away.
     *
     * @author Iriohm
     */
    @Test
    public void testHasAuctionPassedTwoDayCutoffPointHasNotPassed() {
    	GregorianCalendar gcDate = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcDate.add(java.util.Calendar.DAY_OF_MONTH, 5);
		Auction auction = new Auction(gcDate, "Lady's Auction");
		Item item = new Item("000", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");
		auction.addItem(item);

		assertFalse(Bidder.hasAuctionPassedTwoDayCutoffPoint(item));

    }

}
