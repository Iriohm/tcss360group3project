package ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import model.Auction;
import model.Calendar;
import model.NPContact;

public class UINPContact extends UI {

	private static String myCurrentUsername;
	private static Calendar myCalender;
	private static GregorianCalendar myDate;
=======
	private static boolean myCancel;
>>>>>>> branch 'master' of https://github.com/Iriohm/tcss360group3project.git
	// Declaration

	public static NPContact beginNPContactUI(NPContact theNPContact, Calendar theCalendar) {
		int optSel = 0;
		myCurrentUsername = theNPContact.getUsername();
		myCalender = theCalendar;
<<<<<<< HEAD
		myDate = new GregorianCalendar();
=======
		myCancel = false;
>>>>>>> branch 'master' of https://github.com/Iriohm/tcss360group3project.git
		ArrayList<String> options = new ArrayList<>();
		options.add("Submit an auction request");
		options.add("Add an item to your upcoming auction.");
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
			}//sdf
			if (optSel == 1) {
				clearScreen();
				if (theNPContact.hasAuctionUpcomingOrLastYear()) {
					System.out.println("==============================================================================================================================");
					System.out.println("Sorry, but it appears that your non-profit either has an upcoming auction, or had an auction less than a year ago.");
					System.out.println("You may not submit an auction request at this time. If you have any questions, please call customer support at 1-800-101-6969.");
					System.out.println("==============================================================================================================================\n");
				} else if (!myCalender.validateAuctionRequestMax25Auctions()) {
					System.out.println("Sorry, but it appears that we have reached our 25 upcoming auctions limit. Please try again at a later date.\n");
				} else {
					Auction auctionRequest = getAuctionDetailsFromUser(inputScanner);
					while (auctionRequest != null && !myCancel) {
						theCalendar.addAuction(auctionRequest);
						theNPContact.addAuction(auctionRequest);
						System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
						System.out.println(myCurrentUsername + " logged in as Non-profit organization contact");
						System.out.println(myCurrentDate);
						System.out.println();
						System.out.println("Your auction request has successfully been submited!\n");
						System.out.println("Press any key to go back to the main menu: ");
						inputScanner.nextLine();
						clearScreen();
					}
				}
			} else if (optSel == 2) {
				Auction temp = theNPContact.getLatestAuction();
				GregorianCalendar testDate = null;
				if (temp != null) {
					testDate = temp.getDate();

				}
				GregorianCalendar todaysDate = (GregorianCalendar)GregorianCalendar.getInstance();
<<<<<<< HEAD
				if (temp == null || todaysDate.after(testDate.clone())) {

=======
				// || todaysDate.after(testDate)
				if (temp == null) {
					System.out.println(todaysDate +"\n" + testDate);
>>>>>>> branch 'master' of https://github.com/Iriohm/tcss360group3project.git
					System.out.println("================================================");
					System.out.println("Sorry, but you don't have any upcoming auctions.");
					System.out.println("================================================\n");
				} else {
					//getItemInfo();
					System.out.println("Added " + " to the auction.");
					System.out.println();
					System.out.println("Hit any key to continue: ");
					inputScanner.nextLine();
					clearScreen();
				}
			} else if (optSel == 3) {
				// exit system
				System.out.println("Goodbye");
				exit = true;
			}
		}
		inputScanner.close();
		return theNPContact;
	}

	//private static void getItemInfo();
	
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
<<<<<<< HEAD
		GregorianCalendar auctionDate = new GregorianCalendar();
=======
		GregorianCalendar auctionDate;
>>>>>>> branch 'master' of https://github.com/Iriohm/tcss360group3project.git
		int status = 0;
		int responseCode = 0;
		
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
			auctionDate = getAuctionDateInput(input);
			System.out.println("Before check: " + auctionDate);
			responseCode = myCalender.validateAuctionRequest((GregorianCalendar)auctionDate.clone());
			while (!myCancel || responseCode != 0) {
					switch(responseCode) {
						case -1:
							System.out.println("There are already two auctions scheduled on this day, please choose another.\n");
							break;
						case -3:
							System.out.println("We are only allowed to schedule auctions within one month into the future. Please choose a closer date.\n");
							break;
						case -4:
							System.out.println("\n====================================================================");
							System.out.println("Please choose a date that is AT LEAST one week into the future.");
							System.out.println("====================================================================\n");
							break;
				}
				
<<<<<<< HEAD
				status = getAuctionDateInput(input, auctionDate);
				responseCode = myCalender.validateAuctionRequest((GregorianCalendar)auctionDate.clone());
=======
				auctionDate = getAuctionDateInput(input);
				responseCode = myCalender.validateAuctionRequest(auctionDate);
>>>>>>> branch 'master' of https://github.com/Iriohm/tcss360group3project.git
			}
		}
		
		if (status == -1) {
			return null;
		}
		
<<<<<<< HEAD

		return new Auction(myDate, auctionName);
=======
		//System.out.println("Date in auction: " + returnAuction.getDate());
		return new Auction(auctionDate, auctionName);
>>>>>>> branch 'master' of https://github.com/Iriohm/tcss360group3project.git
	}
	
	public static GregorianCalendar getAuctionDateInput(Scanner input) {
		System.out.println("Please enter your proposed auction date in the following format: DD/MM/YYYY");
		String auctionDay = input.nextLine();
		int indexes = auctionDay.lastIndexOf("/") - auctionDay.indexOf("/");
		while (!auctionDay.equals("c") && (auctionDay.indexOf("/") == -1 || indexes != 3)) {
			System.out.println("There was an error with your input, please use the proper format: DD/MM/YYYY - Ex. 10/03/2016");
			auctionDay = input.nextLine();
			indexes = auctionDay.lastIndexOf("/") - auctionDay.indexOf("/");
		}
		if (auctionDay.equals("c")) {
			myCancel = true;
			return null;
		}
		
		System.out.println("Please enter the time you would like your auction to start in the following format: HH [AM/PM] - Ex. 02 PM");
		String auctionTime = input.nextLine();
		
		while (!auctionTime.equals("c") && (auctionTime.indexOf(" ") == -1 || (auctionTime.indexOf("AM") == -1 && auctionTime.indexOf("PM") == -1))) {
			System.out.print("There was an error with your input, please try again: ");
			auctionTime = input.nextLine();
		}
		
		if (auctionTime.equals("c")) {
			myCancel = true;
			return null;
		}
		
		String[] date = auctionDay.split("/");
		String[] time = auctionTime.split(" ");	
		int hour = 0;
		try {
			hour = Integer.parseInt(time[0]);
		} catch (NumberFormatException e) {
			clearScreen();
			System.out.println("We encountered an error with your time input, and must restart auction date input.\n");
			return null;
		}
		
		if (hour > 12 || hour < 1) {
			System.out.println("We encountered an error with your time input, and must restart auction date input.\n");
			return null;
		}
		
		if (time[1].equals("PM")) {
			hour += 12;
		}
		myDate.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), hour, 0, 0);
		theGregCalendar = new GregorianCalendar();
		theGregCalendar.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), hour, 0, 0);

<<<<<<< HEAD
		return 0;
=======
		return new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), hour, 0);
		
		//GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute) 
>>>>>>> branch 'master' of https://github.com/Iriohm/tcss360group3project.git
	}
}
