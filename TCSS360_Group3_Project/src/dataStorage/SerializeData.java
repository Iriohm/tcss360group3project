package dataStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Calendar;
import model.User;

public class SerializeData {

	/**
	 * The String that contains the location of the calendar config file
	 */
	public final String CALENDAR_CONFIG = "CalendarFileName";
	
	/**
	 * The String that contains the location of the user config file
	 */
	public final String USER_CONFIG = "UserFileName";
	
	/**
	 * The String that contains the location of the calendar .ser file
	 */
	private static String myCalendarFile;
	
	/**
	 * The String that contains the location of the user .ser file
	 */
	private static String myUserFile;
	
	/**
	 * The Calendar created from the .ser file
	 */
	private Calendar myCalendar;
	
	/**
	 * The List<User created from the .ser file
	 */
	private List<User> myUserList;

	/**
	* This method loads Calendar info, authenticates a User,
	* and then starts a UI Controller/Manager.
    */
	public SerializeData() {
		
		getFileNames();
		readCalendar();
		readUser();	
		//In the event that the user or calendar is currently non-existent
		if (myCalendar == null) {
			myCalendar = new Calendar();
			System.err.println("No current calendar");
		}
		if (myUserList == null) {
			myUserList = new ArrayList<User>();
			System.err.println("No current list of Users");
		}
				
	}
	
	/**
	 * Gets the Calendar
	 * @return the Calendar
	 */
	public Calendar getCalendar() {
		return myCalendar;
	}
	
	/**
	 * Gets the List of users
	 * @return List of users
	 */
	public List<User> getUsers() {
		return myUserList;
	}
	
	/**
	 * Reads in the desired .ser file names from the two config files. Names of the config files
	 * must remain true to CALENDAR_CONFIG and USER_CONFIG
	 */
	private void getFileNames() {
		 File file = new File(CALENDAR_CONFIG);

		    try {

		        Scanner sc = new Scanner(file);

		        if (sc.hasNextLine()) {
		            myCalendarFile = sc.nextLine();
		        }
		        sc.close();
		    } 
		    catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
		    
		    file = new File(USER_CONFIG);

		    try {

		        Scanner sc = new Scanner(file);

		        if (sc.hasNextLine()) {
		            myUserFile = sc.nextLine();
		        }
		        sc.close();
		    } 
		    catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
	}
	
	/*
	 * this is how we write out the calendar as a serialization object.
	 */
	public void writeOutCalendar() {

		try
	      {
	         FileOutputStream fileOut = new FileOutputStream(myCalendarFile);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myCalendar);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data was saved\n");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
    /**
     * Reads in the Calendar from the .ser file
     */
	private void readCalendar() {

		try
	      {
	         FileInputStream fileIn = new FileInputStream(myCalendarFile);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myCalendar = (Calendar) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Calendar class not found");
	         c.printStackTrace();
	         return;
	      }

	}

    /**
     * Reads in the list of users from the .ser file
     */
	@SuppressWarnings("unchecked")
	private void readUser() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream(myUserFile);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myUserList = (ArrayList<User>)in.readObject();
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
    
	/*
	 * this is how we write out the user list as a serialization object.
	 */
	public void writeOutUserList() {
		try
	      {
	         FileOutputStream fileOut =new FileOutputStream(myUserFile);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myUserList);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data was saved\n");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
    
    
	

}
