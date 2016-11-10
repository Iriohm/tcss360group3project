/*
(Michael) Item
Keeps track of individual items, including starting price, buyout price, etc.
Keeps track of all bids on items (via internal nodes)? Organized by highest bid. Must allow for reorganization if someone else bids higher.
	double minimumBid
	String itemName
	String itemDescription
	? int itemQuantity would also need isValidQuantity
	String or case itemCondition


	Getter for every field


	Boolean isValidBidAmount(double amount)
	Boolean isValidMinimumBid(double minimumBid)
 */

package model;

import java.io.Serializable;

public class Item implements Serializable {
	private String sName;
	private String sDescription;
	private double dMinimumBid;
	private int iQuantity;
	private String sCondition;

	/**
	 *
	 * @param theName
	 * @param theDescription
	 * @param theMinimumBid
	 * @param theQuantity
	 * @param theCondition
	 */
	public Item(String theName, String theDescription, double theMinimumBid, int theQuantity, String theCondition) {
		sName = theName;
		sDescription = theDescription;
		dMinimumBid = theMinimumBid;
		iQuantity = theQuantity;
		sCondition = theCondition;

	}

}