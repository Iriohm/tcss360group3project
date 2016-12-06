package acceptanceTest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class NonProfitCancelAuctionRequestAcceptanceTests {

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
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 12);
    	testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    }

	

    
    @Test
    public void testNoAuctionCanceledLess2DayAuctionCancelledExactly2Day() {
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
    	assertEquals(0, myTestContact.removeMyAuction(aCalender, testAuction));
    }
    
    @Test
    public void testNoAuctionCanceledLess2DayAuctionCancelledExactly1Day() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 1);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	myTestContact.addAuction(testAuction);
    	aCalender.addAuction(testAuction);
    	assertEquals(-1, myTestContact.removeMyAuction(aCalender, testAuction));
    }
    
    @Test
    public void testNoAuctionCanceledLess2DayAuctionCancelledExactly10Day() {
    	Calendar aCalender = new Calendar();
    	Item aTestItem = new Item("theID", "Beanie Baby", "Brand-new Beanie Baby", "Small", 5.00, 1, "New in box");
    	Item aTestItem2 = new Item("ID2", "Seahawks shirt", "Seahawks shirt", "Small", 5.00, 1, "New");
    	GregorianCalendar date = (GregorianCalendar) GregorianCalendar.getInstance();
    	date.add(GregorianCalendar.DAY_OF_YEAR, 10);
    	Auction testAuction = new Auction(date, "im a auctions");
    	testAuction.addItem(aTestItem2);
    	testAuction.addItem(aTestItem);
    	myTestContact.addAuction(testAuction,aCalender);
    	assertEquals(0, myTestContact.removeMyAuction(aCalender, testAuction));
    }
    
  @Test
  public void testRemoveMyAuction() {
  	myTestContact.addAuction(testAuction,aCalender);
  	assertEquals(0, myTestContact.removeMyAuction(aCalender, testAuction));
  }
  
  @Test
  public void testRemoveMyAuctionSomeOneElesNPAuctions() {
  	Auction testAuction2 = new Auction(date, "im someone else");
  	aCalender.addAuction(testAuction2);
  	myTestContact.addAuction(testAuction,aCalender);
  	NPContact someone = new NPContact("someone", aCalender);
  	someone.addAuction(testAuction2);
  	assertEquals(-4, myTestContact.removeMyAuction(aCalender, testAuction2));
  }

}
