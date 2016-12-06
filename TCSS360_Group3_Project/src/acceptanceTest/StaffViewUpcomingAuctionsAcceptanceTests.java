package acceptanceTest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class StaffViewUpcomingAuctionsAcceptanceTests {

	private Calendar aCalender;
	private int numberOfAuction = 22;
	/**
     * Initializes the myTestContact object before every test.
     */
    @Before
    public void setUp() {
    	aCalender = new Calendar();
		int changeDays = 0;
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		for (int i = 0; i < numberOfAuction; i++) {
			Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
			Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
			tesAuction.addItem(testItem);
			aCalender.addAuction(tesAuction);
			if(changeDays == 1){
				changeDays = 0;
				fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 1);
			}
			changeDays++;
		}
    	
    }
	
	@Test
	public void testNumberAuctionsSystem() {
		assertEquals(numberOfAuction, aCalender.getUpcomingAuctionsNumber());
	}
	
	//date of auctions in system this will be done in the UI.

}
