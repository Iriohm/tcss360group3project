package ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import model.Calendar;
import model.NPContact;

public class UINPContact extends UI {

	// Declarations

	public static NPContact beginNPContactUI(NPContact theNPContact, Calendar theCalendar) {
		int optSel = 0;
		ArrayList<String> options = new ArrayList<>();
		options.add("Submit an auction request");
		options.add("Add an item to auction inventory list");
		options.add("Exit AuctionCentral");

		String optHeader = "What would you like to do?";
		String optChoice = "Enter your Selection from 1 to";

		boolean exit = false;
		boolean goBack = false;
		Scanner userInput = new Scanner(System.in);

		// prints menu
		clearScreen();
		while (!exit && !goBack) {
			optSel = 0;
			System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
			System.out.println(theNPContact.getUsername() + " logged in as Non-profit organization contact");
			System.out.println(myCurrentDate);
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
				clearScreen();
			}
			if (optSel == 1) {
				theNPContact.submitAuctionRequest();
				System.out.println();
				System.out.println("Hit any key to continue: ");
				userInput.nextLine();
				clearScreen();
			} else if (optSel == 2) {
				//TODO add in the additem method
				System.out.println("Added " + " to the auction.");
				System.out.println();
				System.out.println("Hit any key to continue: ");
				userInput.nextLine();
				clearScreen();
			} else if (optSel == 3) {
				// exit system
				System.out.println("Goodbye");
				exit = true;
			}

		}
		userInput.close();
		return theNPContact;
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
