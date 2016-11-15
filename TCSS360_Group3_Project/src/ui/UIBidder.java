package ui;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import model.Auction;
import model.Bidder;
import model.Calendar;
import model.Item;


/**
 * Handles all the bidder interaction.
 *
 * @author David Nowlin
 * @version 11/11/2016
 *
 */
public class UIBidder {
	private static Scanner myScanner = new Scanner(System.in);
	private static String myHeadline = null;

	private static Format myFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	protected static Date   myTodayDate = GregorianCalendar.getInstance().getTime();
	protected static String myCurrentDate = myFormatter.format(myTodayDate);

	/**
	 * this is what start all the Bidder UI classes.
	 * dose all the bidder need to do.
	 * @param theBidder the user that is in the UI.
	 * @param theCalendar of auction center.
	 * @return the changes to the calendar.
	 */
	public static Calendar beginBidderUI(Bidder theBidder, Calendar theCalendar) {
		int choose = 0;
		myHeadline = "\n\n\nAuctionCentral: the auctioneer for non-profit organization.\n" + theBidder.getUsername()
				+ " logged in as Bidder.\nToday date: " + myCurrentDate + "\n";
		System.out.println(myHeadline);
		do {
			System.out.print("1) View List Of Auctions.\n" + "2) Exit.\n" + "Enter your Selection from 1 to 2: ");
			choose = myScanner.nextInt();
		} while (choose != 1 && choose != 2);
		if (choose == 1) {
			pickAuction(theBidder, theCalendar);
		}
		return theCalendar;
	}

	/*
	 * this will show all the auction that you can pick from.
	 */
	private static Bidder pickAuction(Bidder theBidder, Calendar theCalendar) {
		int choose = 0;
		boolean exit = false;
		if(theCalendar != null){
			if (theCalendar.getUpcomingAuctionsNumber() != 0) {
				while (!exit) {
					System.out.println(myHeadline);
					List<Auction> desiredAcctions = theCalendar.getAuctions((GregorianCalendar) GregorianCalendar.getInstance());//TODO: shows yesterday auction
					for (int i = 0; i < (theCalendar.getUpcomingAuctionsNumber()); i++) {
						System.out.println((i+1) + ") " + desiredAcctions.get((i)).getAuctionName() + "\t"
								+ myFormatter.format(desiredAcctions.get(i).getDate().getTime()) + "   ");
					}
					System.out.println((theCalendar.getUpcomingAuctionsNumber() + 1) + ") Exit");
					do {
						System.out.print("Enter your Selection from 1 to " + (theCalendar.getUpcomingAuctionsNumber() + 1) + ": ");
						choose = myScanner.nextInt();
					} while (choose < 1 || choose > (theCalendar.getUpcomingAuctionsNumber() + 1));
					if ((theCalendar.getUpcomingAuctionsNumber() + 1) == choose) {
						return theBidder;
					}
					exit = viewItem(theBidder, desiredAcctions.get((choose-1))); // choose -1
				}
			} else {
				System.out.println("Sorry the is no Auction to bid on at this moment");
			}
		}else {
			System.out.println("Sorry the is no Auction to bid on at this moment");
		}
		return theBidder;
	}

	/*
	 * view all the item in a select auction let you choose to go back or choose to bid.
	 */
	private static boolean viewItem(Bidder theBidder, Auction theAuction) {
		int choose = 0;
		boolean exit = false;
		boolean goBack = false;
		while (!exit && !goBack) {
			System.out.println(myHeadline);
			String AuctionInfo = theAuction.getAuctionName() + ", \t"
						+ myFormatter.format(theAuction.getDate().getTime());
			System.out.println(AuctionInfo);
			List<Item> itemlist = theAuction.getItems();
			do {
				System.out.println("Items Offered For Sale:\n" + "ID\tItem Name\t\t\tCondition\tMin Bid\tMy Bid");
				for (int i = 0; i < itemlist.size(); i++) {
					System.out.print(itemlist.get(i).getID() + "\t" + itemlist.get(i).getName() + "\t\t\t"
							+ itemlist.get(i).getCondition() + "\t" + itemlist.get(i).getMinBid() + "\t");//TODO: fix the format of the string.
				if(itemlist.get(i).getBidOf(theBidder.getUsername()) != null) {
					System.out.print(itemlist.get(i).getBidOf(theBidder.getUsername()).getBidAmount());
				}
				System.out.print("\n");
				}
				System.out.println("1) Bid On An Item.\n2) Go Back.\n3) Exit AuctionCentral.");
				choose = myScanner.nextInt();
			} while (choose < 1 || choose > 3);
			if (choose == 1) {
				selectItem(theBidder, theAuction);
			} else if (choose == 2) {
				goBack = true;
				System.out.println(goBack);
			} else if (choose == 3){
				exit = true;
			}
		}
		return exit;
	}

	/*
	 * this will allow you to select which item to get more information on.
	 */
	private static void selectItem(Bidder theBidder, Auction theAuction) {
		System.out.println(myHeadline);
		int choose = 0;
		String AuctionInfo = theAuction.getAuctionName() + ", \t"
				+ myFormatter.format(theAuction.getDate().getTime());
		System.out.println(AuctionInfo);
		List<Item> itemlist = theAuction.getItems();

		do {
			System.out.println("Items Offered For Sale:\n" + "ID\tItem Name\t\t\tCondition\tMin Bid\tMy Bid");
			for (int i = 0; i < itemlist.size(); i++) {
				System.out.print(itemlist.get(i).getID() + "\t" + itemlist.get(i).getName() + "\t\t\t"
						+ itemlist.get(i).getCondition() + "\t" + itemlist.get(i).getMinBid() + "\t");
				if(itemlist.get(i).getBidOf(theBidder.getUsername()) != null) {
					System.out.print(itemlist.get(i).getBidOf(theBidder.getUsername()).getBidAmount());
				}
				System.out.print("\n");
			}
			System.out.print("\nType Item ID to get more information and bid on the item :");
			choose = myScanner.nextInt();
		} while (choose < 1 || (itemlist.size()) < choose);
		placeBid(theBidder, itemlist.get((choose-1)));
	}

	/*
	 * this is will you place your bid on the select item or to go back.
	 *
	 * Michael: Altered to pass in an Auction, so placeBid() could call viewItem() to return to the previous screen.
	 */
	private static void placeBid(Bidder theBidder, Item theItem) {
		if	(theItem.isBeingBidOnBy(theBidder.getUsername())) {
			System.out.println("You've already bid on this item!\n");
			return;
		} 
		double bid = 0;
		int choose = 0;
		System.out.println(myHeadline);
		do {
			choose = 0;
			System.out.println(theItem.getName() + "\t" + theItem.getCondition() + " condition " + theItem.getMinBid());
			System.out.println(theItem.getDescription() + "\n\n"
					+ "What would you like to do?\n"
					+ "1) Place bid on this item.\n"
					+ "2) Go back\n");
			choose = myScanner.nextInt();
		} while (choose < 1 || choose > 2);
		if(choose == 1){
				bid = 0;
				do {
					System.out.println("Enter a bid of least $" + theItem.getMinBid() + ": $");
					bid = myScanner.nextDouble();
				} while (bid < theItem.getMinBid());
				System.out.println("You have placed a bid of $" + bid + " on " + theItem.getName() + ",\n"
						+ "AuctionCentral will notify you after the auction ends to let you know if\n"
						+ "yours was the winning bid. Good Luck!\n");

				theBidder.placeBid(theItem, bid);
		}else if (choose == 2) {
			return;

		} 
	}
}
