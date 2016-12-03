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
	private static final long serialVersionUID = 2354756645451028497L;

	/** The ID of the Bidder who made this bid. */
	private String myBidderID;

	/** The amount of money bid. */
	private double myBidAmount;

	/** The bid immediately lower in the chain. */
	private Bid myNextBid;


	/**
	 * Basic constructor.
	 *
	 * @param theBidderID The ID of the Bidder making the bid.
	 */
	public Bid(String theBidderID) {
		myBidderID = theBidderID;
		myBidAmount = 0.0;
		myNextBid = null;

	}


	/**
	 * Basic constructor.
	 *
	 * @param theBidderID The ID of the Bidder making the bid.
	 * @param theBidAmount The amount the Bidder is making for the bid.
	 */
	public Bid(String theBidderID, double theBidAmount) {
		myBidderID = theBidderID;
		changeBidAmount(theBidAmount);
		myNextBid = null;

	}


	/**
	 * Basic constructor.
	 *
	 * @param theBidderID The ID of the Bidder making the bid.
	 * @param theNextBid The next Bid in the linked list of Bids. Should point to the next highest.
	 */
	public Bid(String theBidderID, Bid theNextBid) {
		myBidderID = theBidderID;
		myBidAmount = 0.0;
		myNextBid = theNextBid;

	}


	/**
	 * Constructor to make placeholder Bids for the beginnings of Bid chains.
	 */
	public Bid() {
		myBidderID = "BID_TOP";
		myBidAmount = 0.0;
		myNextBid = null;

	}


	// Getters
	public String getBidder() { return myBidderID; }
	public double getBidAmount() { return myBidAmount; }

	/** @return If called on BID_TOP, should return the highest bid. */
	public Bid getNext() { return myNextBid; }


	/**
	 * Gets the Bid in the chain belonging to the given Bidder, if it exists.
	 *
	 * @param theBidder The Bidder username to search for.
	 * @return Returns the Bid made by the given Bidder. Otherwise returns null.
	 */
	public Bid getBidByBidder(String theBidder) {
		Bid bidFocus = this;

		while (bidFocus.myNextBid != null) {
			bidFocus = bidFocus.myNextBid;

			if (bidFocus.myBidderID.equals(theBidder)) { return bidFocus; }

		}

		return null;

	}


	/**
	 * Counts the number of Bids in the chain.
	 *
	 * @return Returns the number of Bids below this one.
	 */
	public int bidCount() {
		Bid bidFocus = this;
		int iReturn = 0;

		while (bidFocus.myNextBid != null) {
			bidFocus = bidFocus.myNextBid;
			iReturn++;

		}

		return iReturn;

	}


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

		myBidAmount = theNewAmount;

	}


	/**
	 * Sets the next Bid in the chain.
	 *
	 * @param theNextBid The Bid this Bid should point to as being immediately lower.
	 */
	public void setNext(Bid theNextBid) {
		myNextBid = theNextBid;

	}


	/**
	 * Checks to see if a given Bidder has made a Bid in the chain.
	 * @param theBidder The Bidder username to search for.
	 * @return Returns true if the named Bidder has made a bid in this chain.
	 */
	public boolean isBidderPresent(String theBidder) {
		Bid bidFocus = this;

		while (bidFocus.myNextBid != null) {
			bidFocus = bidFocus.myNextBid;

			if (bidFocus.myBidderID.equals(theBidder)) { return true; }

		}

		return false;

	}


	/**
	 * Removes the Bid of the given Bidder from the Bid chain.
	 *
	 * @param theBidder The Bidder whose Bid should be removed.
	 * @return Returns true if Bid was found. Returns false otherwise.
	 */
	public boolean removeBid(String theBidder) {
		if	(myNextBid == null) { return false; }
		else if (myNextBid.myBidderID.equals(theBidder)) {
			myNextBid = myNextBid.myNextBid;
			return true;
		} else {
			Bid bidPrev = this;
			Bid bidFocus = myNextBid;

			while (bidFocus != null) {
				if	(bidFocus.myBidderID.equals(theBidder)) {
					bidPrev.myNextBid = bidFocus.myNextBid;
					return true;

				}

				bidFocus = bidFocus.myNextBid;

			}

		}

		return false;

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

		// Removes the old bid by this Bidder, if there is one.
		removeBid(theBid.myBidderID);

		Bid bidFocus = this;
		Bid bidTemp;

		while (true) {
			if	(bidFocus.myNextBid != null) {
				if	(theBid.myBidAmount >= bidFocus.myNextBid.myBidAmount) {
					bidTemp = bidFocus.myNextBid;
					bidFocus.myNextBid = theBid;
					theBid.myNextBid = bidTemp;

					break;

				}

			} else {
				bidFocus.myNextBid = theBid;

				break;

			}

		}

	}

}
