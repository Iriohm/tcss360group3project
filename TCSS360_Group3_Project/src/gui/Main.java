package gui;

import dataStorage.SerializeData;

/**
* This class is used to begin the AuctionCentral environment.
*
* @author Vlad Kaganyuk
* @author Justin Washburn
* @version 8 Nov 2016
*
*/
public class Main {


	/**
	* This method loads serialized info then gives it to the authenticate GUI
	* to begin the GUI
    */
	public static void main(String[] args) {
		SerializeData allData = new SerializeData();
		Authenticate.beginUI(args, allData);
	}

}