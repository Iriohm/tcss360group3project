package ui;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model.Auction;
import model.Bidder;
import model.Calendar;
import model.Item;
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
	private static Calendar myCalender	= null;
	private static ArrayList<User> myListUser = null;
//	
//	static Format myFormatter = new SimpleDateFormat("MMMM dd, yyyy"); 
//	static Date myTodayDate = new Date();
//	static String myCurrentDay = myFormatter.format(myTodayDate);

	
	
	public static void begainUI() {
		readInData();
		User chooseUser = choosePreviousOrMakeUser();
		if(chooseUser != null) {
			myCalender = whichUserUI(chooseUser);
		}
		writeOutData();
	}
	
	
	/*
	 * login on a previously created user account or create a new user account
	 */
	private static User choosePreviousOrMakeUser() {
		User myUser = null;
		int choose = 0;
		do {
			System.out.print("Welcome to Auction Center\n"
					+ "1) Login\n"
					+ "2) crate acount\n"
					+ "3) Exit.\n"
					+ "Enter your Selection from 1 to 3: ");
			choose = myScanner.nextInt();
		} while (choose < 1 && choose > 3);
		if(choose == 1){
			System.out.println("went in to login");
			myUser = chooseLogin();
		} else if (choose ==2){
			System.out.println("went in to create User");
			myUser = createUser();
			myListUser.add(myUser);
		}
		return myUser;
	}
	
	private static Calendar whichUserUI(User theUser) {
		Calendar aCalendar = null;
		if(theUser.getType() == 1) {// the Staff
			//TODO: the Stuff UI
		} else if (theUser.getType() == 2) { // the NPContact
			//TODO: the NPContact UI
		} else { // the Bidder type
			aCalendar = UIBidder.beginBidderUI((Bidder)theUser, myCalender);
		}
		return aCalendar;
	}
	
	private static User chooseLogin() {
		int choose = 0;
		do {
			System.out.println("Pick the user you are.\n");
			for (int i = 0; i < myListUser.size(); i++) {
				System.out.println((i+1) + ") " + myListUser.get(i).getUsername());
			}
			System.out.print("Enter your Selection from 1 to " + myListUser.size() + ": ");
			choose = myScanner.nextInt();
		} while (choose < 1 || choose > myListUser.size());		
		return myListUser.get((choose - 1));
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
	
	private static void readInData() {
		myCalender = null;
		myListUser = null;
	    readCaledar();
	    readUser();
	}
	
	private static void readCaledar() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream("testCalendar.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myCalender = (Calendar) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Calender class not found");
	         c.printStackTrace();
	         return;
	      }
	}
	
	private static void readUser() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream("testUserList.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myListUser = (ArrayList<User>)in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Calender class not found");
	         c.printStackTrace();
	         return;
	      }
	}
	
	private static void writeOutData() {
		writeOutCalendar();
		writeOutCalendar();
	}
	
	private static void writeOutCalendar() {
//		Item football = new Item("1", "football", "the best football", "Small", 20.0, 1, "Very Fine");
//		Item baseball = new Item("2", "baseball", "the best baseball", "Small", 20.0, 1, "Very Fine");
//		Auction testAuction = new Auction(new GregorianCalendar(), "testAcution");
//		testAuction.addItem(football);
//		testAuction.addItem(baseball);
//		myCalender = new Calendar();
//		myCalender.addAuction(testAuction);
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("testCalendar.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myCalender);
	         fileOut = new FileOutputStream("testUserList.ser");
	         out = new ObjectOutputStream(fileOut);
	         out.writeObject(myListUser);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in testCalendar.ser\n");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	private static void writeOutUserList() {
//		myListUser = new ArrayList<User>();
//		myListUser.add(new Bidder("davidTest", myCalender));
		try
	      {
	         FileOutputStream fileOut =new FileOutputStream("testUserList.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myListUser);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in testUserList.ser\n");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
}
