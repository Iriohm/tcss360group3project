package testing;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.*;
import org.junit.Test;

import junit.framework.*;
import model.Auction;
import model.Calendar;

public class CalendarTest extends TestCase {
	
	private Calendar calendarTest;
	private Calendar calendarTests2345;
	private Calendar calendarTests167;
	private Auction auctionOne;
	private Auction auctionSix;
	private Auction auctionSeven;
	
	/**
	 * Creates and adds all auctions to a Calendar
	 * necessary for a before, after, during, and boundary case.
	 */
	@Before
	public void setUp() {
		calendarTest = new Calendar();
		calendarTests2345 = new Calendar();
		calendarTests167 = new Calendar();
		
		//2000-1-1
		GregorianCalendar aDate = new GregorianCalendar(2000, 1, 1);
		Auction testAuction1 = new Auction(((GregorianCalendar)aDate.clone()), "Test1");
		calendarTest.addAuction(testAuction1); 
		calendarTests167.addAuction(testAuction1);
		auctionOne = testAuction1;
		
		//2000-1-2
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction testAuction2 = new Auction(((GregorianCalendar)aDate.clone()), "Test2");
		calendarTest.addAuction(testAuction2); 
		calendarTests2345.addAuction(testAuction2);
		
		//2000-1-12
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		Auction testAuction3 = new Auction(((GregorianCalendar)aDate.clone()), "Test3");
		calendarTest.addAuction(testAuction3); 
		calendarTests2345.addAuction(testAuction3);
		
		//2000-1-22
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		Auction testAuction4 = new Auction(((GregorianCalendar)aDate.clone()), "Test4");
		calendarTest.addAuction(testAuction4); 
		calendarTests2345.addAuction(testAuction4); 
		
		//2000-1-32
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		Auction testAuction5 = new Auction(((GregorianCalendar)aDate.clone()), "Test5");
		calendarTest.addAuction(testAuction5); 
		calendarTests2345.addAuction(testAuction5);
		
		//2000-1-33
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction testAuction6 = new Auction(((GregorianCalendar)aDate.clone()), "Test6");
		calendarTest.addAuction(testAuction6); 
		calendarTests167.addAuction(testAuction6); 
		auctionSix = testAuction6;
		
		//2000-1-34
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction testAuction7 = new Auction(((GregorianCalendar)aDate.clone()), "Test7");
		calendarTest.addAuction(testAuction7); 
		calendarTests167.addAuction(testAuction7); 
		auctionSeven = testAuction7;
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
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction upcomingAuction1 = new Auction(aDate, "test");
		calendarTest.addAuction(upcomingAuction1);
		
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction upcomingAuction2 = new Auction(aDate, "test");
		calendarTest.addAuction(upcomingAuction2);
		
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction upcomingAuction3 = new Auction(aDate, "test");
		calendarTest.addAuction(upcomingAuction3);

		assertEquals(3, calendarTest.getUpcomingAuctionsNumber());
	}
	
}
