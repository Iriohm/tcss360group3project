package model;

import java.io.Serializable;

/**
* Represents a Bid on some Item, by some Bidder. Successive Bids can be arranged in
* a chain format, with a placeholder bid at the top (created using the parameterless
* constructor), and the Bids beyond sorted according to price (highest = first).
*
* @author Iriohm
* @version Nov 2016
*/
public class Bid implements Serializable {
	/** A generated Serial Version ID */
	private static final long serialVersionUID = 6051144789148089874L;

	/** The ID of the Bidder who made this bid. */
	private String sBidderID;

	/** The amount of money bid. */
	private double dBidAmount;

	/** The item bid on. */
	private String sItemID;

	/** The bid immediately lower in the chain. */
	private Bid bidNext;


	/**
	 * Basic constructor.
	 *
	 * @param theBidderID The ID of the Bidder making the bid.
	 * @param theItemID The amount the Bidder is making for the bid.
	 * @param theNextBid The next Bid in the linked list of Bids. Should point to the next highest.
	 */
	public Bid(String theBidderID, String theItemID, Bid theNextBid) {
		sBidderID = theBidderID;
		dBidAmount = 0.0;
		sItemID = theItemID;
		bidNext = theNextBid;

	}


	/**
	 * Constructor to make placeholder Bids for the beginnings of Bid chains.
	 *
	 * @param theBidderID The ID of the Bidder making the bid.
	 * @param theItemID The amount the Bidder is making for the bid.
	 * @param theNextBid The next Bid in the linked list of Bids. Should point to the next highest.
	 */
	public Bid() {
		sBidderID = "BID_TOP";
		dBidAmount = 0.0;
		sItemID = "NULL_ITEM_ID";
		bidNext = null;

	}


	// Getters
	public String getBidder() { return sBidderID; }
	public double getBidAmount() { return dBidAmount; }
	public String getItemID() { return sItemID; }

	/** @return If called on BID_TOP, should return the highest bid. */
	public Bid getNext() { return bidNext; }


	/**
	 * Alters the amount of the bid.
	 *
	 * @param theNewAmount The new amount to change to. Cannot be negative.
	 * @throws IllegalArgumentException if theNewAmount < 0
	 */
	public void changeBidAmount(double theNewAmount) {
		if	(theNewAmount < 0.0) {
			throw new IllegalArgumentException();

		}

		dBidAmount = theNewAmount;

	}


	/**
	 * Sets the next Bid in the chain.
	 *
	 * @param theNextBid The Bid this Bid should point to as being immediately lower.
	 */
	public void setNext(Bid theNextBid) {
		bidNext = theNextBid;

	}


	/**
	 * Adds a new Bid to a chain of Bids, arranging them so the highest Bids are at the top.
	 * The Bid which calls this is considered the placeholder Bid, representing the head
	 * of the chain, and is ignored for the purposes of sorting.
	 *
	 * @param theBid The Bid to add to the chain.
	 * @throws NullPointerException if theBid == null
	 */
	public void addBidToChain(Bid theBid) {
		if	(theBid == null) {
			throw new NullPointerException();

		}

		Bid bidFocus = this;
		Bid bidTemp;

		while (true) {
			if	(bidFocus.bidNext != null) {
				if	(theBid.dBidAmount >= bidFocus.bidNext.dBidAmount) {
					bidTemp = bidFocus.bidNext;
					bidFocus.bidNext = theBid;
					theBid.bidNext = bidTemp;

					break;

				}

			} else {
				bidFocus.bidNext = theBid;

				break;

			}

		}

	}

}
