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
    	//
    }
}
