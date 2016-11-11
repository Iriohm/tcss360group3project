package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Auction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7183405560696200845L;
	List<Item> myItems;
	Date myDate;
	String myAuctionName;
	
	public Auction(Date theDate, String theAuctionName) {
		myDate = theDate;
		myAuctionName = theAuctionName;
		myItems = new LinkedList<Item>();
	}
	
	public List<Item> getItems() {
		return myItems;
	}
	
	public boolean addItem(Item theItem) {
		return false
	}
	
	
}
