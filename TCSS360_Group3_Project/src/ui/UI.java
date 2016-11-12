package ui;

import java.util.Scanner;

/**
 * this start the ui.
 * 
 * @author David Nowlin
 * @version 11/11/2016
 *
 */
public class UI {
	private Scanner scanner = new Scanner(System.in);
	
	public static void begainUI() {
		choosePreviousOrMakeUser();
	}
	
	/*
	 * this choose if it a previous user to login or crate a user
	 */
	private static void choosePreviousOrMakeUser() {
		int choose = 0;
		do {
			System.out.println("Welcome to Auction Center\n"
					+ "1) Login\n"
					+ "2) crate acount\n"
					+ "Enter your Selection from 1 to 2");
		} while (choose != 1 || choose != 2);
		if(choose == 1){
			//TODO Login a user need to figure out
		} else {
			
		}
	}
	
	private static void createUser() {
		//TODO create the the new user.
		System.out.println("");
	}
}
