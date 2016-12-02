package testing;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;
import org.junit.Test;

import junit.framework.*;
import model.Auction;
import model.Calendar;
import model.Item;
import model.Staff;




/**
 * The Calendar class takes care of keeping track of Auction requests and holds all of the Auctions.
 * This class tests its logic
 * 
 * @author Justin Washburn
 * @version 11/10/2016
 *
 */

public class CalendarTest extends TestCase {
	
	private Calendar calendarTest;
	private Calendar calendarTests2345;
	private Auction auctionOne;
	private Auction auctionSix;
	private Auction auctionSeven;
	private Calendar testCalendar;
	private Calendar testCalendar2;
	private GregorianCalendar aNewDate;
	private Auction testAuction;
	private Item testItem1;
	private Item testItem2;
	
	/**
	 * Creates and adds all auctions to a Calendar
	 * necessary for a before, after, during, and boundary case.
	 */
	@Before
	public void setUp() {
		
		List<Auction> testAuctions = new LinkedList<Auction>();
		List<Auction> testAuctions2345 = new LinkedList<Auction>();
		List<Auction> testAuctions167 = new LinkedList<Auction>();
		
		//2000-1-1
		GregorianCalendar aDate = new GregorianCalendar(2000, 1, 1);
		aDate.add(GregorianCalendar.HOUR_OF_DAY, 1);
		Auction testAuction1 = new Auction(((GregorianCalendar)aDate.clone()), "Test1");
		testAuctions.add(testAuction1); 
		testAuctions167.add(testAuction1); 
		auctionOne = testAuction1;
		
		//2000-1-2
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction testAuction2 = new Auction(((GregorianCalendar)aDate.clone()), "Test2");
		testAuctions.add(testAuction2); 
		testAuctions2345.add(testAuction2); 
		
		//2000-1-12
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		Auction testAuction3 = new Auction(((GregorianCalendar)aDate.clone()), "Test3");
		testAuctions.add(testAuction3);  
		testAuctions2345.add(testAuction3); 
		
		//2000-1-22
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		Auction testAuction4 = new Auction(((GregorianCalendar)aDate.clone()), "Test4");
		testAuctions.add(testAuction4);  
		testAuctions2345.add(testAuction4); 
		
		//2000-1-32
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		Auction testAuction5 = new Auction(((GregorianCalendar)aDate.clone()), "Test5");
		testAuctions.add(testAuction5); 
		testAuctions2345.add(testAuction5); 
		
		//2000-1-33
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction testAuction6 = new Auction(((GregorianCalendar)aDate.clone()), "Test6");
		testAuctions.add(testAuction6); 
		testAuctions167.add(testAuction6); 
		auctionSix = testAuction6;
		
		//2000-1-34
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction testAuction7 = new Auction(((GregorianCalendar)aDate.clone()), "Test7");
		testAuctions.add(testAuction7); 
		testAuctions167.add(testAuction7); 
		auctionSeven = testAuction7;
		
		calendarTest = new Calendar(testAuctions);
		calendarTests2345 = new Calendar(testAuctions2345);
		new Calendar(testAuctions167);
		
		//used in testSetMaxAuctions.. methods
		testCalendar2 = new Calendar();
	
		aNewDate = (GregorianCalendar)GregorianCalendar.getInstance();
		for(int i = 0; i < testCalendar2.getMaxAuctionsLimit();i++){
		aNewDate.add(GregorianCalendar.DATE, i);
		testAuction = new Auction(aNewDate, "test");
		testCalendar2.devOnlyAddAuctionByPassValidation(testAuction);
		}
		
	}
	
	/**
	 * Tests to ensure that the getAuctions function return will include the proper auctions in 
	 * the event of both boundary cases, an auction within the boundaries, and an auction
	 * past each side of the boundaries.
	 */
	@Test
	public void testGetAuctionsIncludes() {
		GregorianCalendar aDate = new GregorianCalendar(2000, 1, 2);
		List<Auction> auctions;
		auctions = calendarTest.getAuctions(aDate);
		assertTrue(auctions.containsAll(calendarTests2345.getAllAuctions()));
	
	}
	
	/**
	 * Tests to ensure that the getAuctions function return will exclude the proper auctions in 
	 * the event of both boundary cases, an auction within the boundaries, and an auction
	 * past each side of the boundaries.
	 */
	@Test
	public void testGetAuctionsExcludes() {
		GregorianCalendar aDate = new GregorianCalendar(2000, 1, 2);
		List<Auction> auctions;
		auctions = calendarTest.getAuctions(aDate);
		assertFalse(auctions.contains(auctionOne));
		assertFalse(auctions.contains(auctionSix));
		assertFalse(auctions.contains(auctionSeven));
	}
	
	/**
	 * Tests to ensure that a getAuction request will return an empty list if
	 * the date given is after every Auction on the Calendar
	 */
	@Test
	public void testGetAuctionsEmptyIfDateIsAfterAllAuctions() {
		GregorianCalendar aDate = new GregorianCalendar(2001, 1, 2);
		List<Auction> auctions;
		auctions = calendarTest.getAuctions(aDate);
		assertTrue(auctions.isEmpty());
	}
	
	/**
	 * Tests to ensure that a getAuction request will return an empty list if
	 * the date given is more than 31 days before every Auction on the Calendar
	 */
	@Test
	public void testGetAuctionsEmptyIfDateIsBeforeAllAuctions() {
		GregorianCalendar aDate = new GregorianCalendar(1999, 1, 2);
		List<Auction> auctions;
		auctions = calendarTest.getAuctions(aDate);
		assertTrue(auctions.isEmpty());
	}
	
	/**
	 * Tests to make sure that no Auctions in the Year 2000 will show as upcoming in 2016
	 */
	@Test
	public void testGetUpcomingAuctionsNumberZero() {
		assertEquals(0, calendarTest.getUpcomingAuctionsNumber());
	}
	
	
	/**
	 * Tests to make sure that when 3 Auctions are upcoming they will be shown as upcoming
	 */
	@Test
	public void testGetUpcomingAuctionsNumber3() {
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 7);
		
		Auction upcomingAuction1 = new Auction(aDate, "test");
		
		calendarTest.addAuction(upcomingAuction1);
		
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction upcomingAuction2 = new Auction(aDate, "test");
		calendarTest.addAuction(upcomingAuction2);
		
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction upcomingAuction3 = new Auction(aDate, "test");
		calendarTest.addAuction(upcomingAuction3);
		
		assertEquals(3, calendarTest.getUpcomingAuctionsNumber());// 0 is first 1 is second 2 is threed.
	}
	
	/**
	 * Tests validating the first auction on a day
	 */
	@Test
	public void testValidateAuctionRequestTwoPerDayFirstOnDay() {
		Calendar testCalendar = new Calendar();
		
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 8);
		assertTrue(testCalendar.validateAuctionRequestTwoPerDay(aDate));
		Auction upcomingAuction1 = new Auction(aDate, "test");
		assertTrue(testCalendar.addAuction(upcomingAuction1));

		
	}
	
	
	/**
	 * Tests validating the first auction on a day and the second
	 */
	@Test
	public void testValidateAuctionRequestTwoPerDaySecondOnDay() {
		Calendar testCalendar = new Calendar();
		
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 8);
		assertTrue(testCalendar.validateAuctionRequestTwoPerDay(aDate));
		Auction upcomingAuction1 = new Auction(aDate, "test");
		assertTrue(testCalendar.addAuction(upcomingAuction1));
		
		//Get instance for a  date a couple hours after the first date
		aDate.add(GregorianCalendar.HOUR_OF_DAY, 2);
		assertTrue(testCalendar.validateAuctionRequestTwoPerDay(aDate));
		Auction upcomingAuction2 = new Auction(aDate, "test");
		assertTrue(testCalendar.addAuction(upcomingAuction2));
		
	}
	
	/**
	 * Tests validating the first auction on a day and the second and makes sure the third fails
	 */
	@Test
	public void testValidateAuctionRequestTwoPerDayMoreThanTwoOnDay() {
		Calendar testCalendar = new Calendar();
		
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.set(GregorianCalendar.HOUR, 0);
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 8);
		assertTrue(testCalendar.validateAuctionRequestTwoPerDay(aDate));
		Auction upcomingAuction1 = new Auction(aDate, "test");
		assertTrue(testCalendar.addAuction(upcomingAuction1));
		
		//Get instance for a  date a couple hours after the current date
		aDate.add(GregorianCalendar.HOUR_OF_DAY, 2);
		assertTrue(testCalendar.validateAuctionRequestTwoPerDay(aDate));
		Auction upcomingAuction2 = new Auction(aDate, "test");
		assertTrue(testCalendar.addAuction(upcomingAuction2));
		
		aDate.add(GregorianCalendar.HOUR_OF_DAY, 2);
		assertFalse(testCalendar.validateAuctionRequestTwoPerDay(aDate));
		Auction upcomingAuction3 = new Auction(aDate, "test");
		assertFalse(testCalendar.addAuction(upcomingAuction3));
		
		aDate.add(GregorianCalendar.HOUR_OF_DAY, 2);
		assertFalse(testCalendar.validateAuctionRequestTwoPerDay(aDate));
		Auction upcomingAuction4 = new Auction(aDate, "test");
		assertFalse(testCalendar.addAuction(upcomingAuction4));
		
	}
	
	/**
	 * Tests to make sure you can add an auction exactly one month in the future or between
	 *  the current time and one month in the future
	 */
	@Test
	public void testValidateAuctionRequestAtMostOneMonthInFutureExactlyOneMonthOrLess() {
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.MONTH, 1);
		//Exactly one month
		assertTrue(calendarTest.validateAuctionRequestAtMostOneMonthInFuture(aDate));
		aDate.add(GregorianCalendar.DAY_OF_MONTH, -10);
		assertTrue(calendarTest.validateAuctionRequestAtMostOneMonthInFuture(aDate));
	}
	
	/**
	 * Tests to make sure you cannot add an auction exactly one month and one day in the future or 
	 *  after that date
	 */
	@Test
	public void testValidateAuctionRequestAtMostOneMonthInFutureExactlyOneMonthAndOneDayOrMore() {
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.MONTH, 1);
		aDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
		//Exactly one month and one day
		assertFalse(calendarTest.validateAuctionRequestAtMostOneMonthInFuture(aDate));
		aDate.add(GregorianCalendar.DAY_OF_MONTH, 10);
		assertFalse(calendarTest.validateAuctionRequestAtMostOneMonthInFuture(aDate));
	}
	/**
	 * Tests to make sure that a Date less than one week ahead will not work for an auction
	 */
	@Test
	public void testdateAtLeastOneWeekInFutureExactlyOneWeekAndOneDayOrLess() {
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.WEEK_OF_YEAR, 1);
		aDate.add(GregorianCalendar.DAY_OF_MONTH, -1);
		//Exactly six days
		assertFalse(calendarTest.dateAtLeastOneWeekInFuture(aDate));
		aDate.add(GregorianCalendar.DAY_OF_MONTH, -3);
		assertFalse(calendarTest.dateAtLeastOneWeekInFuture(aDate));
		aDate.add(GregorianCalendar.DAY_OF_MONTH, -1000);
		assertFalse(calendarTest.dateAtLeastOneWeekInFuture(aDate));
	}

	/**
	 * Tests to make sure that a Date one week ahead or more will work for an auction
	 */
	@Test
	public void testdateAtLeastOneWeekInFutureExactlyOneWeekOrMore() {
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 7);
		//Exactly one week
		assertTrue(calendarTest.dateAtLeastOneWeekInFuture(aDate));
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		assertTrue(calendarTest.dateAtLeastOneWeekInFuture(aDate));
	}
	
	/**
	 * Tests to make sure you can add up to 25 Auctions to a Calendar
	 */
	@Test
	public void testValidateAuctionRequestMax25AuctionsUpTo25() {
		Calendar testCalendar = new Calendar();
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.WEEK_OF_YEAR, 1);
		for (int i = 0; i < 25; i++) {

			//adds 2 auctions a day until there are 25 auctions
			aDate.add(GregorianCalendar.HOUR_OF_DAY, 12);
			Auction temp = new Auction(aDate, "test");
			assertTrue(testCalendar.validateAuctionRequestMax25Auctions());
			assertTrue(testCalendar.addAuction(temp));

		}
	}
	
	/**
	 * Tests to make sure you cannot add more than 25 Auctions to a Calendar
	 */
	@Test
	public void testValidateAuctionRequestMax25AuctionsMoreThan25() {
		Calendar testCalendar = new Calendar();
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.WEEK_OF_YEAR, 1);
		for (int i = 0; i < 25; i++) {
			//adds 2 auctions a day until there are 25 auctions
			aDate.add(GregorianCalendar.HOUR_OF_DAY, 12);
			Auction temp = new Auction(aDate, "test");
			assertTrue(testCalendar.validateAuctionRequestMax25Auctions());
			assertTrue(testCalendar.addAuction(temp));
		}
		//tries to add 2 auctions over 25
		aDate.add(GregorianCalendar.HOUR_OF_DAY, 12);
		Auction temp = new Auction(aDate, "test");
		assertFalse(testCalendar.validateAuctionRequestMax25Auctions());
		assertFalse(testCalendar.addAuction(temp));
		
		aDate.add(GregorianCalendar.HOUR_OF_DAY, 12);
		temp = new Auction(aDate, "test");
		assertFalse(testCalendar.validateAuctionRequestMax25Auctions());
		assertFalse(testCalendar.addAuction(temp));
	}
	
	/**
     * Test method for increasing the max auction count by zero
     * 
     * @author "Robert Hinds"
     */
    @Test
    //Maximum future auctions increased by zero... ALLOWED
    public void testSetMaxAuctionsLimitByZero() {
    	int myNumberToIncreaseMaxAuctionLimitTo = testCalendar2.getMaxAuctionsLimit();
    	assertEquals(testCalendar2.setMaxAuctionsLimit(myNumberToIncreaseMaxAuctionLimitTo), 0);

    }
    
    /**
     * Test method for increasing the max auction count below zero
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set below zero... NOT ALLOWED
    public void testSetMaxAuctionsLimitBelowZero() {
    	
    	int myNumberToChangeMaxAuctionLimitTo = -1;
		assertEquals(testCalendar2.setMaxAuctionsLimit(myNumberToChangeMaxAuctionLimitTo),-1);
    }
    
    /**
     * Test method for increasing the max auction count to zero
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to zero... NOT ALLOWED
    public void testSetMaxAuctionsLimitToZero() {
    	int myNumberToChangeMaxAuctionLimitTo =  0;
    	assertEquals(testCalendar2.setMaxAuctionsLimit(myNumberToChangeMaxAuctionLimitTo),-1);
    }
    
    /**
     * Test method for decreasing maximum auction limit below the number of active auctions.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to below current active auctions... NOT ALLOWED
    public void testSetMaxAuctionsLimitToBelowCurrentActiveAuctionCount() {
    	int myNumberToChangeMaxAuctionLimitTo =  testCalendar2.getUpcomingAuctionsNumber() ;

    	assertEquals(testCalendar2.setMaxAuctionsLimit(myNumberToChangeMaxAuctionLimitTo), -2);
    }
    
    /**
     * Test method for increasing the maximum auction limit by 1.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to one more than current max auction limit value... ALLOWED
    public void testSetMaxAuctionsLimitByOneAboveCurrentNumber() {
    	int myNumberToChangeMaxAuctionLimitBy = testCalendar2.getMaxAuctionsLimit()+1;

    	assertEquals(testCalendar2.setMaxAuctionsLimit(myNumberToChangeMaxAuctionLimitBy),0);
    }
    
    /**
     * Test method for increasing the maximum auction limit by 10.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to 10 more than current max auction limit value... ALLOWED
    public void testSetMaxAuctionsLimitByTenAboveCurrentNumber() {
    	int myNumberToChangeMaxAuctionLimitBy = testCalendar2.getMaxAuctionsLimit()+10;

    	assertEquals(testCalendar2.setMaxAuctionsLimit(myNumberToChangeMaxAuctionLimitBy),0);
    }
    
    /**
     * Test method for decreased to some value greater than both zero and the number of current active auctions.
     * 
     * @author "Robert Hinds"
     */
    @Test
  //Maximum future auctions set to 10 more than current max auction limit value then find active auction count which 
  //can't be more than previous max auction limit. Find the difference which at the most will be ten. Divide this result by 2, giving
  //a value of at least 5. Subtract this newly calculated value from the current max auction limit. Set max auction limit to this new result.
  // which will be at least 5 less than the previous max but still greater than the  current active auction count... ALLOWED
    public void testSetMaxAuctionDecreaseLimitByRange() {
    	int myNumberToChangeMaxAuctionLimitBy = testCalendar2.getMaxAuctionsLimit()+10;
    	assertEquals(testCalendar2.setMaxAuctionsLimit(myNumberToChangeMaxAuctionLimitBy),0);
    	
    	int myCurrentMaxAuctionLimitAmount = testCalendar2.getMaxAuctionsLimit() - (testCalendar2.getMaxAuctionsLimit() - testCalendar2.getUpcomingAuctionsNumber())/2;
    	assertEquals(testCalendar2.setMaxAuctionsLimit(myCurrentMaxAuctionLimitAmount),0);
    }
}
