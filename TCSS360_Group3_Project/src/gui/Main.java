package gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import dataStorage.SerializeData;
import model.Calendar;
import model.User;

/**
* This class is used to begin the AuctionCentral environment.
*
* @author Vlad Kaganyuk
* @version 8 Nov 2016
*
*/
public class Main {


	/**
	* This method loads Calendar info, authenticates a User,
	* and then starts a UI Controller/Manager.
    */
	public static void main(String[] args) {
		SerializeData allData = new SerializeData();
		Authenticate.beginUI(args, allData);
	}

}