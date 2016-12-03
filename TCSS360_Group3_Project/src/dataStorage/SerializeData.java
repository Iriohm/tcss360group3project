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

	public static String myCalendarFile = "testCalendar.ser";
	
	public static String myUserFile = "testUserList.ser";
	
	private Calendar myCalendar;
	
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
	
	
	public Calendar getCalendar() {
		return myCalendar;
	}
	
	public List<User> getUsers() {
		return myUserList;
	}
	
	private void getFileNames() {
		 File file = new File("CalendarFileName");

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
		    
		    file = new File("UserFileName");

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
	 * how we write out the calendar as a serialization object.
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
