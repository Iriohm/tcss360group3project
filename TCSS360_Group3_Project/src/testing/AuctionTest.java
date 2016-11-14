package testing;

import java.util.GregorianCalendar;
import org.junit.*;
import org.junit.Test;

import junit.framework.*;
import model.Auction;
import model.Calendar;
import model.Item;

public class AuctionTest extends TestCase {
	Calendar testCalendar;
	Auction testAuction;
	Item testItem1;
	Item testItem2;
	
	@Before
	public void setUp() {
		testCalendar = new Calendar();
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		testAuction = new Auction(aDate, "test");
		testItem1 = new Item(testCalendar.getNextItemID() + "", "Football", "A football, wow!", "Small", 10.01, 2, "good");
		testItem2 = new Item(testCalendar.getNextItemID() + "", "Handball", "A handball, wow!", "small", 10.51, 1, "good");
		
	}
	
	/**
	 * Tests to make sure multiple different items can be added to an Auction
	 */
	@Test
	public void testvalidateItemAddDifferentItem() {
		assertTrue(testAuction.validateItemAdd(testItem1));
		assertTrue(testAuction.validateItemAdd(testItem2));
	}
	
	/**
	 * Tests to make sure multiple of the same item cannot be added to an Auction
	 */
	@Test
	public void testvalidateItemAddSameItem() {
		assertTrue(testAuction.validateItemAdd(testItem1));
		assertTrue(testAuction.addItem(testItem1));
		assertFalse(testAuction.validateItemAdd(testItem1));
		assertFalse(testAuction.addItem(testItem1));
	}
	
	/**
	 * Tests to make sure you cannot set a negative number of estimated items
	 */
	@Test
	public void testsetEstimatedItemsNegative() {
		assertFalse(testAuction.setEstimatedItems(-1));
	}
	
	/**
	 * Tests to make sure you cannot set a zero number of estimated items
	 */
	@Test
	public void testsetEstimatedItemsZero() {
		assertFalse(testAuction.setEstimatedItems(0));
	}
	
	/**
	 * Tests to make sure you can set a positive number of estimated items
	 */
	@Test
	public void testsetEstimatedItemsPositive() {
		assertTrue(testAuction.setEstimatedItems(1));
		assertTrue(testAuction.setEstimatedItems(100));
	}
	/**
	 * Tests to make sure you cannot add an item to an auction that is in the past
	 */
	@Test
	public void testvalidateItemAddAuctionDateAuctionInPast() {
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.DAY_OF_YEAR, -1);
		Auction testAuction = new Auction(aDate, "test");
		assertFalse(testAuction.validateItemAddAuctionDate());
		
		aDate.add(GregorianCalendar.DAY_OF_YEAR, -10);
		assertFalse(testAuction.validateItemAddAuctionDate());
	}
	
	/**
	 * Tests to make sure you can add an item to an auction that is in the future
	 */
	@Test
	public void testvalidateItemAddAuctionDateAuctionInFuture() {
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		Auction testAuction = new Auction(aDate, "test");
		assertTrue(testAuction.validateItemAddAuctionDate());
		
		aDate.add(GregorianCalendar.DAY_OF_YEAR, 10);
		assertTrue(testAuction.validateItemAddAuctionDate());
	}
}