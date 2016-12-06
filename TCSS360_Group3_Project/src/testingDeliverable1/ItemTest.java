package testingDeliverable1;

import static org.junit.Assert.*;
import org.junit.Test;

import model.Item;


/**
 * JUnit Tests for the Item Class
 *
 * @author Iriohm
 * @version November 2016
 */
public class ItemTest {
	// Placeholder variables for creating testing Bids.
	private Item item1;


	/**
	 * Tests to ensure minimum bids of zero are not allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMinimumBidToZero() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.setMinBid(0.0);

	}


	/**
	 * Tests to ensure minimum bids in the negative are not allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMinimumBidToNegative() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.setMinBid(-1.0);

	}


	/**
	 * Tests to ensure quantities of zero are not allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetQuantityToZero() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.setQuantity(0);

	}


	/**
	 * Tests to ensure negative quantities are not allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetQuantityToNegative() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.setQuantity(-1);

	}


	/**
	 * Tests to ensure Bids below the minimum cannot be made.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMakeBidBelowMinBid() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.makeBid("Sir Britty von Brittington", 1.0);

	}


	/**
	 * Tests to ensure Bids of zero cannot be made.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMakeBidZero() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.makeBid("Sir Britty von Brittington", 0.0);

	}


	/**
	 * Tests to ensure Bids below zero cannot be made.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMakeBidNegative() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.makeBid("Sir Britty von Brittington", -1.0);

	}


	/**
	 * Tests to ensure Bids can be added successfully.
	 */
	@Test
	public void testMakeBidSuccessfulNotNull() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.makeBid("Sir Britty von Brittington", 5.0);

		assertTrue(item1.getHighestBid() != null);

	}


	/**
	 * Tests to ensure Bids added have the correct name.
	 */
	@Test
	public void testMakeBidSuccessfulCorrectBidder() {
		item1 = new Item("123", "Fancy Hat", "An extremely fancy hat.", "Small", 2.0, 1, "Very Fine");

		item1.makeBid("Sir Britty von Brittington", 5.0);

		assertTrue(item1.getHighestBid().getBidder().equals("Sir Britty von Brittington"));

	}

}
