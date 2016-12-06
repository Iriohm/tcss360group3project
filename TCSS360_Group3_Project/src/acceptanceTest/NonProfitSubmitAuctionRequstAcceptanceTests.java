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
	
	//Business rule: only the contact person for this non-profit organization can submit an auction request.
	// is handled by UI
	
	@Test
	public void testMaximumOneFutureAuctionOneFutureAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 12);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		myTestContact.addAuction(tesAuction, aCalender);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
		tesAuction2.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction2, aCalender));
		assertEquals(1, aCalender.getUpcomingAuctionsNumber());
	}
//	
//	// can't add auction past two
//	
	@Test
	public void testNoAuctionWithinPastYearOneAutionYearMinusDaySubmittingNewAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 15);
		fuctureAuction.add(GregorianCalendar.YEAR, -1);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		myTestContact.addAuction(tesAuction);//testing only
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 1);
		fuctureAuction.add(GregorianCalendar.YEAR, 1);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
		tesAuction2.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction2, aCalender));
	}
	
	@Test
	public void testNoAuctionWithinPastYearOneAutionYearSubmittingNewAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.YEAR, -1);
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 15);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		myTestContact.addAuction(tesAuction);//testing only
		fuctureAuction.add(GregorianCalendar.YEAR, 1);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
		tesAuction2.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction2, aCalender));
	}
	
	@Test
	public void testNoAuctionWithinPastYearOneAutionLessThenYearSubmittingNewAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, -255);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		myTestContact.addAuction(tesAuction);
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 275);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
		tesAuction2.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction2, aCalender));
	}
	
	//4
	@Test
	public void testNoAuctionWithinPastYearOneAutionGreaterThenYearSubmittingNewAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 16);
		fuctureAuction.add(GregorianCalendar.YEAR, 1);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		myTestContact.addAuction(tesAuction);
		fuctureAuction.add(GregorianCalendar.YEAR, -1);
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, -1);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
		tesAuction2.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction2, aCalender));
	}
	
	//5
	@Test
	public void testNoAuctionWithinPastYearOneAutionExaclyYearOutNewAuctionSubmitting() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.YEAR, 1);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		myTestContact.addAuction(tesAuction);
		fuctureAuction.add(GregorianCalendar.YEAR, -1);
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 16);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions");
		tesAuction2.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction2, aCalender));
	}
	
	@Test
	public void testNoAuctionWithinPastYearSubmittingNewAuction() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 16);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximumTwoAuctionPerDayNoAuctionScheduledForThatDay() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 17);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximumTwoAuctionPerDayOneAuctionScheduledForThatDay() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		NPContact oneSomeDay = new NPContact("sameDay", aCalender);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions2");
		tesAuction2.addItem(testItem);
		oneSomeDay.addAuction(tesAuction2, aCalender);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximumTwoAuctionPerDayTwoAuctionScheduledForThatDay() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 17);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		NPContact oneSomeDay = new NPContact("sameDay", aCalender);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions2");
		tesAuction2.addItem(testItem);
		oneSomeDay.addAuction(tesAuction2, aCalender);
		NPContact oneSomeDay2 = new NPContact("sameDay", aCalender);
		Auction tesAuction3 = new Auction(fuctureAuction, "testAuctions2");
		tesAuction3.addItem(testItem);
		oneSomeDay2.addAuction(tesAuction3, aCalender);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximumTwoAuctionPerDayThreeAuctionScheduledForThatDay() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		NPContact oneSomeDay = new NPContact("sameDay", aCalender);
		Auction tesAuction2 = new Auction(fuctureAuction, "testAuctions2");
		tesAuction2.addItem(testItem);
		oneSomeDay.addAuction(tesAuction2, aCalender);
		NPContact oneSomeDay2 = new NPContact("sameDay", aCalender);
		Auction tesAuction3 = new Auction(fuctureAuction, "testAuctions2");
		tesAuction3.addItem(testItem);
		oneSomeDay2.addAuction(tesAuction3, aCalender);
		NPContact oneSomeDay3 = new NPContact("sameDay", aCalender);
		Auction tesAuction4 = new Auction(fuctureAuction, "testAuctions2");
		tesAuction4.addItem(testItem);
		assertFalse(oneSomeDay3.addAuction(tesAuction3, aCalender));
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximum25UpcomingAuctionsLess24Auctions() {
		int changeDays = 0;
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		for (int i = 0; i < 22; i++) {
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
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximum25UpcomingAuctions24Auctions() {
		int changeDays = 0;
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		for (int i = 0; i < 24; i++) {
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
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximum25UpcomingAuctions25Auctions() {
		int changeDays = 0;
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		for (int i = 0; i < 25; i++) {
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
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testMaximum25UpcomingAuctionsGreater25Auctions() {
		int changeDays = 0;
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		for (int i = 0; i < 27; i++) {
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
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionCannotScheduledMoreThanMonthScheduledExactlyOneMonth() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.MONTH, 1);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionCannotScheduledMoreThanMonthScheduledLessOneMonth() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 19);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionCannotScheduledMoreThanMonthScheduledOneMonthOneDay() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.MONTH, 1);
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionCannotScheduledMoreThanMonthScheduledMoreOneMonth() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.MONTH, 1);
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 19);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	//Business rule: all required fields must be specified at the time an auction is submitted. These fields are: auction date (formatted date: DD/MM/YYYY), auction time (formatted time: HH [AM/PM]). Optional (non-required) fields are: approximate number of inventory items (positive integer), comment (string).
    // is headed by the UI
	
	@Test
	public void testAuctionDateLeastOneWeekOutSubmittingAcutionEcavtlyOneWeek() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.WEEK_OF_YEAR, 1);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionDateLeastOneWeekOutSubmittingAcutionGreaterOneWeek() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 8);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertTrue(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionDateLeastOneWeekOutSubmittingAcutionExactlySixDay() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 6);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionDateLeastOneWeekOutSubmittingAcutionLessSixDay() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, 3);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionDateLeastOneWeekOutSubmittingAcutionToday() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
	
	@Test
	public void testAuctionDateLeastOneWeekOutSubmittingAcutionPast() {
		GregorianCalendar fuctureAuction = (GregorianCalendar)GregorianCalendar.getInstance();
		fuctureAuction.add(GregorianCalendar.DAY_OF_YEAR, -3);
		Auction tesAuction = new Auction(fuctureAuction, "testAuctions");
		Item testItem = new Item("1", "testname", "avnsdovn", "smaill", 5.00, 1, "good");
		tesAuction.addItem(testItem);
		assertFalse(myTestContact.addAuction(tesAuction, aCalender));
	}
}
