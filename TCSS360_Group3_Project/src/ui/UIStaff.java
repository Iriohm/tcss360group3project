

package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Calendar;

/**
 * Prints the staff user interface menu. 
 * 
 * @author Robert Hinds
 * @version 11/11/2016
 *
 */
public class UIStaff extends UI{

	public static void main(String[] args)  {  //remove?
		// Declarations

		
		int optSel = 0;
		ArrayList<String> options = new ArrayList<>();
		options.add("View calendar of upcoming auctions");
		options.add("Administrative functions");
		options.add("Exit AuctionCentral");
		
		String optHeader = "What would you like to do?";
		String optChoice = "Enter your Selection from 1 to";
		int intTemp = 0;
		int auctionCount = 0;
		if (Calendar.getAuctions(todayDate) != null){
			auctionCount = Calendar.getAuctions(todayDate).size();
		}
		// prints menu
		do {
			System.out.println(header);

			System.out.println(currentDay + "." + " Total number of upcoming auctions: "  + auctionCount); // add getter to grab auction count for current date?																						 

			System.out.println("");
			System.out.println(optHeader);
			for (int i = 0; i < options.size(); i++) {
				System.out.println(i + 1 + ". " + options.get(i));
			}
			System.out.println("");
			System.out.print(optChoice + " " + options.size() + ": ");
			Scanner userInput = new Scanner(System.in);
			if(userInput.hasNextInt()){
				intTemp = userInput.nextInt();
				if(intTemp > 0 && intTemp < options.size()){
					optSel = intTemp;
					userInput.close();
				}
			}


		} while (optSel <= 0 || optSel > options.size());
	//calls methods depending on option chosen	
		if(optSel == 1){
			try{
			Calendar.getAuctions(todayDate).toString();   // maybe needs to be overridden?
			}catch(NullPointerException e){
				 System.err.println("NullPointerException: " + e.getMessage());
			}
		}
		else if(optSel == 2){
			//admin functions
		}
		else if(optSel == 3){
			//exit system
		}
		
	} // for main


}
