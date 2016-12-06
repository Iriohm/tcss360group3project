package acceptanceTest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class NonProfitRemoveInventoryItemAcceptanceTests {


	private NPContact myTestContact;
	private Calendar aCalender;
	private Auction testAuction;
	private GregorianCalendar date;
	
	/**
     * Initializes the myTestContact object before every test.
     */
    @Before
    public void setUp() {
    	aCalender = new Calendar();
    	myTestContact = new NPContact("sdf", aCalender);
//    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
//    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
//    	date = (GregorianCalendar) GregorianCalendar.getInstance();
//    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
//    	testAuction = new Auction(date, "im a auctions");
//    	testAuction.addItem(aTestItem2);
//    	testAuction.addItem(aTestItem);
    }

	/*
	 * 0 if it dose remove the Auction
	 * -1 is if the auction is two days are less
	 */

    
    @Test
    public void testNoItemRemovedLess2DayItemRemovedExactly2Day() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 2);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	myTestContact.addAuction(testAuction);
    	aCalender.addAuctionForTesting(testAuction);
    	assertEquals(0, myTestContact.removeMyItem(aCalender, testAuction,aTestItem));
    }
    
    @Test
    public void testNoItemRemovedLess2DayItemRemovedExactly1Day() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 1);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	myTestContact.addAuction(testAuction);
    	aCalender.addAuctionForTesting(testAuction);
    	assertEquals(-1, myTestContact.removeMyItem(aCalender, testAuction,aTestItem));
    }
    
    @Test
    public void testNoItemRemovedLess2DayItemRemovedExactly10Day() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 10);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	myTestContact.addAuction(testAuction,aCalender);
    	assertEquals(0, myTestContact.removeMyItem(aCalender, testAuction,aTestItem));
    }
}
