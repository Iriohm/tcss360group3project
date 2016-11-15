package ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.Auction;
import model.Calendar;
import model.Staff;

/**
 * Prints the staff user interface menu.
 * 
 * @author Robert Hinds
 * @version 11/11/2016
 *
 */
public class UIStaff extends UI {

	// Declarations

	public static Calendar beginStaffUI(Staff theStaff, Calendar theCalendar) {
		int optSel = 0;
		ArrayList<String> options = new ArrayList<>();
		options.add("View calendar of upcoming auctions");
		options.add("Administrative functions");
		options.add("Exit AuctionCentral");

		String optHeader = "What would you like to do?";
		String optChoice = "Enter your Selection from 1 to";

		int auctionCount = theCalendar.getUpcomingAuctionsNumber();
		boolean exit = false;
		boolean goBack = false;
		Scanner userInput = new Scanner(System.in);

		// prints menu
		clearScreen();
		while (!exit && !goBack) {
			optSel = 0;
			System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
			System.out.println(theStaff.getUsername() + " logged in as Auction Central Staff Person");
			System.out.println(myCurrentDate + "." + " Total number of upcoming auctions: " + auctionCount);
			System.out.println("");
			System.out.println(optHeader);
			for (int i = 0; i < options.size(); i++) {
				System.out.println(i + 1 + ". " + options.get(i));
			}
			System.out.println("");
			System.out.print(optChoice + " " + options.size() + ": ");
			try {
				optSel = Integer.parseInt(userInput.nextLine());
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				clearScreen();
			}
			if (optSel == 1) {
				getAuction(theCalendar);
				System.out.println();
				System.out.println("Hit any key to continue: ");
				userInput.nextLine();
				clearScreen();
			} else if (optSel == 2) {
				// admin functions
				goBack = true;
				System.out.println("Returning to previous menu...");
			} else if (optSel == 3) {
				// exit system
				System.out.println("Goodbye");
				exit = true;
			}

		}
		userInput.close();
		return theCalendar;
	}

	private static void getAuction(Calendar theCalendar) {
		int monthSize = 64;
		int i = 0, j = 0;
		java.util.Calendar aDate = GregorianCalendar.getInstance();
		List<Auction> auctionList = theCalendar.getAuctions((GregorianCalendar) aDate);
		ArrayList<Integer> displayCalendar = new ArrayList<>(monthSize);
		displayCalendar.add(0);
		for (i = 1; i < monthSize; i++) {
			displayCalendar.add(0);
		}
		for (i = 0; i < auctionList.size(); i++) {
			int temp = auctionList.get(i).getDate().get(java.util.Calendar.DATE);
			displayCalendar.set(temp, displayCalendar.get(temp) + 1);
		}
		System.out.println(aDate.getDisplayName(((java.util.Calendar.MONTH)), java.util.Calendar.LONG, Locale.US));
		for (i = aDate.get(java.util.Calendar.DATE); i <= aDate.getActualMaximum(java.util.Calendar.DATE) + 1; i++) {
			j++;
			if (i < 10) {
				System.out.print("| 0" + i + ":" + displayCalendar.get(i) + " ");
			} else {
				System.out.print("| " + i + ":" + displayCalendar.get(i) + " ");
			}
			if (j % 5 == 0) {
				if (i != 30) {
					System.out.print("|");
				}
				System.out.println("");

				j = 0;
			}
		}
		System.out.print("|");
		if (i + aDate.get(java.util.Calendar.DATE) >= aDate.getActualMaximum(java.util.Calendar.DATE)) {
			int daysLeft = (aDate.get(java.util.Calendar.DATE));
			aDate.add(java.util.Calendar.MONTH, +1);
			int daysToCount = 0;
			daysToCount = (aDate.getActualMaximum(java.util.Calendar.DATE) - daysLeft);
			System.out.println("");
			System.out.println(aDate.getDisplayName(((java.util.Calendar.MONTH)), java.util.Calendar.LONG, Locale.US));
			j = 0;
			for (i = 1; i < (aDate.getActualMaximum(java.util.Calendar.DATE) - daysToCount); i++) {
				j++;
				if (i < 10) {
					System.out.print("| 0" + i + ":" + displayCalendar.get(i) + " ");
				} else {
					System.out.print("| " + i + ":" + displayCalendar.get(i) + " ");
				}

				if (j % 5 == 0) {
					if (i != 30) {
						System.out.print("|");
					}
					System.out.println("");
					j = 0;
				}
			}
			System.out.print("|");
		}
	}

	private static void clearScreen() {
		for (int i = 0; i < 64; i++)
			System.out.println();
	}

}
