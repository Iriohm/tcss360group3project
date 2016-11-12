package testing;

import static org.junit.Assert.*;
import org.junit.Test;

import model.Bid;

/**
 * JUnit Tests for the Bid Class
 *
 * @author Iriohm
 * @version November 2016
 */
public class BidTest {
	/** The accepted variance a double can have from a comparison double without being considered inaccurate. */
	private static double DOUBLE_EPSILON = 0.001;

	// Placeholder variables for creating testing Bids.
	private Bid bid1;
	private Bid bid2;
	private Bid bid3;
	private Bid bid4;


	/**
	 * Tests to ensure a Bid can be found by its Bidder's username.
	 */
	@Test
	public void testGetBidByBidderWorks() {
		bid4 = new Bid("Thing 3", null);
		bid3 = new Bid("Thing 2", bid4);
		bid2 = new Bid("Thing 1", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		assertTrue(bid1.getBidByBidder("Thing 2") == bid3);

	}


	/**
	 * Tests to ensure bidCount returns zero if there are zero bids (aside from the dummy).
	 */
	@Test
	public void testBidCountZero() {
		bid1 = new Bid();

		assertTrue(bid1.bidCount() == 0);

	}


	/**
	 * Tests to ensure bidCount correctly counts the number of bids (not including the dummy).
	 */
	@Test
	public void testBidCountCountsCorrectly() {
		bid4 = new Bid("Thing 3", null);
		bid3 = new Bid("Thing 2", bid4);
		bid2 = new Bid("Thing 1", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		assertTrue(bid1.bidCount() == 3);

	}


	/**
	 * Tests to ensure negative bids are never allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testChangeBidAmountNegative() {
		bid1 = new Bid("Forrester, Lily", null);
		double dPriceNegative = -1.00;

		bid1.changeBidAmount(dPriceNegative);

	}


	/**
	 * Tests to ensure bid amount can be changed properly.
	 */
	@Test
	public void testChangeBidAmountMutability() {
		bid1 = new Bid("Forrester, Lily", null);
		double dPrice = 5.00;

		bid1.changeBidAmount(dPrice);

		assertEquals(dPrice, bid1.getBidAmount(), DOUBLE_EPSILON);

	}


	/**
	 * Tests to ensure setNext() can successfully set a next Bid in the chain.
	 */
	@Test
	public void testSetNext() {
		bid1 = new Bid("Forrester, Lily", null);
		bid2 = new Bid("Hearthright, James", null);

		bid1.setNext(bid2);

		assertTrue(bid1.getNext() == bid2);

	}


	/**
	 * Tests to ensure isBidderPresent returns true if a given Bidder is found.
	 */
	@Test
	public void testIsBidderPresentTrue() {
		bid3 = new Bid("Forrester, Lily", null);
		bid2 = new Bid("Hearthright, James", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		assertTrue(bid1.isBidderPresent(bid2.getBidder()));

	}


	/**
	 * Tests to ensure isBidderPresent returns false if a given Bidder is not found.
	 */
	@Test
	public void testIsBidderPresentFalse() {
		bid3 = new Bid("Forrester, Lily", null);
		bid2 = new Bid("Hearthright, James", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		assertFalse(bid1.isBidderPresent("Alf"));

	}


	/**
	 * Tests to ensure removeBid() removes a Bid properly if the Bid is the first in the chain.
	 */
	@Test
	public void testRemoveBidProperlyFirst() {
		bid3 = new Bid("Jackbone, Hammerfist", null);
		bid2 = new Bid("Forrester, Lily", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		bid1.removeBid("Forrester, Lily");

		assertFalse(bid1.isBidderPresent("Forrester, Lily") && bid1.bidCount() == 1);

	}


	/**
	 * Tests to ensure removeBid() removes a Bid properly if the Bid is further down the chain.
	 */
	@Test
	public void testRemoveBidProperlySecond() {
		bid4 = new Bid("XxSephirothxX_67", null);
		bid3 = new Bid("Jackbone, Hammerfist", bid4);
		bid2 = new Bid("Forrester, Lily", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		bid1.removeBid("Jackbone, Hammerfist");

		assertFalse(bid1.isBidderPresent("Jackbone, Hammerfist") && bid1.bidCount() == 2);

	}


	/**
	 * Tests to ensure removeBid() removes a Bid properly if the Bid is the last in the chain.
	 */
	@Test
	public void testRemoveBidProperlyLast() {
		bid4 = new Bid("XxSephirothxX_67", null);
		bid3 = new Bid("Jackbone, Hammerfist", bid4);
		bid2 = new Bid("Forrester, Lily", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		bid1.removeBid("XxSephirothxX_67");

		assertFalse(bid1.isBidderPresent("XxSephirothxX_67") && bid1.bidCount() == 2);

	}


	/**
	 * Tests to ensure removeBid() returns true when it finds a bid by some username.
	 */
	@Test
	public void testRemoveBidIfFoundReturnTrue() {
		bid3 = new Bid("Jackbone, Hammerfist", null);
		bid2 = new Bid("Forrester, Lily", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		assertTrue(bid1.removeBid(bid2.getBidder()));

	}


	/**
	 * Tests to ensure removeBid() returns false when it can't find a bid by some username.
	 */
	@Test
	public void testRemoveBidCantFindReturnFalse() {
		bid3 = new Bid("Jackbone, Hammerfist", null);
		bid2 = new Bid("Forrester, Lily", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		assertFalse(bid1.removeBid("DingusFlail"));

	}


	/**
	 * Tests to ensure addBidToChain() won't accept a null pointer.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddBidToChainNullPointer() {
		bid1 = new Bid();

		bid1.addBidToChain(null);

	}

	/**
	 * Tests to ensure addBidToChain() adds a new Bid properly.
	 */
	@Test
	public void testAddBidToChainAddsCorrectly() {
		bid1 = new Bid();
		bid2 = new Bid("Forrester, Lily", null);

		bid2.changeBidAmount(3.0);

		bid1.addBidToChain(bid2);

		assertTrue(bid1.getNext() == bid2);

	}


	/**
	 * Tests to ensure addBidToChain() adds a new Bid to the right spot in the chain.
	 */
	@Test
	public void testAddBidToChainSortedProperly() {
		bid1 = new Bid();
		bid2 = new Bid("Forrester, Lily", null);
		bid3 = new Bid("Hearthright, James", null);

		bid2.changeBidAmount(3.0);
		bid3.changeBidAmount(5.0);

		bid1.addBidToChain(bid2);
		bid1.addBidToChain(bid3);

		assertTrue(bid1.getNext() == bid3);

	}


	/**
	 * Tests to ensure addBidToChain() recognizes a Bidder who has already bid,
	 * and updates their bid accordingly.
	 */
	@Test
	public void testAddBidToChainUpdatesProperly() {
		bid4 = new Bid("Forrester, Lily", null);
		bid3 = new Bid("Hamsterdance", null);
		bid2 = new Bid("Forrester, Lily", bid3);
		bid1 = new Bid();

		bid1.setNext(bid2);

		bid2.changeBidAmount(3.0);
		bid3.changeBidAmount(5.0);
		bid4.changeBidAmount(10.0);

		bid1.addBidToChain(bid4);

		assertTrue(bid1.getBidByBidder("Forrester, Lily").getBidAmount() == 10.0 &&
					bid1.bidCount() == 2);

	}

}
