package ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import model.Auction;
import model.Calendar;
import model.Item;
import model.NPContact;

public class UINPContact extends UI {

	private static String myCurrentUsername;
	private static Calendar myCalender;
	private static GregorianCalendar myDate;
	// Declaration

	public static Calendar beginNPContactUI(NPContact theNPContact, Calendar theCalendar) {
		int optSel = 0;
		myCurrentUsername = theNPContact.getUsername();
		myCalender = theCalendar;
		myDate = new GregorianCalendar();
		ArrayList<String> options = new ArrayList<>();
		options.add("Submit an auction request");
		options.add("Add an item to my upcoming auction.");
		options.add("List the item inventory of my upcoming auction.");
		options.add("Exit AuctionCentral");

		String optHeader = "What would you like to do?";
		String optChoice = "Enter your Selection from 1 to";

		boolean exit = false;
		String userInput;
		Scanner inputScanner = new Scanner(System.in);

		// prints menu
		clearScreen();
		while (!exit) {
			optSel = 0;
			printHeader();
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
				System.out.println("==========================================================");
				System.out.println("We encountered an error with your input, please try again.");
				System.out.println("==========================================================");
				System.out.println();
			}
			if (optSel == 1) {
				clearScreen();
				System.out.println(theNPContact.getMyAuctions());
				if (theNPContact.hasAuctionUpcomingOrLastYear()) {
					System.out.println("==============================================================================================================================");
					System.out.println("Sorry, but it appears that your non-profit either has an upcoming auction, or had an auction less than a year ago.");
					System.out.println("You may not submit an auction request at this time. If you have any questions, please call customer support at 1-800-101-6969.");
					System.out.println("==============================================================================================================================\n");
				} else if (!myCalender.validateAuctionRequestMax25Auctions()) {
					System.out.println("Sorry, but it appears that we have reached our 25 upcoming auctions limit. Please try again at a later date.\n");
				} else {
					Auction auctionRequest = getAuctionDetailsFromUser(inputScanner);
					if (auctionRequest != null) {
						myCalender.addAuction(auctionRequest);
						theNPContact.addAuction(auctionRequest);
						printHeader();
						System.out.println("Your auction request has successfully been submited!\n");
						System.out.println("Press any key to go back to the main menu: ");
						inputScanner.nextLine();
						clearScreen();
					}
				}
			} else if (optSel == 2) {
				clearScreen();
				Auction latestAuction = theNPContact.getLatestAuction();
				GregorianCalendar testDate = null;
				if (latestAuction != null) {
					testDate = latestAuction.getDate();
				}
				GregorianCalendar todaysDate = (GregorianCalendar)GregorianCalendar.getInstance();
				if (latestAuction == null || todaysDate.after(testDate.clone())) {
					System.out.println("================================================");
					System.out.println("Sorry, but you don't have any upcoming auctions.");
					System.out.println("================================================\n");
				} else {
					printHeader();
					System.out.println("Adding item to auction: " + latestAuction.getAuctionName() + "\n");
					Item itemToAdd = getItemInfo(inputScanner);
					theNPContact.addItem(latestAuction, itemToAdd);
					clearScreen();
					System.out.println("Successfully added " + itemToAdd.getName() + " to " + latestAuction.getAuctionName() + ".");
					System.out.print("Press any key to continue: ");
					inputScanner.nextLine();
					clearScreen();
				}
			} else if (optSel == 3) {
				clearScreen();
				Auction latestAuction = theNPContact.getLatestAuction();
				GregorianCalendar testDate = null;
				if (latestAuction != null) {
					testDate = latestAuction.getDate();
				}
				GregorianCalendar todaysDate = (GregorianCalendar)GregorianCalendar.getInstance();
				if (latestAuction == null || todaysDate.after(testDate.clone())) {
					System.out.println("================================================");
					System.out.println("Sorry, but you don't have any upcoming auctions.");
					System.out.println("================================================\n");
				} else {
					printHeader();
					System.out.println(latestAuction.getAuctionName() + " Item Inventory");
					System.out.println("==========================================================");
					List<Item> itemInv = latestAuction.getItems();
					for (int i = 1; i <= itemInv.size(); i++) {
						System.out.println(i + ". " + itemInv.get(i - 1).getName());
					}
					if (itemInv.size() == 0) {
						System.out.println("No items registered to this auction.");
					}
					System.out.println();
					System.out.print("Press any key to return to the main menu: ");
					inputScanner.nextLine();
					clearScreen();
				}
			} else if (optSel == 4) {
				// exit system
				System.out.println("Goodbye");
				exit = true;
			}
		}
		inputScanner.close();
		return myCalender;
	}
	
	private static void printHeader() {
		System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
		System.out.println(myCurrentUsername + " logged in as Non-profit organization contact");
		System.out.println(myCurrentDate);
		System.out.println();
	}

	private static Item getItemInfo(Scanner input) {
		//Item Name (string)ields are: Donor Name (string), Item Description for Bidders (string), and Comment for Auction Central staff (string).

		System.out.print("Please enter the item name: ");
		String itemName = input.nextLine();
		
		System.out.print("Please enter the item condition (good, very good, like new, new): ");
		String condition = input.nextLine();
		
		System.out.print("Please enter the item size (small, medium large): ");
		String size = input.nextLine();
		
		System.out.print("Please enter the item's minimum bid (without a $ sign): ");
		double minBid = Double.parseDouble(input.nextLine());
		
		System.out.print("Please enter the item's quantity: ");
		int quantity =  Integer.parseInt(input.nextLine());
		
		Item itemToReturn;
		try {
			itemToReturn = new Item(myCalender.getNextItemID() + "", itemName, "", size, minBid, quantity, condition);
		} catch (IllegalArgumentException ex) {
			System.out.println("\nSorry, but there was an error your minimum bid. Please re-enter the item information.\n");
			itemToReturn = getItemInfo(input);
		}
		return itemToReturn;
	}
	
	private static void clearScreen(){
		for(int i = 0; i < 64; i++)
		System.out.println();
	}
	
	private static Auction getAuctionDetailsFromUser(Scanner input) {
		String auctionName;
		GregorianCalendar auctionDate = new GregorianCalendar();
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
			status = getAuctionDateInput(input, myDate);
			responseCode = myCalender.validateAuctionRequest(myDate);
			while ((status != -1 && status != 0 && status != -2) || responseCode != 0) {
				if (status != -2) {
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
				}
				
				status = getAuctionDateInput(input, auctionDate);
				responseCode = myCalender.validateAuctionRequest((GregorianCalendar)auctionDate.clone());
			}
		}
		
		if (status == -1) {
			return null;
		}
		

		return new Auction(myDate, auctionName);
	}
	
	public static int getAuctionDateInput(Scanner input, GregorianCalendar theGregCalendar) {
		System.out.println("Please enter your proposed auction date in the following format: DD/MM/YYYY");
		String auctionDay = input.nextLine();
		int indexes = auctionDay.lastIndexOf("/") - auctionDay.indexOf("/");
		while (!auctionDay.equals("c") && (auctionDay.indexOf("/") == -1 || indexes != 3)) {
			System.out.println("There was an error with your input, please use the proper format: DD/MM/YYYY - Ex. 10/03/2016");
			auctionDay = input.nextLine();
			indexes = auctionDay.lastIndexOf("/") - auctionDay.indexOf("/");
		}
		if (auctionDay.equals("c")) {
			return -1;
		}
		
		System.out.println("Please enter the time you would like your auction to start in the following format: HH [AM/PM] - Ex. 02 PM");
		String auctionTime = input.nextLine();
		
		while (!auctionTime.equals("c") && (auctionTime.indexOf(" ") == -1 || (auctionTime.indexOf("AM") == -1 && auctionTime.indexOf("PM") == -1))) {
			System.out.print("There was an error with your input, please try again: ");
			auctionTime = input.nextLine();
		}
		
		if (auctionTime.equals("c")) {
			return -1;
		}
		
		String[] date = auctionDay.split("/");
		String[] time = auctionTime.split(" ");	
		int hour = 0;
		try {
			hour = Integer.parseInt(time[0]);
		} catch (NumberFormatException e) {
			clearScreen();
			System.out.println("We encountered an error with your time input, and must restart auction date input.\n");
			return -2;
		}
		
		if (hour > 12 || hour < 1) {
			System.out.println("We encountered an error with your time input, and must restart auction date input.\n");
			return -2;
		}
		
		if (time[1].equals("PM")) {
			hour += 12;
		}
		int year = Integer.parseInt(date[2]);
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[0]);
		System.out.println(year + " " + month + " " + day);
		myDate.set(year, month, day, hour, 0, 0);
		//I have no idea why
		myDate.add(GregorianCalendar.MONTH, -1);
		System.out.println(myDate.getTime());
		//theGregCalendar = new GregorianCalendar();
		//theGregCalendar.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), hour, 0, 0);

		return 0;
	}
}