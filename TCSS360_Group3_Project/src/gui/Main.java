package gui;

import dataStorage.SerializeData;

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