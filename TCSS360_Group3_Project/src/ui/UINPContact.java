package ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import model.Auction;
import model.Calendar;
import model.NPContact;

public class UINPContact extends UI {

	private static String myCurrentUsername;
	// Declaration

	public static NPContact beginNPContactUI(NPContact theNPContact, Calendar theCalendar) {
		int optSel = 0;
		myCurrentUsername = theNPContact.getUsername();
		ArrayList<String> options = new ArrayList<>();
		options.add("Submit an auction request");
		options.add("Add an item to auction inventory list");
		options.add("Exit AuctionCentral");

		String optHeader = "What would you like to do?";
		String optChoice = "Enter your Selection from 1 to";

		boolean exit = false;
		boolean goBack = false;
		String userInput;
		Scanner inputScanner = new Scanner(System.in);

		// prints menu
		clearScreen();
		while (!exit) {
			optSel = 0;
			System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
			System.out.println(myCurrentUsername + " logged in as Non-profit organization contact");
			System.out.println(myCurrentDate);
			System.out.println();
			System.out.println(optHeader);
			for (int i = 0; i < options.size(); i++) {
				System.out.println(i + 1 + ". " + options.get(i));
			}
			System.out.println();
			System.out.print(optChoice + " " + options.size() + ": ");
			userInput = inputScanner.nextLine();
			try {
				optSel = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				clearScreen();
				System.out.println("We encountered an error with your input, please try again.");
				System.out.println();
			}
			if (optSel == 1) {
				clearScreen();
				Auction auctionRequest = getAuctionDetailsFromUser(inputScanner);
				if (auctionRequest != null) {
					theCalendar.addAuction(auctionRequest);
					System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
					System.out.println(myCurrentUsername + " logged in as Non-profit organization contact");
					System.out.println(myCurrentDate);
					System.out.println();
					System.out.println("Your auction request has successfully been submited!\n");
					System.out.println("Press any key to go back to the main menu: ");
					inputScanner.nextLine();
					clearScreen();
				}
			} else if (optSel == 2) {
				//TODO add in the additem method
				System.out.println("Added " + " to the auction.");
				System.out.println();
				System.out.println("Hit any key to continue: ");
				inputScanner.nextLine();
				clearScreen();
			} else if (optSel == 3) {
				// exit system
				System.out.println("Goodbye");
				exit = true;
			}
		}
		inputScanner.close();
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
	
	private static Auction getAuctionDetailsFromUser(Scanner input) {
		String auctionName;
		GregorianCalendar x = new GregorianCalendar();
		int status = 0;
		
		System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
		System.out.println(myCurrentUsername + " logged in as Non-profit organization contact");
		System.out.println(myCurrentDate);
		System.out.println();
		System.out.println("Auction Request Details");
		System.out.println("(Note: You may press 'c' at any time to cancel this auction request.)");
		System.out.println();
		System.out.print("Please enter a name for your auction: ");
		auctionName = input.nextLine();
		if (auctionName.equals("c")) {
			return null;
		} else {
			status = getAuctionDateInput(input, x);
			while (status != -1 && status != 0) {
				System.out.println("There was an error with your proposed date, please try again.");
				status = getAuctionDateInput(input, x);
			}
		}
		
		if (status == -1) {
			return null;
		}
		
		return new Auction(x, "");
	}
	
	public static int getAuctionDateInput(Scanner input, GregorianCalendar theGregCalendar) {
		System.out.println("Please enter your proposed auction date in the following format: month/day/year");
		String auctionDay = input.nextLine();
		int indexes = auctionDay.lastIndexOf("/") - auctionDay.indexOf("/");
		while (!auctionDay.equals("c") && (auctionDay.indexOf("/") == -1 || indexes > 3 || indexes < 1)) {
			System.out.println("There was an error with your input, please use the proper format: month/day/year - Ex. 10/23/2016");
			auctionDay = input.nextLine();
			indexes = auctionDay.lastIndexOf("/") - auctionDay.indexOf("/");
		}
		if (auctionDay.equals("c")) {
			return -1;
		}
		
		System.out.println("Please enter the time you would like your auction to start in the following format: Hour:Minutes - Ex. 14:30 (2:30 PM)");
		String auctionTime = input.nextLine();
		while (!auctionTime.equals("c") && auctionTime.indexOf(":") == -1) {
			System.out.print("There was an error with your input, please try again: ");
			auctionTime = input.nextLine();
		}
		
		if (auctionTime.equals("c")) {
			return -1;
		}
		
		String[] date = auctionDay.split("/");
		String[] time = auctionTime.split(":");	
		
		theGregCalendar = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
		//GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute) 
		return 0;
	}
}
