package ui;

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
	
	public static void begainUI() {
		choosePreviousOrMakeUser();
	}
	
	/*
	 * this choose if it a previous user to login or crate a user
	 */
	private static void choosePreviousOrMakeUser() {
		int choose = 0;
		do {
			System.out.print("Welcome to Auction Center\n"
					+ "1) Login\n"
					+ "2) crate acount\n"
					+ "Enter your Selection from 1 to 2: ");
			choose = myScanner.nextInt();
		} while (choose != 1 && choose != 2);
		if(choose == 1){
			System.out.println("went in to login");
			//TODO Login a user need to figure out
		} else {
			System.out.println("went in to create User");
			myUser = createUser();
		}
	}

	private static User createUser() {
		//TODO create the the new user new to test some how.
		int typeUser = 0;
		String userName = "";
		do {
			System.out.print("Creat a New User.\n"
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
