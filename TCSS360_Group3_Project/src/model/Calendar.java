package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Calendar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Auction> myAuctions;
	
	public Calendar() {
		myAuctions = new LinkedList<Auction>();
	}
	
	public Calendar(List<Auction> theAuctions) {
		myAuctions = theAuctions;
	}
	public boolean validateAuctionRequest(Date theDate) {
		return false;
	}
	
	public boolean addAuction(Auction theAuction) {
		myAuctions.add(theAuction);
		return false;
	}
	
	public List<Auction> getAuctions(Date theDate) {
		return null;
	}
}
