package acceptanceTest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Bidder;
import model.Calendar;
import model.Item;

public class BidderBidItemAccptanceTests {
	/** An Item object that will be used to test the Bidder class. */
    private Item myTestItem;
    private Item myTestItem2;

    private Bidder myTestBidder;
    private Auction myAuctionOnTodayDate;
    private GregorianCalendar myAuctionDateToday = (GregorianCalendar)GregorianCalendar.getInstance();
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

    	
    }
	
	//TODO: cheek the past and future auction
	@Test
	public void testAuctionDateCurrentDayOrPast() {
		Auction todayAuction = new Auction((GregorianCalendar)GregorianCalendar.getInstance(), "today test auction");
		Item testItem = new Item("1", "test", "im im a test dont dalet", "smail", 10.00, 1, "good");
		todayAuction.addItem(testItem);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidPriceBidZero() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	assertFalse(myTestBidder.placeBid(myTestItem, 0));
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidPriceBidNegative() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	assertFalse(myTestBidder.placeBid(myTestItem, -1));
    }
	
	@Test
	public void testValidPriceBidGreaterMinAcceptable() {
    	//Placing a valid bid on myTestItem...ALLOWED
		myTestBidder.placeBid(myTestItem, 5.01);
    	assertEquals("",5.01, myTestItem.getBidOf(myTestBidder.getUsername()).getBidAmount(),.001);
    }
	
	@Test
	public void testValidPriceBidEqualMinAcceptable() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	myTestBidder.placeBid(myTestItem, 5.00);
    	assertEquals("",5.00, myTestItem.getBidOf(myTestBidder.getUsername()).getBidAmount(),.001);

    }
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidPriceBidLessMinAcceptable() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	assertFalse(myTestBidder.placeBid(myTestItem, 4.99));
    }
}
