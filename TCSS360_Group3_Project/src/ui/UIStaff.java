
package ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

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

	public static Staff beginStaffUI(Staff theStaff, Calendar theCalendar) {
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
		return theStaff;
	}

	private static void getAuction(Calendar theCalendar) {
		GregorianCalendar aDate = (GregorianCalendar) GregorianCalendar.getInstance();
		theCalendar.getAuctions(aDate);

	} 
	
	private static void clearScreen(){
		for(int i = 0; i < 64; i++)
		System.out.println();
	}

}
