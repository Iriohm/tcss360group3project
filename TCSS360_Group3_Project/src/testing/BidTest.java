package testing;

import static org.junit.Assert.*;
import org.junit.Test;

import model.Bid;

/**
 * JUnit Tests for Bid.java
 *
 * @author Iriohm
 * @version November 2016
 */
public class BidTest {
	/** The accepted variance a double can have from a comparison double without being considered inaccurate. */
	private static double DOUBLE_EPSILON = 0.001;

	private Bid bid1;
	private Bid bid2;
	private Bid bid3;


	/**
	 * Tests to ensure negative bids are never allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testChangeBidAmountNegative() {
		bid1 = new Bid("Forrester, Lily", "12345", null);
		double dPriceNegative = -1.00;

		bid1.changeBidAmount(dPriceNegative);

	}


	/**
	 * Tests to ensure bid amount can be changed properly.
	 */
	@Test
	public void testChangeBidAmountMutability() {
		bid1 = new Bid("Forrester, Lily", "12345", null);
		double dPrice = 5.00;

		bid1.changeBidAmount(dPrice);

		assertEquals(dPrice, bid1.getBidAmount(), DOUBLE_EPSILON);

	}


	/**
	 * Tests to ensure setNext() can successfully set a next Bid in the chain.
	 */
	@Test
	public void testSetNext() {
		bid1 = new Bid("Forrester, Lily", "12345", null);
		bid2 = new Bid("Hearthright, James", "67890", null);

		bid1.setNext(bid2);

		assertTrue(bid1.getNext() == bid2);

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
		bid2 = new Bid("Forrester, Lily", "112233", null);

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
		bid2 = new Bid("Forrester, Lily", "112233", null);
		bid3 = new Bid("Hearthright, James", "67890", null);

		bid2.changeBidAmount(3.0);
		bid3.changeBidAmount(5.0);

		bid1.addBidToChain(bid2);
		bid1.addBidToChain(bid3);

		assertTrue(bid1.getNext() == bid3);

	}

}
