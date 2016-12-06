package testing;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import junit.framework.*;
import model.Auction;
import model.Bidder;
import model.Calendar;
import model.Item;
import model.NPContact;

public class NPContactTest extends TestCase {
	
	private NPContact myTestContact;
	/**
     * Initializes the myTestContact object before every test.
     */
    @Before
    public void setUp() {
    	//(String theID, String theName, String theDescription, double theMinimumBid,
		//		int theQuantity, String theCondition) {
    	myTestContact = new NPContact("sdf", new Calendar());
    }

    /**
     * Test method for addAuction() and getLatestAuction().
     */
    @Test
    public void testAddAuction() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	Auction auction1 = new Auction(new GregorianCalendar(), "");
    	myTestContact.addAuction(auction1);
    	assertEquals(myTestContact.getLatestAuction(), auction1);

    }
    
    /**
     * Test method for hasAuctionUpcomingOrLastYear().
     */
    @Test
    public void testHasAuctionUpcomingOrLastYear() {
    	//Placing a valid bid on myTestItem...ALLOWED
    	Auction auction1 = new Auction(new GregorianCalendar(2016, 11, 22, 5, 0), "");
    	myTestContact.addAuction(auction1);
    	assertTrue(myTestContact.hasAuctionUpcomingOrLastYear());
    }
    
    /**
     * Test method for addItem().
     */
    @Test
    public void testAddItem() {
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	Auction auctionToAddTo = new Auction(new GregorianCalendar(), "");
    	assertTrue(myTestContact.addItem(auctionToAddTo, aTestItem));
    	
    	assertTrue(myTestContact.addItem(auctionToAddTo, aTestItem2));
    	
    	assertFalse(myTestContact.addItem(auctionToAddTo, aTestItem));
    }
    
    
    @Test
    public void testRemoveMyAuction() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction);
    	myTestContact.addAuction(testAuction);
    	assertEquals(0, myTestContact.removeMyAuction(aCalender, testAuction));
    }
    
    
    @Test
    public void testRemoveMyAuctionNPNoAuctions() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction);
//    	myTestContact.addAuction(testAuction);
    	assertEquals(-3, myTestContact.removeMyAuction(aCalender, testAuction));
    }
    
    @Test
    public void testRemoveMyAuctionSomeOneElesNPAuctions() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	Auction testAuction = new Auction(date, "im a auctions");
    	Auction testAuction2 = new Auction(date, "im someone else");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction);
    	aCalender.addAuction(testAuction2);
    	myTestContact.addAuction(testAuction);
    	NPContact someone = new NPContact("someone", aCalender);
    	someone.addAuction(testAuction2);
    	assertEquals(-4, myTestContact.removeMyAuction(aCalender, testAuction2));
    }
    
    @Test
    public void testRemoveMyAuctionTwoDayWay() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 2);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction);
    	myTestContact.addAuction(testAuction);
    	assertEquals(-1, myTestContact.removeMyAuction(aCalender, testAuction));
    }
    
    @Test
    public void testRemoveMyAuctionNoAuctionInCalendar() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	Auction testAuction = new Auction(date, "im a auctions");
    	Auction testAuction2 = new Auction(date, "im someone else");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction2);
    	myTestContact.addAuction(testAuction);
    	assertEquals(-2, myTestContact.removeMyAuction(aCalender, testAuction));
    }
    
    @Test
    public void testRemoveMyItem() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction);
    	myTestContact.addAuction(testAuction);
    	assertEquals(0, myTestContact.removeMyItem(aCalender, testAuction, aTestItem));
    }
    
    @Test
    public void testRemoveMyItemTwoDaysWay() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 2);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction);
    	myTestContact.addAuction(testAuction);
    	assertEquals(-1, myTestContact.removeMyItem(aCalender, testAuction, aTestItem));
    }
    
    @Test
    public void testRemoveMyItemNoNoneProfitAuction() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	Auction testAuction = new Auction(date, "im a auctions");
    	Auction testAuction2 = new Auction(date, "im someone else");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction2);
    	assertEquals(-3, myTestContact.removeMyItem(aCalender, testAuction, aTestItem));
    }
    
    @Test
    public void testRemoveMyItemSomeOneElesNPAuctions() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	Auction testAuction = new Auction(date, "im a auctions");
    	Auction testAuction2 = new Auction(date, "im someone else");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	aCalender.addAuction(testAuction);
    	aCalender.addAuction(testAuction2);
    	myTestContact.addAuction(testAuction);
    	NPContact someone = new NPContact("someone", aCalender);
    	someone.addAuction(testAuction2);
    	assertEquals(-4, myTestContact.removeMyItem(aCalender, testAuction2, aTestItem));
    }
}
