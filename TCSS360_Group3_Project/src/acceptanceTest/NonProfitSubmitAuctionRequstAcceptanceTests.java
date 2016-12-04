package acceptanceTest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class NonProfitSubmitAuctionRequstAcceptanceTests {
	private Calendar aCalender;
	private NPContact myTestContact;
	/**
     * Initializes the myTestContact object before every test.
     */
    @Before
    public void setUp() {
    	aCalender = new Calendar();
    	myTestContact = new NPContact("test", aCalender);
    }
	
//	@Test
//	public void testMaximumOneFutureAuctionNoFutureAuction() {
//		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
//		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 12);
//		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
//		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
//		tesAuction.addItem(testItem);
//		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
//	}
//	
//	//Business rule: only the contact person for this non-profit organization can submit an auction request.
//	// is handed by UI
//	
//	@Test
//	public void testMaximumOneFutureAuctionOneFutureAuction() {
//		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
//		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 12);
//		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
//		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
//		tesAuction.addItem(testItem);
//		myTestContact.addAuction(tesAuction, aCalender);
//		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
//		tesAuction2.addItem(testItem);
//		assertFalse(myTestContact.addAuction(tesAuction2, aCalender));
//		assertEquals(1, aCalender.getUpcomingAuctionsNumber());
//	}
//	
//	// con't add auction past two
//	
	@Test
	public void testNoAuctionWithinPastYearOneAutionYearMinusDaySubmittingNewAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, -1);
		fuctureAuction.add(GregorianCalendar.YEAR, -1);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		myTestContact.addAuction(tesAuction, aCalender);
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, +1);
		fuctureAuction.add(GregorianCalendar.YEAR, 1);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
		tesAuction2.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction2, aCalender));
	}
}
