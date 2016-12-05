package model;

import java.io.Serializable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents an Item to be bid upon. Includes fields for the ID number, name, description,
 * minimum bid, quantity, and condition. Also keeps track of any bids made on it.
 *
 * @author Iriohm
 * @version Dec 2016
 */
public class Item implements Serializable {
	/** A generated serial ID. */
	private static final long serialVersionUID = -7510712678086711711L;

	/** The ID of this Item. */
	private String myID;

	/** The name of this Item. */
	private String myName;

	/** The description of this Item. */
	private String myDescription;

	/** The description of this Item. */
	private String mySize;

	/** The minimum bid that can be placed on this Item. */
	private double myMinimumBid;

	/** The quantity of this Item. */
	private int myQuantity;

	/** The condition of this Item. */
	private String myCondition;

	/** The top (dummy) node of this Item's Bid chain. */
	private Bid myBidChain;

	/** The Auction this item is associated with. */
	private Auction myAuction;


	/**
	 * A basic constructor for Items.
	 *
	 * @param theID The Item ID.
	 * @param theName The name of the Item.
	 * @param theDescription A description of the Item.
	 * @param theSize The size of the Item. Should be either small, medium, or large.
	 * @param theMinimumBid The minimum bid that can be made on the Item. Must be > 0
	 * @param theQuantity The quantity of the Item. Must be > 0
	 * @param theCondition The condition of the Item. Should be either acceptable, good, very good, like new, or new.
	 */
	public Item(String theID, String theName, String theDescription, String theSize, double theMinimumBid,
					int theQuantity, String theCondition) {
		myID = theID;
		myName = theName;
		myDescription = theDescription;
		mySize = theSize;
		myCondition = theCondition;
		myAuction = null;

		setMinBid(theMinimumBid);
		setQuantity(theQuantity);

		myBidChain = new Bid();

	}


	/**
	 * A basic constructor for Items, which also adds them to some Auction.
	 *
	 * @param theID The Item ID.
	 * @param theName The name of the Item.
	 * @param theDescription A description of the Item.
	 * @param theSize The size of the Item. Should be either small, medium, or large.
	 * @param theMinimumBid The minimum bid that can be made on the Item. Must be > 0
	 * @param theQuantity The quantity of the Item. Must be > 0
	 * @param theCondition The condition of the Item. Should be either acceptable, good, very good, like new, or new.
	 * @param theAuction
	 */
	public Item(String theID, String theName, String theDescription, String theSize, double theMinimumBid,
					int theQuantity, String theCondition, Auction theAuction) {
		myID = theID;
		myName = theName;
		myDescription = theDescription;
		mySize = theSize;
		myCondition = theCondition;
		myAuction = theAuction;

		setMinBid(theMinimumBid);
		setQuantity(theQuantity);

		myBidChain = new Bid();

	}


	// Getters
	public String getID() { return myID; }
	public String getName() { return myName; }
	public String getDescription() { return myDescription; }
	public String getSize() { return mySize; }
	public double getMinBid() { return myMinimumBid; }
	public int getQuantity() { return myQuantity; }
	public String getCondition() { return myCondition; }
	public Bid getBidChain() { return myBidChain; }
	public Auction getAuction() { return myAuction; }


	/**
	 * @return Returns the highest Bid made on this Item, or null if no Bids exist.
	 */
	public Bid getHighestBid() { return myBidChain.getNext(); }


	// Setters
	public void setID(String theNewID) { myID = theNewID; }
	public void setName(String theNewName) { myName = theNewName; }
	public void setDescription(String theNewDescription) { myDescription = theNewDescription; }
	public void setSize(String theNewSize) { mySize = theNewSize; }
	public void setCondition(String theNewCondition) { myCondition = theNewCondition; }
	public void setAuction(Auction theNewAuction) { myAuction = theNewAuction; }


	/**
	 * Sets the minimum bid of this Item.
	 *
	 * @param theNewMinimumBid The new minimum bid to set to.
	 * @throws IllegalArgumentException if theNewMinimumBid <= 0.0
	 */
	public void setMinBid(double theNewMinimumBid) {
		if	(theNewMinimumBid <= 0.0) { throw new IllegalArgumentException(); }

		myMinimumBid = theNewMinimumBid;

	}


	/**
	 * Sets the quantity of this Item.
	 *
	 * @param theNewQuantity The new quantity bid to set to.
	 * @throws IllegalArgumentException if theNewQuantity < 1
	 */
	public void setQuantity(int theNewQuantity) {
		if	(theNewQuantity < 1) { throw new IllegalArgumentException(); }

		myQuantity = theNewQuantity;

	}


	/**
	 * Allows a Bidder to make a bid of some price on this Item.
	 * If the Bidder has already made a bid on this Item, the bid is updated.
	 *
	 * @param theBidder The Bidder making the bid.
	 * @param thePrice The price of the bid.
	 * @throws IllegalArgumentException If thePrice < the minimum bid.
	 */
	public void makeBid(String theBidder, double thePrice) {
		if	(thePrice < myMinimumBid) { throw new IllegalArgumentException(); }

		myBidChain.addBidToChain(new Bid(theBidder, thePrice));

	}


	/**
	 * Checks to see if a given Bidder is bidding on this Item.
	 *
	 * @param theBidder The Bidder to check for.
	 * @return Returns true if the given Bidder is bidding on this Item. Otherwise returns false.
	 */
	public boolean isBeingBidOnBy(String theBidder) {
		return myBidChain.isBidderPresent(theBidder);

	}


	/**
	 * Gets the Bid made by the given Bidder.
	 *
	 * @param theBidder The Bidder to search the Bids for.
	 * @return Returns the Bid made by Bidder of the given username, or null if no such Bidder was found.
	 */
	public Bid getBidOf(String theBidder) {
		return myBidChain.getBidByBidder(theBidder);

	}

}