package dataStorage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Calendar;
import model.User;

public class SerializeData {

	public static final String CALENDAR_SER = "testCalendar.ser";
	
	public static final String USER_SER = "testUserList.ser";
	
	private Calendar myCalendar;
	
	private List<User> myUserList;

	/**
	* This method loads Calendar info, authenticates a User,
	* and then starts a UI Controller/Manager.
    */
	public SerializeData() {
		readCalendar();
		readUser();			
	}
	
	public Calendar getCalendar() {
		return myCalendar;
	}
	
	public List<User> getUsers() {
		return myUserList;
	}
	
	/*
	 * how we write out the calendar as a serialization object.
	 */
	public void writeOutCalendar() {

		try
	      {
	         FileOutputStream fileOut = new FileOutputStream(CALENDAR_SER);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myCalendar);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data was saved in testCalendar.ser\n");
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
	         FileInputStream fileIn = new FileInputStream(CALENDAR_SER);
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
	         FileInputStream fileIn = new FileInputStream("testUserList.ser");
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
	         FileOutputStream fileOut =new FileOutputStream(USER_SER);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myUserList);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data was saved in testUserList.ser\n");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
    
    
	

}
