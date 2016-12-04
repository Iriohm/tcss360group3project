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
	
	@Test
	public void testMaximumOneFutureAuctionNoFutureAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 12);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}

}
