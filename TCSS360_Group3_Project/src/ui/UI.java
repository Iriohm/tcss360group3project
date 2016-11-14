package ui;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import model.Calendar;
import model.User;

/**
 * this start the ui.
 * 
 * @author David Nowlin
 * @version 11/11/2016
 *
 */
public class UI {
	private static Scanner myScanner = new Scanner(System.in);
	private static Calendar myCalender	= new Calendar();
	private static User myUser = null;	
	//used to generate the current Date.
	private static Format myFormatter = new SimpleDateFormat("MMMM dd, yyyy"); 
	protected static Date   myTodayDate = GregorianCalendar.getInstance().getTime();
	protected static String myCurrentDate = myFormatter.format(myTodayDate);

	
	public static void begainUI() {
		choosePreviousOrMakeUser();
	}
	
	/*
	 * this choose if it a previous user to login or crate a user
	 */
	private static void choosePreviousOrMakeUser() {
		int choose = 0;
		do {
			System.out.print("AuctionCentral: the auctioneer for non-profit organizations.\n"
					+ "1) Login\n"
					+ "2) crate acount\n"
					+ "3) Exit."
					+ "Enter your Selection from 1 to 3: ");
			choose = myScanner.nextInt();
		} while (choose < 1 && choose > 3);
		if(choose == 1){
			System.out.println("went in to login");
			//TODO Login a user need to figure out
		} else if (choose ==2){
			System.out.println("went in to create User");
			myUser = createUser();
		} else {
			return;
		}
	}

	private static User createUser() {
		//TODO create the the new user new to test some how.
		int typeUser = 0;
		String userName = "";
		do {
			System.out.print("Create a New User.\n"
					+ "What Type Of User You Are.\n"
					+ "1) For Staff User.\n"
					+ "2) For None For Profit Contact User.\n"
					+ "3) For Bidder User.\n"
					+ "Enter your Selection from 1 to 3: ");
			typeUser = myScanner.nextInt();
		} while (typeUser < 1 && typeUser > 3);
		System.out.println("Your User Name With No Space Please.");
		userName = myScanner.next();		
		return new User(userName, myCalender, typeUser);
	}
}
